package com.guan.learning.common;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 使用@mapper注解 而不是在主项目类路径下使用@mapperscan
 */
@Slf4j
@MapperScan(basePackages = "com.guan.learning")
public class MapperScanConfig {
}
