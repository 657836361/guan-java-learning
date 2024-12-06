package com.guan.learning.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.guan.learning.common.bean.DictDataTypeHandler;
import com.guan.learning.common.dict.model.BaseSysDictDataVo;
import com.guan.learning.common.pojo.BaseDeletedModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "`user`", autoResultMap = true)
public class User extends BaseDeletedModel {
    private String name;
    private Integer age;
    private String email;
    @TableField(typeHandler = DictDataTypeHandler.class)
    private BaseSysDictDataVo gender;
}
