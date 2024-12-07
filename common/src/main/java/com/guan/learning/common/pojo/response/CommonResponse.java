package com.guan.learning.common.pojo.response;


import com.guan.learning.common.enums.response.ApiBaseResponseEnum;
import com.guan.learning.common.enums.response.CommonErrorResponseEnum;
import com.guan.learning.common.enums.response.IResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 返回结果
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CommonResponse<T> extends BaseResponse<T> {


    protected CommonResponse() {
        super();
    }

    /**
     * 构建成功响应结果
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> withSuccess() {
        return BaseResponse.withResponse(ApiBaseResponseEnum.SUCCESS);
    }

    /**
     * 构建成功响应结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> withSuccess(IResponseEnum responseEnum, T data) {
        BaseResponse<T> response = withResponse(responseEnum);
        response.setData(data);
        return response;
    }


    /**
     * 构建成功响应结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> withSuccess(T data) {
        BaseResponse<T> response = withSuccess();
        response.setData(data);
        return response;
    }


    /**
     * 基于ApiBaseResponseEnum.SUCCESS 修改返回的msg
     *
     * @param msg
     * @return
     */
    public static <T> BaseResponse<T> withSuccessMsg(String msg) {
        BaseResponse<T> response = withSuccess();
        response.setMessage(msg);
        return response;
    }

    /**
     * 构建失败响应结果
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> withError() {
        return BaseResponse.withResponse(CommonErrorResponseEnum.OPTIOPN_ERROR);
    }


    /**
     * 构建失败响应结果
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> withError(IResponseEnum responseEnum) {
        BaseResponse<T> response = withResponse(responseEnum);
        return response;
    }


    /**
     * 基于CommonErrorResponseEnum.OPTIOPN_ERROR 修改返回的msg
     *
     * @param msg
     * @return
     */
    public static <T> BaseResponse<T> withErrorMsg(String msg) {
        BaseResponse<T> response = withError();
        response.setMessage(msg);
        return response;
    }


}
