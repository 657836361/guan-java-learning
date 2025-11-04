package com.guan.distribute.zookeeper;

import com.guan.distribute.core.DistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * ZooKeeper分布式锁实现
 * 特点：
 * - 高可靠性，基于ZooKeeper的强一致性
 * - 支持锁重入
 * - 支持公平锁（按请求顺序获取）
 * - 支持锁等待机制
 * - 性能相对较低，但可靠性高
 */
public class ZookeeperDistributedLock implements DistributedLock {

    private final CuratorFramework client;
    private static final String LOCK_PATH_PREFIX = "/locks/";

    // 存储锁实例的Map，用于重入和释放
    private final ConcurrentMap<String, InterProcessMutex> lockMap = new ConcurrentHashMap<>();

    // 线程本地存储，用于支持锁重入
    private final ThreadLocal<ConcurrentMap<String, Integer>> lockCount = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public ZookeeperDistributedLock(CuratorFramework client) {
        this.client = client;
        ensureLockPathExists();
    }

    private void ensureLockPathExists() {
        try {
            if (client.checkExists().forPath(LOCK_PATH_PREFIX) == null) {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(LOCK_PATH_PREFIX);
            }
        } catch (Exception e) {
            // 创建路径失败，但不影响锁的使用
        }
    }

    @Override
    public boolean tryLock(String key) {
        return tryLock(key, -1, null); // 永久锁
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit unit) {
        String lockPath = LOCK_PATH_PREFIX + key;

        try {
            // 获取或创建锁实例
            InterProcessMutex lock = lockMap.computeIfAbsent(lockPath, k -> new InterProcessMutex(client, lockPath));

            // 尝试获取锁
            boolean acquired = false;
            try {
                if (expire > 0 && unit != null) {
                    lock.acquire(expire, unit);
                    acquired = true;
                } else {
                    lock.acquire();
                    acquired = true;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                acquired = false;
            }

            if (acquired) {
                // 记录重入次数
                ConcurrentMap<String, Integer> threadLockCount = lockCount.get();
                threadLockCount.merge(lockPath, 1, Integer::sum);
            }

            return acquired;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit) {
        String lockPath = LOCK_PATH_PREFIX + key;
        long deadline = System.nanoTime() + waitUnit.toNanos(waitTime);
        long retryInterval = Math.min(100, waitUnit.toMillis(waitTime) / 10);

        while (System.nanoTime() < deadline) {
            if (tryLock(key, expire, expireUnit)) {
                return true;
            }

            long remainingNanos = deadline - System.nanoTime();
            if (remainingNanos <= 0) {
                break;
            }

            long sleepMillis = Math.min(retryInterval, TimeUnit.NANOSECONDS.toMillis(remainingNanos));
            if (sleepMillis > 0) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(sleepMillis));
            }
        }

        return false;
    }

    @Override
    public void unlock(String key) {
        String lockPath = LOCK_PATH_PREFIX + key;

        try {
            ConcurrentMap<String, Integer> threadLockCount = lockCount.get();
            Integer count = threadLockCount.get(lockPath);

            if (count == null || count <= 0) {
                return;
            }

            // 减少重入次数
            if (count > 1) {
                threadLockCount.put(lockPath, count - 1);
                return;
            }

            // 释放锁
            InterProcessMutex lock = lockMap.get(lockPath);
            if (lock != null) {
                lock.release();
                threadLockCount.remove(lockPath);
                lockMap.remove(lockPath);
            }
        } catch (Exception e) {
            // 释放锁异常
        } finally {
            // 清理ThreadLocal
            if (lockCount.get().isEmpty()) {
                lockCount.remove();
            }
        }
    }

    @Override
    public boolean isLocked(String key) {
        String lockPath = LOCK_PATH_PREFIX + key;
        InterProcessMutex lock = lockMap.get(lockPath);
        return lock != null;
    }

    /**
     * 强制释放锁（用于异常情况下的清理）
     */
    public void forceUnlock(String key) {
        String lockPath = LOCK_PATH_PREFIX + key;
        try {
            InterProcessMutex lock = lockMap.get(lockPath);
            if (lock != null) {
                lock.release();
                lockMap.remove(lockPath);
            }
        } catch (Exception e) {
            // 强制释放失败
        }
    }
}