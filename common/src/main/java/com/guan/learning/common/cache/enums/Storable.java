package com.guan.learning.common.cache.enums;

/**
 * 枚举类实现该接口
 */
public interface Storable {

    /**
     * 编码
     *
     * @return
     */
    String getCode();

    /**
     * 编码对应值
     *
     * @return
     */
    Object getText();

}
