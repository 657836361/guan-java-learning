package com.guan.learning.spring.lifecycle.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 对标准BeanFactory PostProcessor SPI的扩展，允许在常规BeanFactory Postprocessor检测开始之前注册更多的bean定义。
 * 特别是，BeanDefinitionRegistryPostProcessor可以注册更多的bean定义，进而定义BeanFactory PostProcessing实例。
 */
@Slf4j
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("MyBeanDefinitionRegistryPostProcessor");
    }
}
