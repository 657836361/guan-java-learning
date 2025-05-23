package com.guan.datasource.dynamic.config;

import com.guan.datasource.dynamic.DynamicDataSource;
import com.guan.datasource.dynamic.enums.DataSourceFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicDateSourceConfig {

    @Bean("masterDataSource")
    @ConfigurationProperties("spring.config.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties("spring.config.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource createDynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                                     @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceFlagEnum.MASTER, masterDataSource);
        dataSourceMap.put(DataSourceFlagEnum.SLAVE, slaveDataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        log.info("DynamicDateSource created");
        return dynamicDataSource;
    }

}
