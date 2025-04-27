package com.guan.spring.enviroment.aop.service;

import com.guan.spring.enviroment.aop.config.Mark;
import com.guan.spring.enviroment.aop.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceANotFinalMethod implements IServiceA {

    @Autowired
    private TransactionMapper mapper;

    @Mark
    @Override
    public void doServiceA(int i) {
        mapper.doMapper(i);
    }
}
