package com.guan.learning;

import com.guan.learning.anno.EnableDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @see EnableDataSource
 */
//@EnableDataSource(mode = DataSourceMode.NORMAL)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 两种都行
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = SpringLifeTypeFilter.class))
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.guan.learning.spring.lifecycle.*"))
public class LearningCombinerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCombinerApplication.class, args);
    }

}
