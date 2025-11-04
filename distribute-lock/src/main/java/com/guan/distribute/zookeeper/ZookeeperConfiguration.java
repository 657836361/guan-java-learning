package com.guan.distribute.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ZooKeeper配置类
 * 简化的配置，提供基本的Curator客户端配置
 */
@Configuration
@EnableConfigurationProperties(ZookeeperConfiguration.ZookeeperProperties.class)
public class ZookeeperConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RetryPolicy retryPolicy(ZookeeperProperties properties) {
        return new ExponentialBackoffRetry(
                properties.getBaseSleepTimeMs(),
                properties.getMaxRetries(),
                properties.getMaxSleepTimeMs()
        );
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy, ZookeeperProperties properties) {
        return CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectString())
                .namespace(properties.getNamespace())
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(properties.getSessionTimeoutMs())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .build();
    }

    /**
     * ZooKeeper配置属性
     */
    @ConfigurationProperties(prefix = "zookeeper")
    public static class ZookeeperProperties {
        private String connectString = "localhost:2181";
        private String namespace = "distribute-lock";
        private int sessionTimeoutMs = 60000;
        private int connectionTimeoutMs = 15000;
        private int baseSleepTimeMs = 1000;
        private int maxSleepTimeMs = 30000;
        private int maxRetries = 3;

        // Getters and Setters
        public String getConnectString() {
            return connectString;
        }

        public void setConnectString(String connectString) {
            this.connectString = connectString;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public int getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public int getConnectionTimeoutMs() {
            return connectionTimeoutMs;
        }

        public void setConnectionTimeoutMs(int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
        }

        public int getBaseSleepTimeMs() {
            return baseSleepTimeMs;
        }

        public void setBaseSleepTimeMs(int baseSleepTimeMs) {
            this.baseSleepTimeMs = baseSleepTimeMs;
        }

        public int getMaxSleepTimeMs() {
            return maxSleepTimeMs;
        }

        public void setMaxSleepTimeMs(int maxSleepTimeMs) {
            this.maxSleepTimeMs = maxSleepTimeMs;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }
    }
}