package com.guan.common.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数校验异常返回结果
 */
@Getter
@AllArgsConstructor
public enum ArgumentErrorResponseEnum implements ErrorResponseEnum {


    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(4001, "参数非法"),
    DATA_NOT_EXIST(4001, "数据不存在"),

    ;

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;


}
