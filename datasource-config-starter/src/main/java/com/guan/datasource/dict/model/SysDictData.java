package com.guan.datasource.dict.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.guan.common.pojo.BaseIdCreateUpdateModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @see BaseDictModel
 */
@Getter
@Setter
@TableName("sys_dict_data")
public class SysDictData extends BaseIdCreateUpdateModel {
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
