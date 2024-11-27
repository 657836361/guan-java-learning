package com.guan.learning.strategy.controller;

import com.guan.learning.strategy.enums.StrategyEnum;
import com.guan.learning.strategy.pojo.context.AuditContext;
import com.guan.learning.strategy.pojo.context.DealContext;
import com.guan.learning.strategy.pojo.request.AuditRequest;
import com.guan.learning.strategy.pojo.request.DealRequest;
import com.guan.learning.strategy.pojo.request.ProcessRequest;
import com.guan.learning.strategy.service.AbstractProcessService;
import com.guan.learning.strategy.service.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/strategy")
public class StrategyController {

    @GetMapping("/deal")
    public void deal(@RequestBody DealRequest request) {
        AbstractProcessService<DealContext> service = StrategyFactory.get(StrategyEnum.DEAL);
        service.process(request.context());
    }

    @GetMapping("/audit")
    public void audit(@RequestBody AuditRequest request) {
        AbstractProcessService<AuditContext> service = StrategyFactory.get(StrategyEnum.AUDIT);
        service.process(request.context());
    }

    @GetMapping("/process")
    public void audit(@RequestBody ProcessRequest request) {
        log.info("{}",request.getStrategyEnum());
    }
}
