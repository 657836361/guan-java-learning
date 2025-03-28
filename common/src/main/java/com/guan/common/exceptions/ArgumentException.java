package com.guan.common.exceptions;


import com.guan.common.enums.response.ErrorResponseEnum;

/**
 * 参数校验异常
 * 业务处理时，出现异常，可以抛出该异常
 */
public class ArgumentException extends BaseException {

    public ArgumentException(ErrorResponseEnum responseEnum) {
        super(responseEnum);
    }

    public ArgumentException(ErrorResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public ArgumentException(ErrorResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}