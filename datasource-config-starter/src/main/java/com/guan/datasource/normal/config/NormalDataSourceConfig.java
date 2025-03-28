package com.guan.datasource.normal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
public class NormalDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.config.datasource")
    public DataSource dataSource() {
        log.info("normalDateSource created");
        return DataSourceBuilder.create().build();
    }

}