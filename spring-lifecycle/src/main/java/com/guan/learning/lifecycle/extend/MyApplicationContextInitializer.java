package com.guan.learning.lifecycle.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextInitializer用于在ApplicationContext被刷新（refresh）之前对其进行初始化操作。
 * 这是一个非常强大的扩展点，可以用于设置环境变量、添加配置源、或者对应用上下文的初始化过程进行其他自定义的操作。
 * 它可以在 Spring 应用启动的早期阶段就介入，影响整个应用上下文的初始化过程。
 */
@Slf4j
@Component
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("MyApplicationContextInitializer");
    }
}
