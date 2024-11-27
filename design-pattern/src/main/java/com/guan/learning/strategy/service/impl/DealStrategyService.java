package com.guan.learning.strategy.service.impl;

import com.guan.learning.strategy.enums.StrategyEnum;
import com.guan.learning.strategy.pojo.context.DealContext;
import com.guan.learning.strategy.service.AbstractProcessService;
import com.guan.learning.strategy.service.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DealStrategyService extends AbstractProcessService<DealContext> {
    @Override
    public void doProcess(DealContext param) {
        log.info("deal");
    }

    @Override
    protected void beforeProcess(DealContext context) {
        log.info(context.getRemark());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyFactory.put(StrategyEnum.DEAL, this);
    }
}
