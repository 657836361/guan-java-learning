package com.guan.learning.common.exceptions.handler;


import com.guan.learning.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.learning.common.enums.response.CommonErrorResponseEnum;
import com.guan.learning.common.enums.response.ErrorResponseEnum;
import com.guan.learning.common.enums.response.ServletErrorResponseEnum;
import com.guan.learning.common.exceptions.BaseException;
import com.guan.learning.common.exceptions.BusinessException;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * 全局异常处理器
 */

@Slf4j
@ControllerAdvice("com.guan.learning")
@SuppressWarnings(value = {"rawtypes"})
public class GlobalExceptionHandler {

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prd";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String profile;


    /**
     * 业务json处理异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public BaseResponse handleJSONException(Exception e) {
        log.error("error", e);
        return CommonResponse.withError(CommonErrorResponseEnum.JSON_ERROR);
    }

    /**
     * 业务sql处理异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = {SQLException.class, DataAccessException.class})
    @ResponseBody
    public BaseResponse handleSQLException(Exception e) {
        log.error("error", e);
        return CommonResponse.withError(CommonErrorResponseEnum.SQL_ERROR);
    }

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BaseResponse handleBusinessException(BaseException e) {
        log.error("error", e);
        ErrorResponseEnum responseEnum = e.getResponseEnum();
        if (responseEnum == null) {
            return CommonResponse.withErrorMsg(e.getMessage());
        }
        return CommonResponse.withError(responseEnum);
    }

    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public BaseResponse handleBaseException(BaseException e) {
        log.error("error", e);
        return CommonResponse.withError(e.getResponseEnum());
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class, MissingServletRequestParameterException.class,
            TypeMismatchException.class, HttpMessageNotWritableException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class,
            MissingServletRequestPartException.class, AsyncRequestTimeoutException.class})
    @ResponseBody
    public BaseResponse handleServletException(Exception e) {
        log.error("error", e);
        int code = CommonErrorResponseEnum.SERVER_ERROR.getCode();
        try {
            ServletErrorResponseEnum servletExceptionEnum = ServletErrorResponseEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletErrorResponseEnum.class.getName());
        }
        if (ENV_PROD.equals(profile)) {
            return CommonResponse.withError(CommonErrorResponseEnum.SERVER_BUSY);
        }
        return CommonResponse.withError().withCode(code).withMessage(e.getMessage());
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public BaseResponse handleBindException(HttpServletRequest request, BindException e) {
        log.error("请求[{}]参数绑定异常", request.getRequestURI(), e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse handleValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("请求[{}]参数校验异常", request.getRequestURI(), e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private BaseResponse wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(",");
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return CommonResponse.withError(ArgumentErrorResponseEnum.VALID_ERROR).withMessage(msg.substring(1));
    }

    /**
     * 文件上传异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public BaseResponse handleFileUploadException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        String msg = MessageFormat.format(ServletErrorResponseEnum.MaxUploadSizeExceededException.getMessage(), e.getMaxUploadSize());
        log.error("请求[{}]异常,原因[{}]", request.getRequestURI(), msg);
        return CommonResponse.withError(ServletErrorResponseEnum.MaxUploadSizeExceededException).withMessage(msg);
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse handleException(Exception e) {
        log.error("error", e);
        return CommonResponse.withError(CommonErrorResponseEnum.SERVER_BUSY).withMessage(e.getMessage());
    }

}
