package com.guan.learning.designPattern.strategy.pojo.request;

import com.guan.learning.designPattern.strategy.enums.StrategyEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRequest {
    private StrategyEnum strategyEnum;
}
