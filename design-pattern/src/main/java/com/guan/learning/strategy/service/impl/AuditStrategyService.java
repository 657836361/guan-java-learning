package com.guan.learning.strategy.service.impl;

import com.guan.learning.strategy.enums.StrategyEnum;
import com.guan.learning.strategy.pojo.context.AuditContext;
import com.guan.learning.strategy.service.AbstractProcessService;
import com.guan.learning.strategy.service.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuditStrategyService extends AbstractProcessService<AuditContext> {
    @Override
    public void doProcess(AuditContext param) {
        log.info("audit");
    }

    @Override
    protected void beforeProcess(AuditContext context) {
        log.info(context.getAuditResult());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyFactory.put(StrategyEnum.AUDIT, this);
    }
}
