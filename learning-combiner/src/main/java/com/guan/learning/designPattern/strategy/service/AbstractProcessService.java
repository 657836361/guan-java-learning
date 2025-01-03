package com.guan.learning.designPattern.strategy.service;

import com.guan.learning.designPattern.strategy.pojo.context.BaseContext;
import org.springframework.beans.factory.InitializingBean;


public abstract class AbstractProcessService<T extends BaseContext> implements IStrategy<T>, InitializingBean {


    public final void process(T context) {
        beforeProcess(context);
        doProcess(context);
        afterProcess(context);
    }

    protected void afterProcess(T context) {
    }

    protected void beforeProcess(T context) {

    }
}
