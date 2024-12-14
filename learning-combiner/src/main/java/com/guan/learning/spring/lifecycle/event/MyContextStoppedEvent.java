package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyContextStoppedEvent implements ApplicationListener<ContextStoppedEvent> {
    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        log.info("ContextStoppedEvent");
    }
}
