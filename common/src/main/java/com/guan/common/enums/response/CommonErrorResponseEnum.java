package com.guan.common.enums.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公共报错枚举
 */
@Getter
@AllArgsConstructor
public enum CommonErrorResponseEnum implements ErrorResponseEnum {


    /**
     * 服务器繁忙，请稍后重试
     */
    SERVER_BUSY(1001, "服务器繁忙"),

    /**
     * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
     */
    SERVER_ERROR(1002, "网络异常"),

    /**
     * 需求没定义文案的情况下使用改提示
     */
    OPTIOPN_ERROR(1003, "操作失败"),

    /**
     * sql解析异常一般是数据或者数据库有异常，需要联系研发/运维处理
     */
    SQL_ERROR(1005, "系统内部错误,请联系运维人员处理"),

    /**
     * json解析异常一般是请求数据问题
     */
    JSON_ERROR(1006, "数据无法处理,请检查后再发起请求"),

    /**
     * 数据源未启动 不能操作
     */
    DATASOURCE_UNABLE(1007, "数据源未启动 不能操作"),

    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;


}
