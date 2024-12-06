package com.guan.learning.common.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDictModel {

    /**
     * 字典数据code
     */
    private String dictDataCode;

    /**
     * 字典数据名称
     */
    private String dictDataName;

    /**
     * 字典类型code
     */
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    private String dictTypeName;
}
