package com.guan.learning.spring.lifecycle.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BeanPostProcessor是 Spring 框架中一个重要的扩展点，它允许在 Bean 的初始化过程中进行自定义的处理。
 * 在 Spring 容器创建 Bean 实例后，会在初始化方法（如afterPropertiesSet或带有@PostConstruct注解的方法）
 * 被调用之前和之后分别调用BeanPostProcessor的
 * postProcessBeforeInitialization和postProcessAfterInitialization方法。
 */
@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("targetService")) {
            log.info("MyBeanPostProcessor postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("targetService")) {
            log.info("MyBeanPostProcessor postProcessAfterInitialization");
        }
        return bean;
    }
}
