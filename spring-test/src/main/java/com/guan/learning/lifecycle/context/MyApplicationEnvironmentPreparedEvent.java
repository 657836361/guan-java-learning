package com.guan.learning.lifecycle.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：在SpringApplication准备好环境（Environment）之后，
 * 但在创建应用上下文（ApplicationContext）之前触发。
 * 此时，配置文件已经被加载，环境变量、系统属性等都已经被解析并设置到Environment对象中。
 * 这个事件可以用于在应用上下文创建之前，根据环境信息来调整配置或者执行一些特定于环境的初始化操作。
 */
@Slf4j
@Component
public class MyApplicationEnvironmentPreparedEvent implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("ApplicationEnvironmentPreparedEvent");
    }
}
