package com.guan.learning.spring.lifecycle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 触发时间：在ApplicationContext已经创建完成，但还没有被刷新（refresh）之前触发。
 * 此时，Bean 定义已经被加载到应用上下文中，但 Bean 还没有被实例化和初始化。
 * 这个事件可以用于在 Bean 初始化之前，对应用上下文进行一些最后的调整，
 * 比如添加额外的 Bean 定义或者修改已有的 Bean 定义。
 */
@Slf4j
@Component
public class MyApplicationPreparedEvent implements ApplicationListener<ApplicationPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        log.info("ApplicationPreparedEvent");
    }
}
