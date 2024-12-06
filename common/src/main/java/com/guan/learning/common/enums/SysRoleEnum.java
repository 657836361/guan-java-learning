package com.guan.learning.common.enums;

public enum SysRoleEnum implements BaseEnum {

    ADMIN("admin", "管理员"),
    USER("user", "普通用户"),
    ;

    private final String code;

    private final String desc;

    SysRoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return this.code + "|" + this.desc;
    }
}
