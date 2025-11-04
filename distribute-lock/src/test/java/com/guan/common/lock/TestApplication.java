package com.guan.common.lock;

import com.guan.common.lock.annotation.EnableDistributeLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试应用配置
 */
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
@EnableDistributeLock
@ComponentScan("com.guan.common.lock")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}