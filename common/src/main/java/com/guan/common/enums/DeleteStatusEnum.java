package com.guan.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteStatusEnum implements BaseEnum {
    DELETED("Y", "已删除"),
    NOT_DELETED("N", "未删除");

    private final String code;
    private final String text;

}
