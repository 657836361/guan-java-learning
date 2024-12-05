package com.guan.learning.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseModel implements Serializable {

    @TableField(value = "deleted")
    @JsonIgnore
    private String deleted;
}
