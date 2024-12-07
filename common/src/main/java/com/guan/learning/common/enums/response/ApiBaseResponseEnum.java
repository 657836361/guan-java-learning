package com.guan.learning.common.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * api响应基础公共枚举
 */

@Getter
@AllArgsConstructor
public enum ApiBaseResponseEnum implements IResponseEnum {

    SUCCESS(200, "操作成功"),
    DELETE_SUCCESS(200, "删除成功"),
    UPDATE_SUCCESS(200, "更新成功"),
    SAVE_SUCCESS(200, "保存成功");

    /**
     * code值
     */
    private final int code;
    /**
     * message
     */
    private final String message;


}
