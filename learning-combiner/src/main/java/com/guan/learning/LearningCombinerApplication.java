package com.guan.learning;

import com.guan.learning.anno.EnableDataSource;
import com.guan.learning.spring.lifecycle.SpringLifeTypeFilter;
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
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = SpringLifeTypeFilter.class))
public class LearningCombinerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCombinerApplication.class, args);
    }

}
