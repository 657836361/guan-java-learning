package com.guan.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 分布式锁模块示例应用
 * 演示如何集成和使用分布式锁功能
 * <p>
 * 特性：
 * - 支持Redis、Redisson、ZooKeeper三种分布式锁实现
 * - 简洁的编程式API，无切面复杂度
 * - 自动配置，开箱即用
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

