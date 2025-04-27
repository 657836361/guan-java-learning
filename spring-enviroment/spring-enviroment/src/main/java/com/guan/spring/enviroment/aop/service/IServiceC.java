package com.guan.spring.enviroment.aop.service;

import com.guan.spring.enviroment.aop.config.Mark;

public interface IServiceC {

    @Mark
    void doServiceC(int i);

    void doServiceC1(int i);

    @Mark
    void doServiceC2(int i);
}
