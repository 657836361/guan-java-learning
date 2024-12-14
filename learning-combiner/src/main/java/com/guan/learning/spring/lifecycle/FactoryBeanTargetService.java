package com.guan.learning.spring.lifecycle;

import lombok.Getter;

@Getter
public class FactoryBeanTargetService {

    private final String name;

    public FactoryBeanTargetService(String name) {
        this.name = name;
    }
}
