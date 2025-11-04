package com.guan.distribute.redis;

import com.guan.distribute.core.DistributedLock;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Redis分布式锁实现
 * 特点：
 * - 高性能，基于内存操作
 * - 支持防误删机制（UUID + Lua脚本）
 * - 支持锁过期时间，防止死锁
 * - 不支持锁重入
 */
public class RedisDistributedLock implements DistributedLock {

    private final StringRedisTemplate redisTemplate;

    // 线程本地存储，用于防误删
    private final ThreadLocal<String> lockValue = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    // Lua脚本，确保原子性释放锁
    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] " +
                    "then " +
                    "    return redis.call('del', KEYS[1]) " +
                    "else " +
                    "    return 0 " +
                    "end";

    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryLock(String key) {
        return tryLock(key, -1, null); // 永久锁
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit unit) {
        String value = lockValue.get();
        if (expire > 0 && unit != null) {
            // 带过期时间的锁
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, expire, unit));
        } else {
            // 永久锁
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
        }
    }

    @Override
    public boolean tryLock(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit) {
        long deadline = System.nanoTime() + waitUnit.toNanos(waitTime);
        long retryInterval = Math.min(100, waitUnit.toMillis(waitTime) / 10); // 重试间隔

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
        String value = lockValue.get();
        try {
            // 使用Lua脚本确保原子性释放锁
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);

            if (result == null || result == 0) {
                // 锁不存在或已过期，或者当前线程不持有锁
            }
        } finally {
            lockValue.remove();
        }
    }

    @Override
    public boolean isLocked(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 手动释放锁（用于异常情况下的清理）
     */
    public void forceUnlock(String key) {
        redisTemplate.delete(key);
    }
}