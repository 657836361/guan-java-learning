package com.guan.learning.common.bean;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringBoot：使用Jackson完成全局序列化配置
 * 序列化枚举值为前端返回值
 * Jackson 重写 toString 方法
 */
@Configuration
public class EnumJacksonCustomizer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }
}
