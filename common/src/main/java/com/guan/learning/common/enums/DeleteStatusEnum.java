package com.guan.learning.common.enums;

import lombok.Getter;

@Getter
public enum DeleteStatusEnum {
    DELETED("Y", "已删除"),
    NOT_DELETED("N", "未删除");

    private final String code;
    private final String description;

    private DeleteStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
