package com.guan.common.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * controller http 异常 4xxx
 *
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@Getter
@AllArgsConstructor
public enum ServletErrorResponseEnum implements ErrorResponseEnum {

    MethodArgumentNotValidException(4401, ""),
    MethodArgumentTypeMismatchException(4402, ""),
    MissingServletRequestPartException(4403, ""),
    MissingPathVariableException(4404, ""),
    BindException(4405, ""),
    MissingServletRequestParameterException(4406, ""),
    TypeMismatchException(4407, ""),
    ServletRequestBindingException(4408, ""),
    HttpMessageNotReadableException(4409, ""),
    NoHandlerFoundException(4410, ""),
    NoSuchRequestHandlingMethodException(4411, ""),
    HttpRequestMethodNotSupportedException(4412, ""),
    HttpMediaTypeNotAcceptableException(4413, ""),
    HttpMediaTypeNotSupportedException(4414, ""),
    ConversionNotSupportedException(4415, ""),
    HttpMessageNotWritableException(4416, ""),
    AsyncRequestTimeoutException(4417, ""),
    MaxUploadSizeExceededException(4418, "文件上传失败,大小超过{0}M"),
    ;

    /**
     * 返回码，
     */
    private final int code;

    /**
     * 返回信息，直接读取异常的message
     */
    private final String message;


}
