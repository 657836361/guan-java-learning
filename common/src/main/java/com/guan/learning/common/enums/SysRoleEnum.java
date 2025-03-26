package com.guan.learning.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysRoleEnum implements BaseEnum {

    ADMIN("admin", "管理员"),
    USER("user", "普通用户"),
    ;

    private final String code;

    private final String text;

    @Override
    public String toString() {
        return this.code + "|" + this.text;
    }
}
