package com.guan.learning.common.exceptions;

import com.guan.learning.common.enums.response.ErrorResponseEnum;
import lombok.Getter;

/**
 * 基础异常类，所有自定义异常类都需要继承本类
 */
@Getter
public class BaseException extends RuntimeException {

    /**
     * 返回码
     */
    protected ErrorResponseEnum responseEnum;

    /**
     * 异常消息参数
     */
    protected Object[] args;

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(ErrorResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public BaseException(int code, String msg, String success) {
        super(msg);
        this.responseEnum = new ErrorResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }

            @Override
            public String getSuccess() {
                return success;
            }
        };
    }

    public BaseException(ErrorResponseEnum responseEnum, String message) {
        super(message);
        this.responseEnum = responseEnum;
    }

    public BaseException(ErrorResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(ErrorResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }
}
