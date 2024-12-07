package com.guan.learning.common.pojo.response;

import com.guan.learning.common.enums.response.IResponseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础返回结果
 */
@Getter
@Setter
public class BaseResponse<T> {

    /**
     * 响应code 该值为int 未设置值初始值为0，业务中不可使用0作判断
     */
    protected int code;

    /**
     * 是否成功 Y/N
     */
    protected String success;

    /**
     * 提示消息
     */
    protected String message;

    /**
     * 数据列表
     */
    protected T data;

    /**
     * traceId
     */
    protected String traceId;


    /**
     * 构建响应结果
     *
     * @return
     */
    public BaseResponse<T> doResponse(IResponseEnum response) {
        this.setCode(response.getCode());
        this.setSuccess(response.getSuccess());
        this.setMessage(response.getMessage());
        return this;
    }

    /**
     * 构建响应结果
     *
     * @return
     */
    public static <T> BaseResponse<T> withResponse(IResponseEnum response) {
        BaseResponse<T> result = new BaseResponse<T>();
        result.withCode(response.getCode());
        result.withMessage(response.getMessage());
        result.withSuccessFlag(response.getSuccess());
        return result;
    }

    public BaseResponse<T> withCode(int code) {
        this.setCode(code);
        return this;
    }

    public BaseResponse<T> withSuccessFlag(String success) {
        this.setSuccess(success);
        return this;
    }

    public BaseResponse<T> withMessage(String message) {
        this.setMessage(message);
        return this;
    }


}
