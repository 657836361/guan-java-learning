package com.guan.learning.designPattern.strategy.service;


import com.guan.learning.designPattern.strategy.pojo.context.BaseContext;

public interface IStrategy<T extends BaseContext> {

    void doProcess(T param);
}
