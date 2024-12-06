package com.guan.learning.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDeletedModel extends BaseModel implements Serializable {

    @TableField(value = "deleted")
    @JsonIgnore
    private LocalDateTime deleted;

}
