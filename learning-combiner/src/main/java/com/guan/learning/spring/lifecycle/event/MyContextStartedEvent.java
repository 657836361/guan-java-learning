package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyContextStartedEvent implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        log.info("ContextStartedEvent");
    }
}
