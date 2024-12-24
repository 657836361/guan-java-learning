package com.guan.learning.common.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class BaseIdBizIdCreateUpdateDeleletedModel {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonIgnore
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private String bizId;

    @TableField(value = "deleted")
    @JsonIgnore
    private LocalDateTime deleted;

    /**
     * 如果有配置jackson的springboot全局配置则不需要该注解@JsonFormat
     * 加上注解会局部覆盖全局配置 优先以注解为主
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 如果有配置jackson的springboot全局配置则不需要该注解@JsonFormat
     * 加上注解会局部覆盖全局配置 优先以注解为主
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModify;

    @TableField(fill = FieldFill.UPDATE)
    private String modifyUser;

}
