package com.guan.example;

import com.guan.distribute.core.DistributedLockTemplate;
import com.guan.distribute.redis.RedisDistributedLock;
import com.guan.distribute.redisson.RedissonDistributedLock;
import com.guan.distribute.zookeeper.ZookeeperDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁使用示例
 * 演示三种锁的基本用法
 */
@Service
public class LockUsageExample {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    // 如果需要直接使用特定的锁实现，可以注入
    @Autowired(required = false)
    private RedisDistributedLock redisLock;

    @Autowired(required = false)
    private RedissonDistributedLock redissonLock;

    @Autowired(required = false)
    private ZookeeperDistributedLock zkLock;

    /**
     * 示例1：使用模板类的基本用法
     */
    public void basicExample() {
        // 自动管理锁的获取和释放
        lockTemplate.execute(
                "example:basic",                    // 锁的key
                30, TimeUnit.SECONDS,              // 锁的过期时间
                () -> {
                    System.out.println("执行业务逻辑");
                    // 这里放置需要加锁的业务代码
                }
        );
    }

    /**
     * 示例2：带返回值的锁操作
     */
    public String withReturnValue() {
        return lockTemplate.execute(
                "example:return",
                10, TimeUnit.SECONDS,
                () -> {
                    System.out.println("执行业务逻辑并返回结果");
                    return "操作结果";
                }
        );
    }

    /**
     * 示例3：带等待时间的锁操作
     */
    public boolean withWaitTime() {
        return lockTemplate.execute(
                "example:wait",                     // 锁的key
                5, TimeUnit.SECONDS,               // 等待获取锁的时间
                30, TimeUnit.SECONDS,              // 锁的过期时间
                () -> {
                    System.out.println("等待5秒获取锁后执行业务逻辑");
                    return true;
                }
        );
    }

    /**
     * 示例4：尝试执行并检查结果
     */
    public void tryExecuteExample() {
        boolean success = lockTemplate.tryExecute(
                "example:try",
                30, TimeUnit.SECONDS,
                () -> {
                    System.out.println("尝试执行业务逻辑");
                }
        );

        if (!success) {
            System.out.println("获取锁失败，业务逻辑未执行");
        } else {
            System.out.println("业务逻辑执行成功");
        }
    }

    /**
     * 示例5：直接使用Redis锁
     */
    public void directRedisLock() {
        if (redisLock != null) {
            if (redisLock.tryLock("example:redis", 30, TimeUnit.SECONDS)) {
                try {
                    System.out.println("使用Redis锁执行业务逻辑");
                } finally {
                    redisLock.unlock("example:redis");
                }
            }
        } else {
            System.out.println("Redis锁未配置");
        }
    }

    /**
     * 示例6：直接使用Redisson锁（支持重入）
     */
    public void directRedissonLock() {
        if (redissonLock != null) {
            if (redissonLock.tryLock("example:redisson", 30, TimeUnit.SECONDS)) {
                try {
                    System.out.println("使用Redisson锁执行业务逻辑");
                    // Redisson支持锁重入
                    nestedMethod();
                } finally {
                    redissonLock.unlock("example:redisson");
                }
            }
        } else {
            System.out.println("Redisson锁未配置");
        }
    }

    private void nestedMethod() {
        if (redissonLock != null) {
            // 锁重入示例
            if (redissonLock.tryLock("example:redisson", 10, TimeUnit.SECONDS)) {
                try {
                    System.out.println("锁重入成功，执行嵌套方法");
                } finally {
                    redissonLock.unlock("example:redisson");
                }
            }
        }
    }

    /**
     * 示例7：直接使用ZooKeeper锁
     */
    public void directZookeeperLock() {
        if (zkLock != null) {
            if (zkLock.tryLock("example:zk", 30, TimeUnit.SECONDS)) {
                try {
                    System.out.println("使用ZooKeeper锁执行业务逻辑");
                } finally {
                    zkLock.unlock("example:zk");
                }
            }
        } else {
            System.out.println("ZooKeeper锁未配置");
        }
    }

    /**
     * 示例8：锁状态检查
     */
    public void checkLockStatus() {
        String lockKey = "example:check";

        if (redisLock != null) {
            boolean locked = redisLock.isLocked(lockKey);
            System.out.println("Redis锁状态 - " + lockKey + ": " + (locked ? "已锁定" : "未锁定"));
        }

        if (redissonLock != null) {
            boolean locked = redissonLock.isLocked(lockKey);
            System.out.println("Redisson锁状态 - " + lockKey + ": " + (locked ? "已锁定" : "未锁定"));
        }

        if (zkLock != null) {
            boolean locked = zkLock.isLocked(lockKey);
            System.out.println("ZooKeeper锁状态 - " + lockKey + ": " + (locked ? "已锁定" : "未锁定"));
        }
    }
}