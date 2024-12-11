package com.guan.learning.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guan.learning.common.bean.SensitiveDataSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserVo {
    @JsonIgnore
    private Long id;

    private String bizId;

    @JsonSerialize(using = SensitiveDataSerializer.class)
    private String name;
    private Integer age;
    @JsonSerialize(using = SensitiveDataSerializer.class)
    private String email;
    private String gender;
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
}
