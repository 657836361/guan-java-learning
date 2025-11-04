package com.guan.common.lock.impl;

import com.guan.common.lock.LockOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisDistributeLock implements LockOperation {

    private final StringRedisTemplate redisTemplate;

    // 存储线程标识的Map
    private final ThreadLocal<String> lockFlag = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    // Lua脚本确保原子性操作
    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] " +
                    "then " +
                    "    return redis.call('del', KEYS[1]) " +
                    "else " +
                    "    return 0 " +
                    "end";

    public RedisDistributeLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryLock(String key) {
        String lockValue = lockFlag.get();
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, lockValue));
    }

    @Override
    public boolean tryLock(String key, long expire, TimeUnit timeUnit) {
        String lockValue = lockFlag.get();
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, lockValue, expire, timeUnit));
    }

    @Override
    public void unlock(String key) {
        String lockValue = lockFlag.get();
        try {
            // 使用Lua脚本确保原子性删除
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), lockValue);

            // 记录释放结果（可以后续添加日志）
            if (result == null || result == 0) {
                // 锁不存在或已过期
            } else {
                // 成功释放锁
            }
        } finally {
            // 清理ThreadLocal
            lockFlag.remove();
        }
    }
}
