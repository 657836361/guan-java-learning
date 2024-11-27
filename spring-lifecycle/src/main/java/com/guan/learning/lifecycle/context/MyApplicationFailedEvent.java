package com.guan.learning.lifecycle.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：当应用启动过程中出现异常导致启动失败时触发。
 * 这个事件可以用于记录启动失败的详细信息，比如异常堆栈信息等，以便于后续的故障排查。
 */
@Slf4j
@Component
public class MyApplicationFailedEvent implements ApplicationListener<ApplicationFailedEvent> {
    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        log.info("ApplicationFailedEvent");
    }
}
