package com.guan.learning.common.exceptions;


import cn.hutool.core.util.StrUtil;
import com.guan.learning.common.enums.response.ErrorResponseEnum;

/**
 * 业务异常
 * 业务处理时，出现异常，可以抛出该异常
 */
public class BusinessException extends BaseException {

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(ErrorResponseEnum responseEnum) {
        super(responseEnum);
    }

    public BusinessException(ErrorResponseEnum responseEnum, String message) {
        super(responseEnum.getCode(), StrUtil.isBlank(message) ? responseEnum.getMessage() : message, responseEnum.getSuccess());
    }

    public BusinessException(ErrorResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BusinessException(ErrorResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}