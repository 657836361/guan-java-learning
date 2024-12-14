package com.guan.learning.spring.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TargetService implements InitializingBean, DisposableBean, BeanNameAware, ApplicationContextAware, SmartInitializingSingleton {

    @Autowired
    private FactoryBeanTargetService service;

    private String beanName;
    private ApplicationContext applicationContext;

    public void doSomething() {
        log.info("UserService is doing something.{}", service.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        log.info("TargetService setApplicationContext");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        log.info("TargetService setBeanName");
    }

    @Override
    public void destroy() throws Exception {
        log.info("TargetService destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("TargetService afterPropertiesSet");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("TargetService afterSingletonsInstantiated");
    }
}
