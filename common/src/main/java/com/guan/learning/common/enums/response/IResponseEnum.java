package com.guan.learning.common.enums.response;

/**
 * 返回码枚举接口
 */
public interface IResponseEnum {


    /**
     * 获取返回码
     *
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     *
     * @return 返回信息
     */
    String getMessage();

    /**
     * 获取是否成功，默认成功
     *
     * @return
     */
    default String getSuccess() {
        return "Y";
    }


}
