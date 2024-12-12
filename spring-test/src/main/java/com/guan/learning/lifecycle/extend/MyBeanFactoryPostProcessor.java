package com.guan.learning.lifecycle.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * BeanFactoryPostProcessor是在 Spring 容器加载了 Bean 的定义信息之后，在 Bean 实例化之前执行的扩展接口。
 * 它可以用于修改 Bean 的定义，例如修改 Bean 的属性值、添加或删除 Bean 定义等。通过这种方式，
 * 可以在不修改原始 Bean 代码的情况下，对 Bean 的配置进行动态调整。
 */
@Slf4j
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsBean("targetService")) {
            BeanDefinition targetService = beanFactory.getBeanDefinition("targetService");
            // 假设这里获取DataSource的Bean定义并修改其属性
            log.info(targetService.getBeanClassName());
        }
    }
}
