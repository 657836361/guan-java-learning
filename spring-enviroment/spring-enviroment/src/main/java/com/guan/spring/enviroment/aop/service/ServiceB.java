package com.guan.spring.enviroment.aop.service;

import com.guan.spring.enviroment.aop.config.Mark;
import com.guan.spring.enviroment.aop.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceB {
    @Autowired
    private TransactionMapper mapper;

    @Mark
    public final void doServiceB(int i) {
        mapper.doMapper(i);
    }

    @Mark
    public void doServiceB1(int i) {
        mapper.doMapper(i);
    }
}
