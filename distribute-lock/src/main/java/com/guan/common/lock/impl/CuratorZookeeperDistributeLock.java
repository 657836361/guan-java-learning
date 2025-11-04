package com.guan.common.lock.impl;

import com.guan.common.lock.LockOperation;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CuratorZookeeperDistributeLock implements LockOperation {

    private final CuratorFramework client;

    // 锁路径前缀
    private static final String LOCK_PATH_PREFIX = "/locks/";

    // 存储锁实例的Map，用于重入和释放
    private final ConcurrentMap<String, InterProcessMutex> lockMap = new ConcurrentHashMap<>();

    // 线程本地存储，用于支持锁重入
    private final ThreadLocal<ConcurrentMap<String, Integer>> lockCount = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public CuratorZookeeperDistributeLock(CuratorFramework client) {
        this.client = client;
        // 确保锁根路径存在
        ensureLockPathExists();
    }

    private void ensureLockPathExists() {
        try {
            if (client.checkExists().forPath(LOCK_PATH_PREFIX) == null) {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(LOCK_PATH_PREFIX);
                // ZooKeeper锁根路径创建成功
            }
        } catch (Exception e) {
            // 检查或创建锁路径失败
        }
    }

    @Override
    public boolean tryLock(String key) {
        return tryLock(key, 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit timeUnit) {
        String lockPath = LOCK_PATH_PREFIX + key;

        try {
            // 获取或创建锁实例
            InterProcessMutex lock = lockMap.computeIfAbsent(lockPath, k -> new InterProcessMutex(client, lockPath));

            // 尝试获取锁
            boolean acquired = lock.acquire(expire, timeUnit);

            if (acquired) {
                // 记录重入次数
                ConcurrentMap<String, Integer> threadLockCount = lockCount.get();
                threadLockCount.merge(lockPath, 1, Integer::sum);
                // 成功获取ZooKeeper分布式锁
            } else {
                // 获取ZooKeeper分布式锁失败
            }

            return acquired;
        } catch (Exception e) {
            // 获取ZooKeeper分布式锁异常
            return false;
        }
    }

    @Override
    public void unlock(String key) {
        String lockPath = LOCK_PATH_PREFIX + key;

        try {
            ConcurrentMap<String, Integer> threadLockCount = lockCount.get();
            Integer count = threadLockCount.get(lockPath);

            if (count == null || count <= 0) {
                // 尝试释放未持有的锁
                return;
            }

            // 减少重入次数
            if (count > 1) {
                threadLockCount.put(lockPath, count - 1);
                // 减少锁重入次数
                return;
            }

            // 释放锁
            InterProcessMutex lock = lockMap.get(lockPath);
            if (lock != null) {
                lock.release();
                threadLockCount.remove(lockPath);

                // 从Map中移除锁实例（可选，根据需要）
                lockMap.remove(lockPath);

                // 成功释放ZooKeeper分布式锁
            }
        } catch (Exception e) {
            // 释放ZooKeeper分布式锁异常
        } finally {
            // 清理ThreadLocal
            if (lockCount.get().isEmpty()) {
                lockCount.remove();
            }
        }
    }
}
