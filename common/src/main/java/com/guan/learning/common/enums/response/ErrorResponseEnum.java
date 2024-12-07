package com.guan.learning.common.enums.response;

/**
 * 返回码枚举接口
 *
 * @author cc
 */
public interface ErrorResponseEnum extends IResponseEnum {

    /**
     * 获取是否成功，默认成功
     *
     * @return
     */
    @Override
    default String getSuccess() {
        return "N";
    }
}
