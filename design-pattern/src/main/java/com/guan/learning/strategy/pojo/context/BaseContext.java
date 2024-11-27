package com.guan.learning.strategy.pojo.context;

import lombok.Getter;

@Getter
public class BaseContext {

    private final Long id;

    public BaseContext(Long id) {
        this.id = id;
    }
}
