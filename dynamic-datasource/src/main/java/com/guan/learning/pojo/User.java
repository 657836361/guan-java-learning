package com.guan.learning.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.guan.learning.common.pojo.BaseDeletedModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("`user`")
public class User extends BaseDeletedModel {

    private String name;
    private Integer age;
    private String email;
}
