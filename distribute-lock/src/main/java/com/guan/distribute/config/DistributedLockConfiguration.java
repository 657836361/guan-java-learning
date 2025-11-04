package com.guan.distribute.config;

import com.guan.distribute.core.DistributedLockTemplate;
import com.guan.distribute.redis.RedisDistributedLock;
import com.guan.distribute.redisson.RedissonDistributedLock;
import com.guan.distribute.zookeeper.ZookeeperDistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 分布式锁配置类
 * 简化的配置，只提供必要的Bean定义
 */
@Configuration
public class DistributedLockConfiguration {

    /**
     * Redis分布式锁Bean
     */
    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnMissingBean(RedisDistributedLock.class)
    public RedisDistributedLock redisDistributedLock(StringRedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

    /**
     * Redisson分布式锁Bean
     */
    @Bean
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnMissingBean(RedissonDistributedLock.class)
    public RedissonDistributedLock redissonDistributedLock(RedissonClient redissonClient) {
        return new RedissonDistributedLock(redissonClient);
    }

    /**
     * ZooKeeper分布式锁Bean
     */
    @Bean
    @ConditionalOnBean(CuratorFramework.class)
    @ConditionalOnMissingBean(ZookeeperDistributedLock.class)
    public ZookeeperDistributedLock zookeeperDistributedLock(CuratorFramework curatorFramework) {
        return new ZookeeperDistributedLock(curatorFramework);
    }

    /**
     * 分布式锁模板Bean（优先使用Redis实现）
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(DistributedLockTemplate.class)
    @ConditionalOnBean(RedisDistributedLock.class)
    public DistributedLockTemplate distributedLockTemplate(RedisDistributedLock redisDistributedLock) {
        return new DistributedLockTemplate(redisDistributedLock);
    }

    /**
     * 分布式锁模板Bean（Redisson实现）
     */
    @Bean
    @ConditionalOnMissingBean({DistributedLockTemplate.class, RedisDistributedLock.class})
    @ConditionalOnBean(RedissonDistributedLock.class)
    public DistributedLockTemplate redissonLockTemplate(RedissonDistributedLock redissonDistributedLock) {
        return new DistributedLockTemplate(redissonDistributedLock);
    }

    /**
     * 分布式锁模板Bean（ZooKeeper实现）
     */
    @Bean
    @ConditionalOnMissingBean({DistributedLockTemplate.class, RedisDistributedLock.class, RedissonDistributedLock.class})
    @ConditionalOnBean(ZookeeperDistributedLock.class)
    public DistributedLockTemplate zookeeperLockTemplate(ZookeeperDistributedLock zookeeperDistributedLock) {
        return new DistributedLockTemplate(zookeeperDistributedLock);
    }
}