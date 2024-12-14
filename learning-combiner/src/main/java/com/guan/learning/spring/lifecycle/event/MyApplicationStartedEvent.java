package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：在ApplicationContext已经刷新（refresh）完成，
 * 并且所有的CommandLineRunner和ApplicationRunner接口实现类还没有被调用之前触发。
 * 此时，应用已经基本启动完成，所有的单例 Bean 已经被初始化和配置完成。
 * 这个事件可以用于在应用完全启动后，执行一些初始化后的任务，比如启动一些后台服务等。
 */
@Slf4j
@Component
public class MyApplicationStartedEvent implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("ApplicationStartedEvent");
    }
}
