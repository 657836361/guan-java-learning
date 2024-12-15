package com.guan.learning;

import com.guan.learning.dynamic.anno.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 是否启动动态数据源取决于是否使用该注解
 *
 * @see EnableDynamicDataSource
 */
//@EnableDynamicDataSource
@SpringBootApplication
public class LearningCombinerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCombinerApplication.class, args);
    }

}
