package com.guan.learning;

import com.guan.learning.anno.EnableDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @see EnableDataSource
 */
//@EnableDataSource(mode = DataSourceMode.NORMAL)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LearningCombinerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCombinerApplication.class, args);
    }

}
