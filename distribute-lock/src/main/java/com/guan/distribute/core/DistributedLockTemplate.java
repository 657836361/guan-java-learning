package com.guan.distribute.core;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁模板类
 * 提供简化的锁操作模板，自动管理锁的获取和释放
 */
public class DistributedLockTemplate {

    private final DistributedLock distributedLock;

    public DistributedLockTemplate(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    /**
     * 执行带锁的任务（无返回值）
     *
     * @param key    锁的key
     * @param expire 锁的过期时间
     * @param unit   过期时间单位
     * @param task   要执行的任务
     */
    public void execute(String key, long expire, TimeUnit unit, Runnable task) {
        if (distributedLock.tryLock(key, expire, unit)) {
            try {
                task.run();
            } finally {
                distributedLock.unlock(key);
            }
        }
    }

    /**
     * 执行带锁的任务（有返回值）
     *
     * @param key    锁的key
     * @param expire 锁的过期时间
     * @param unit   过期时间单位
     * @param task   要执行的任务
     * @param <T>    返回值类型
     * @return 任务执行结果，获取锁失败返回null
     */
    public <T> T execute(String key, long expire, TimeUnit unit, Supplier<T> task) {
        if (distributedLock.tryLock(key, expire, unit)) {
            try {
                return task.get();
            } finally {
                distributedLock.unlock(key);
            }
        }
        return null;
    }

    /**
     * 尝试执行带锁的任务，返回是否执行成功
     *
     * @param key    锁的key
     * @param expire 锁的过期时间
     * @param unit   过期时间单位
     * @param task   要执行的任务
     * @return 是否成功执行了任务
     */
    public boolean tryExecute(String key, long expire, TimeUnit unit, Runnable task) {
        if (distributedLock.tryLock(key, expire, unit)) {
            try {
                task.run();
                return true;
            } finally {
                distributedLock.unlock(key);
            }
        }
        return false;
    }

    /**
     * 执行带等待时间的锁任务（无返回值）
     *
     * @param key        锁的key
     * @param waitTime   等待获取锁的最大时间
     * @param waitUnit   等待时间单位
     * @param expire     锁的过期时间
     * @param expireUnit 过期时间单位
     * @param task       要执行的任务
     * @return 是否成功执行了任务
     */
    public boolean execute(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit, Runnable task) {
        if (distributedLock.tryLock(key, waitTime, waitUnit, expire, expireUnit)) {
            try {
                task.run();
                return true;
            } finally {
                distributedLock.unlock(key);
            }
        }
        return false;
    }

    /**
     * 执行带等待时间的锁任务（有返回值）
     *
     * @param key        锁的key
     * @param waitTime   等待获取锁的最大时间
     * @param waitUnit   等待时间单位
     * @param expire     锁的过期时间
     * @param expireUnit 过期时间单位
     * @param task       要执行的任务
     * @param <T>        返回值类型
     * @return 任务执行结果，获取锁失败返回null
     */
    public <T> T execute(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit, Supplier<T> task) {
        if (distributedLock.tryLock(key, waitTime, waitUnit, expire, expireUnit)) {
            try {
                return task.get();
            } finally {
                distributedLock.unlock(key);
            }
        }
        return null;
    }

    /**
     * 获取底层的分布式锁实例
     *
     * @return 分布式锁实例
     */
    public DistributedLock getDistributedLock() {
        return distributedLock;
    }
}