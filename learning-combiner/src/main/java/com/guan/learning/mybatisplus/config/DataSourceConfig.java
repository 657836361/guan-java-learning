package com.guan.learning.mybatisplus.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = {"com.guan.learning.mybatisplus.mapper", "com.guan.learning.common.dict.mapper"})
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties("spring.datasource")
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
