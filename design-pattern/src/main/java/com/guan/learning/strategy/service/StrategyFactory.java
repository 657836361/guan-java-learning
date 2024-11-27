package com.guan.learning.strategy.service;

import com.guan.learning.strategy.enums.StrategyEnum;
import com.guan.learning.strategy.pojo.context.BaseContext;

import java.util.HashMap;
import java.util.Map;

public class StrategyFactory {
    private static final Map<StrategyEnum, AbstractProcessService<? extends BaseContext>> MAP = new HashMap<>();


    public static <T extends BaseContext> void put(StrategyEnum strategyEnum, AbstractProcessService<T> strategy) {
        MAP.put(strategyEnum, strategy);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseContext> AbstractProcessService<T> get(StrategyEnum strategyEnum) {
        return (AbstractProcessService<T>) MAP.get(strategyEnum);
    }
}
