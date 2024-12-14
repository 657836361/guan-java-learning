package com.guan.learning.mybatisplus.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan(basePackages = {"com.guan.learning.mybatisplus.mapper", "com.guan.learning.common.dict.mapper"})
public class MapperScanConfig {
}
