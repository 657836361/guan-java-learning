package com.guan.learning.spring.lifecycle.extend;

import com.guan.learning.spring.lifecycle.FactoryBeanTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * FactoryBean是一个工厂 Bean 接口，用于创建复杂的 Bean 对象。
 * 与普通的 Bean 不同，FactoryBean接口定义了getObject方法，该方法返回实际的 Bean 实例。
 * 通过实现FactoryBean，可以在getObject方法中进行复杂的对象创建逻辑，例如创建连接池、代理对象等。
 * 同时，FactoryBean还提供了获取对象类型和控制单例模式的方法。
 */
@Slf4j
@Component
public class MyFactoryBean implements FactoryBean<FactoryBeanTargetService> {

    @Override
    public FactoryBeanTargetService getObject() throws Exception {
        return new FactoryBeanTargetService("guan");
    }

    @Override
    public Class<?> getObjectType() {
        return FactoryBeanTargetService.class;
    }
}
