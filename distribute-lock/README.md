# 分布式锁模块

## 概述

这是一个简洁、高效的分布式锁解决方案，支持Redis、Redisson、ZooKeeper三种实现方式。采用纯编程式API设计，避免切面复杂性，让锁的使用更加直观和可控。

## 项目结构

```
com.guan.distribute/
├── core/                          # 核心接口和模板
│   ├── DistributedLock.java      # 分布式锁统一接口
│   └── DistributedLockTemplate.java # 锁操作模板类
├── redis/                        # Redis实现
│   └── RedisDistributedLock.java
├── redisson/                     # Redisson实现
│   └── RedissonDistributedLock.java
├── zookeeper/                    # ZooKeeper实现
│   ├── ZookeeperDistributedLock.java
│   └── ZookeeperConfiguration.java
└── config/                       # 配置类
    └── DistributedLockConfiguration.java
```

## 快速开始

### 1. 添加依赖

```xml

<dependency>
    <groupId>com.guan.learning</groupId>
    <artifactId>distribute-lock</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 基本使用

```java

@Service
public class OrderService {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    public void createOrder(Order order) {
        // 使用模板类，自动管理锁的获取和释放
        lockTemplate.execute(
                "order:create:" + order.getUserId(),  // 锁的key
                30,                                   // 锁的过期时间
                TimeUnit.SECONDS,                     // 时间单位
                () -> {
                    // 业务逻辑：创建订单
                    saveOrder(order);
                }
        );
    }
}
```

## 三种分布式锁的详细使用

### 1. Redis分布式锁

#### 特点

- ✅ **高性能**：基于内存操作，性能最好
- ✅ **防误删**：使用UUID + Lua脚本确保只能释放自己持有的锁
- ✅ **自动过期**：支持锁过期时间，防止死锁
- ❌ **不支持重入**：同一线程无法重复获取同一个锁

#### 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: your-password
      database: 0
```

#### 使用示例

```java

@Configuration
public class RedisLockConfig {

    @Bean
    public RedisDistributedLock redisDistributedLock(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

    @Bean
    public DistributedLockTemplate redisLockTemplate(RedisDistributedLock redisLock) {
        return new DistributedLockTemplate(redisLock);
    }
}

@Service
public class UserService {

    @Autowired
    private DistributedLockTemplate lockTemplate;

    // 基本使用
    public void updateUser(String userId) {
        lockTemplate.execute(
                "user:update:" + userId,
                30, TimeUnit.SECONDS,
                () -> {
                    // 更新用户信息
                    userRepository.update(userId);
                }
        );
    }

    // 带返回值的使用
    public User getUserWithLock(String userId) {
        return lockTemplate.execute(
                "user:query:" + userId,
                10, TimeUnit.SECONDS,
                () -> {
                    return userRepository.findById(userId);
                }
        );
    }

    // 带等待时间的使用
    public boolean updateWithWait(String userId) {
        return lockTemplate.execute(
                "user:update:" + userId,    // 锁key
                5, TimeUnit.SECONDS,       // 等待5秒获取锁
                30, TimeUnit.SECONDS,      // 锁持有30秒
                () -> {
                    userRepository.update(userId);
                    return true;
                }
        );
    }

    // 直接使用Redis锁实例
    @Autowired
    private RedisDistributedLock redisLock;

    public void manualLock(String key) {
        try {
            if (redisLock.tryLock(key, 30, TimeUnit.SECONDS)) {
                // 手动管理锁
                doSomething();
            }
        } finally {
            redisLock.unlock(key);
        }
    }
}
```

### 2. Redisson分布式锁

#### 特点

- ✅ **功能丰富**：支持多种锁类型和高级特性
- ✅ **锁重入**：同一线程可重复获取同一个锁
- ✅ **公平锁**：支持按请求顺序获取锁
- ✅ **读写锁**：支持读写分离的锁机制
- ✅ **自动续期**：支持锁的自动续期
- ❌ **依赖较重**：需要Redisson框架

#### 配置

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: your-password

# Redisson配置（可选）
spring:
  redis:
    redisson:
      config: |
        singleServerConfig:
          address: redis://localhost:6379
          password: your-password
          database: 0
```

#### 使用示例

```java

@Service
public class InventoryService {

    @Autowired
    private RedissonDistributedLock redissonLock;

    // 基本使用（支持重入）
    public void updateInventory(String productId) {
        redissonLock.tryLock(
                "inventory:update:" + productId,
                30, TimeUnit.SECONDS
        );
        try {
            // 业务逻辑
            inventoryRepository.update(productId);
            // 支持锁重入，可以调用其他也需要同一把锁的方法
            updateRelatedData(productId);
        } finally {
            redissonLock.unlock("inventory:update:" + productId);
        }
    }

    // 使用公平锁
    public boolean fairUpdate(String productId) {
        return redissonLock.tryFairLock(
                "inventory:fair:" + productId,
                30, TimeUnit.SECONDS
        );
    }

    // 使用读写锁
    public void writeInventory(String productId) {
        if (redissonLock.tryWriteLock("inventory:rw:" + productId, 30, TimeUnit.SECONDS)) {
            try {
                // 写操作
                inventoryRepository.save(productId);
            } finally {
                redissonLock.unlock("inventory:rw:" + productId);
            }
        }
    }

    public Inventory readInventory(String productId) {
        if (redissonLock.tryReadLock("inventory:rw:" + productId, 10, TimeUnit.SECONDS)) {
            try {
                // 读操作（多个读锁可以同时获取）
                return inventoryRepository.findById(productId);
            } finally {
                redissonLock.unlock("inventory:rw:" + productId);
            }
        }
        return null;
    }
}
```

### 3. ZooKeeper分布式锁

#### 特点

- ✅ **高可靠性**：基于ZooKeeper的强一致性保证
- ✅ **锁重入**：支持同一线程的重入
- ✅ **公平锁**：天然支持按请求顺序获取锁
- ✅ **会话机制**：客户端断开连接时自动释放锁
- ❌ **性能较低**：网络开销较大，性能不如Redis

#### 配置

```yaml
zookeeper:
  connect-string: localhost:2181
  namespace: distribute-lock
  session-timeout-ms: 60000
  connection-timeout-ms: 15000
  base-sleep-time-ms: 1000
  max-sleep-time-ms: 30000
  max-retries: 3
```

#### 使用示例

```java

@Service
public class ConfigService {

    @Autowired
    private ZookeeperDistributedLock zkLock;

    // 基本使用
    public void updateConfig(String configKey) {
        if (zkLock.tryLock("config:update:" + configKey, 30, TimeUnit.SECONDS)) {
            try {
                // 更新配置
                configRepository.update(configKey);
            } finally {
                zkLock.unlock("config:update:" + configKey);
            }
        }
    }

    // 配合模板使用
    @Autowired
    private DistributedLockTemplate zkLockTemplate;

    public Config updateWithTemplate(String configKey) {
        return zkLockTemplate.execute(
                "config:update:" + configKey,
                30, TimeUnit.SECONDS,
                () -> {
                    // 更新并返回配置
                    return configRepository.updateAndReturn(configKey);
                }
        );
    }

    // 强制释放锁（异常情况使用）
    public void emergencyUnlock(String configKey) {
        zkLock.forceUnlock("config:update:" + configKey);
    }
}
```

## 三种锁的对比和选择

| 特性    | Redis | Redisson | ZooKeeper |
|-------|-------|----------|-----------|
| 性能    | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐     | ⭐⭐        |
| 可靠性   | ⭐⭐⭐   | ⭐⭐⭐⭐     | ⭐⭐⭐⭐⭐     |
| 锁重入   | ❌     | ✅        | ✅         |
| 公平锁   | ❌     | ✅        | ✅         |
| 读写锁   | ❌     | ✅        | ❌         |
| 自动续期  | ❌     | ✅        | ✅（会话机制）   |
| 依赖复杂度 | 低     | 中        | 高         |
| 适用场景  | 高并发场景 | 功能复杂场景   | 高可靠性场景    |

### 选择建议

1. **选择Redis分布式锁**：
    - 对性能要求高
    - 业务逻辑简单，不需要锁重入
    - 已有Redis基础设施

2. **选择Redisson分布式锁**：
    - 需要锁重入功能
    - 需要公平锁或读写锁
    - 需要联锁、红锁等高级特性
    - 对功能完整性要求高

3. **选择ZooKeeper分布式锁**：
    - 对可靠性要求极高
    - 需要强一致性保证
    - 需要会话管理和自动释放
    - 可以接受较低的性能

## 最佳实践

### 1. 锁的Key设计

```java
// 好的key设计
String orderLockKey = "order:create:" + order.getUserId();
String inventoryLockKey = "inventory:update:" + productId;
String userLockKey = "user:profile:" + userId;

// 避免的key设计
String badKey1 = "lock";  // 太通用，容易冲突
String badKey2 = "order:" + order.toString();  // 可能包含敏感信息
```

### 2. 过期时间设置

```java
// 根据业务处理时间设置合理的过期时间
lockTemplate.execute(
    "quick:operation",
            5,TimeUnit.SECONDS,      // 快速操作5秒
    () ->

quickTask()
);

        lockTemplate.

execute(
    "complex:operation",
            60,TimeUnit.SECONDS,     // 复杂操作60秒
    () ->

complexTask()
);
```

### 3. 异常处理

```java
public void safeOperation(String key) {
    try {
        boolean success = lockTemplate.tryExecute(
                key, 30, TimeUnit.SECONDS,
                () -> doBusiness()
        );

        if (!success) {
            // 获取锁失败的处理
            throw new BusinessException("系统繁忙，请稍后重试");
        }
    } catch (Exception e) {
        // 业务异常处理
        logger.error("业务处理失败", e);
        throw e;
    }
}
```

### 4. 锁监控

```java

@Component
public class LockMonitor {

    private final MeterRegistry meterRegistry;
    private final Counter lockSuccessCounter;
    private final Counter lockFailureCounter;

    public LockMonitor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.lockSuccessCounter = Counter.builder("lock.success")
                .description("锁获取成功次数")
                .register(meterRegistry);
        this.lockFailureCounter = Counter.builder("lock.failure")
                .description("锁获取失败次数")
                .register(meterRegistry);
    }

    public void recordLockSuccess(String lockType) {
        lockSuccessCounter.increment(Tags.of("type", lockType));
    }

    public void recordLockFailure(String lockType) {
        lockFailureCounter.increment(Tags.of("type", lockType));
    }
}
```

## 注意事项

1. **避免死锁**：始终设置合理的过期时间
2. **锁粒度**：锁的粒度要适中，太细可能导致死锁，太粗影响并发
3. **资源释放**：确保在finally块中释放锁
4. **异常处理**：妥善处理获取锁失败的情况
5. **性能监控**：监控锁的获取成功率和等待时间

## 总结

本分布式锁模块通过简洁的设计和清晰的API，提供了易于使用的分布式锁解决方案。根据业务场景选择合适的锁实现，并遵循最佳实践，可以有效解决分布式环境下的并发控制问题。