package com.guan.spring.enviroment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringEnviromentShow implements SmartInitializingSingleton {
    @Autowired
    private ConfigurableEnvironment environment;

    @Override
    public void afterSingletonsInstantiated() {
        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            log.info("propertySource:{}", propertySource.getName());
        }
        log.info("location.content：{}", environment.getProperty("location.content"));

    }
}
