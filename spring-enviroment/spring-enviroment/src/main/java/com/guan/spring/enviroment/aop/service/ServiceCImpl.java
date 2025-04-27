package com.guan.spring.enviroment.aop.service;

import com.guan.spring.enviroment.aop.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceCImpl implements IServiceC {

    @Autowired
    private TransactionMapper mapper;


    @Override
    public final void doServiceC(int i) {
        mapper.doMapper(i);
    }

    @Override
    public void doServiceC1(int i) {
        mapper.doMapper(i);
    }

    @Override
    public void doServiceC2(int i) {
        mapper.doMapper(i);
    }


}
