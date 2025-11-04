package com.guan.common.lock.impl;

import com.guan.common.lock.LockOperation;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedissionDistributeLock implements LockOperation {

    private final RedissonClient redissonClient;

    public RedissionDistributeLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(expire, timeUnit);
        } catch (InterruptedException e) {
            // Redisson锁获取异常
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
