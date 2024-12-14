package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：这个事件是在 Spring Boot 应用启动的最开始阶段触发，在创建和准备ApplicationContext之前。
 * 它主要用于系统初始化的一些非常早期的任务，例如记录启动开始的日志等。
 * 例如，你可以在这个阶段设置一些系统级别的属性，这些属性会在后续的应用上下文创建过程中发挥作用。
 */
@Slf4j
@Component
public class MyApplicationStartingEvent implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        log.info("ApplicationStartingEvent");
    }
}
