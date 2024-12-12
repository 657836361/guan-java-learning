package com.guan.learning.mybatisplus;

import com.guan.learning.anno.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicDataSource
@SpringBootApplication
public class LearningCombinerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCombinerApplication.class, args);
    }

}
