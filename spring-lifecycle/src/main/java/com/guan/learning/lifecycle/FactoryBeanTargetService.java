package com.guan.learning.lifecycle;

import lombok.Getter;

@Getter
public class FactoryBeanTargetService {

    private final String name;

    public FactoryBeanTargetService(String name) {
        this.name = name;
    }
}
