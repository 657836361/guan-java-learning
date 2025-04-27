package com.guan.spring.enviroment.aop.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMapper {

    public void doMapper(int i) {
        if (i == 1) {
            throw new RuntimeException("test");
        }
        log.info("doMapper");
    }
}
