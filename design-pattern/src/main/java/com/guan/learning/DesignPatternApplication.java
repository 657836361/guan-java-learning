package com.guan.learning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@ServletComponentScan
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DesignPatternApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignPatternApplication.class, args);
    }
}
