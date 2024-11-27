package com.guan.learning.strategy.enums;

import lombok.Getter;

@Getter
public enum StrategyEnum {

    AUDIT,
    DEAL,
    ;

    public static StrategyEnum getEnum(String code) {
        for (StrategyEnum anEnum : StrategyEnum.values()) {
            // ds
        }
        return null;
    }
}
