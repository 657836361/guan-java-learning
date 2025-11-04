package com.guan.distribute.redisson;

import com.guan.distribute.core.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson分布式锁实现
 * 特点：
 * - 基于Redisson框架，功能丰富
 * - 支持锁重入
 * - 支持公平锁和非公平锁
 * - 支持锁等待和自动续期
 * - 支持联锁、红锁等高级特性
 */
public class RedissonDistributedLock implements DistributedLock {

    private final RedissonClient redissonClient;

    public RedissonDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(expire, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean tryLock(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, expire, expireUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public boolean isLocked(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.isLocked();
    }

    /**
     * 获取公平锁
     *
     * @param key 锁的key
     * @return 公平锁实现
     */
    public boolean tryFairLock(String key, long expire, TimeUnit unit) {
        RLock lock = redissonClient.getFairLock(key);
        try {
            return lock.tryLock(expire, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 获取读写锁的写锁
     *
     * @param key 锁的key
     * @return 是否成功获取写锁
     */
    public boolean tryWriteLock(String key, long expire, TimeUnit unit) {
        RLock writeLock = redissonClient.getReadWriteLock(key).writeLock();
        try {
            return writeLock.tryLock(expire, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 获取读写锁的读锁
     *
     * @param key 锁的key
     * @return 是否成功获取读锁
     */
    public boolean tryReadLock(String key, long expire, TimeUnit unit) {
        RLock readLock = redissonClient.getReadWriteLock(key).readLock();
        try {
            return readLock.tryLock(expire, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}