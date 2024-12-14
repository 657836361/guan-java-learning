package com.guan.learning.spring.lifecycle.imports.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class MytBeanImporConfiguration {

    static {
        log.info("MytBeanImporConfiguration static init");
    }

    @Bean
    public MyImportBeanA myImportBeanA() {
        log.info("MytBeanImporConfiguration myImportBeanA");
        return new MyImportBeanA();
    }

    @Bean
    public MyImportBeanB myImportBeanB() {
        log.info("MytBeanImporConfiguration myImportBeanB");
        return new MyImportBeanB();
    }

    public static class MyImportBeanA {
    }

    public static class MyImportBeanB {
    }
}
