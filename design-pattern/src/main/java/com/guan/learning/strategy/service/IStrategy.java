package com.guan.learning.strategy.service;

import com.guan.learning.strategy.pojo.context.BaseContext;

public interface IStrategy<T extends BaseContext> {

    void doProcess(T param);
}
