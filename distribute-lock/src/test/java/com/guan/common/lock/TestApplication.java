package com.guan.common.lock;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试应用配置
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class,
        org.redisson.spring.starter.RedissonAutoConfiguration.class})
@ComponentScan("com.guan.distribute")
public class TestApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(TestApplication.class, args);
    }
}
