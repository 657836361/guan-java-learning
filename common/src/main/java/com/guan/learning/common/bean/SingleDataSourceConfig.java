package com.guan.learning.common.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class SingleDataSourceConfig {

    static {
        log.info("SingleDataSourceConfig invoked");
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        log.info("SingleDateSource created");
        return DataSourceBuilder.create().build();
    }

}