package com.guan.learning.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum implements BaseEnum {

    /**
     *
     */
    EFFECTIVE("1", "有效"),
    INVALID("0", "无效");

    private final String code;

    private final String desc;

}
