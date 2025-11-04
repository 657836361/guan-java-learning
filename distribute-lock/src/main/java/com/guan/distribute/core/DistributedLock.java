package com.guan.distribute.core;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁统一接口
 * 提供基本的分布式锁操作
 */
public interface DistributedLock {

    /**
     * 尝试获取锁（永久锁，需要手动释放）
     *
     * @param key 锁的key
     * @return 是否成功获取锁
     */
    boolean tryLock(String key);

    /**
     * 尝试获取带过期时间的锁
     *
     * @param key    锁的key
     * @param expire 锁的过期时间
     * @param unit   过期时间单位
     * @return 是否成功获取锁
     */
    boolean tryLock(String key, long expire, TimeUnit unit);

    /**
     * 释放锁
     *
     * @param key 锁的key
     */
    void unlock(String key);

    /**
     * 检查锁是否存在
     *
     * @param key 锁的key
     * @return 锁是否存在
     */
    boolean isLocked(String key);

    /**
     * 尝试获取锁，并等待指定时间
     *
     * @param key        锁的key
     * @param waitTime   等待获取锁的最大时间
     * @param waitUnit   等待时间单位
     * @param expire     锁的过期时间
     * @param expireUnit 过期时间单位
     * @return 是否成功获取锁
     */
    boolean tryLock(String key, long waitTime, TimeUnit waitUnit, long expire, TimeUnit expireUnit);
}