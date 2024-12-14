package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：在ApplicationContext已经刷新（refresh）完成，
 * 并且所有的CommandLineRunner和ApplicationRunner接口实现类已经被调用之后触发。
 * 这意味着应用已经完全启动并且准备好接收请求或者执行任务了。
 * 这个事件通常用于在应用完全准备好后，执行一些与应用运行相关的任务，比如发送应用启动成功的通知等。
 */
@Slf4j
@Component
public class MyApplicationReadyEvent implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyEvent");
    }
}
