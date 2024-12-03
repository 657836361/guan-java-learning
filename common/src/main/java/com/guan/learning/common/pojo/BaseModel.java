package com.guan.learning.common.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {

    @TableId(type = IdType.AUTO)
    @JsonIgnore
    private Long id;
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
    @TableField(value = "gmt_modify", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModify;
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    @TableField(value = "modify_user", fill = FieldFill.UPDATE)
    private String modifyUser;
}
