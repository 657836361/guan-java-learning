package com.guan.spring.enviroment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        MutablePropertySources propertySources = run.getEnvironment().getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            log.info("propertySource:{}", propertySource.getName());
        }
        log.info("location.content：{}", run.getEnvironment().getProperty("location.content"));
    }

}
