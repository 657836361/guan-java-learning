package com.guan.learning.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Java 16 引入了 Record 类型（JEP 395）。Record 类是不可变数据的透明载体。
 * 这使它们成为配置持有者和 DTO 的理想选择。
 * 事实上，可以在 Spring Boot 中将 Java Record 定义为配置属性。
 * 从 Spring Boot 2.6 开始，对于单构造函数 Record，可以不用 @ConstructorBinding 注解。
 * 但是，如果 Record 有多个构造函数，则仍应使用 @ConstructorBinding 来标识用于属性绑定的构造函数。
 * <p>
 * record不需要get/set方法声明
 */
@ConfigurationProperties("mock")
public record Java16RecordConfigProperties(String privateKey) {
}
