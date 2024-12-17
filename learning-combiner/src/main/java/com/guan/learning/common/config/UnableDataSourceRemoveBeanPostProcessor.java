package com.guan.learning.common.config;

import com.guan.learning.LearningCombinerApplication;
import com.guan.learning.anno.EnableDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @EnableDataSource 不生效或不存在时时移除MapperScanConfig的BeanDefinition
 */
@Slf4j
@Component
public class UnableDataSourceRemoveBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        EnableDataSource enableDataSource = AnnotationUtils.findAnnotation(LearningCombinerApplication.class, EnableDataSource.class);
        if (enableDataSource == null) {
            try {
                registry.removeBeanDefinition("mapperScanConfig");
            } catch (NoSuchBeanDefinitionException e) {
                log.warn("mapperScanConfig bean not exists");
            }
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
