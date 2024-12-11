package com.guan.learning.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guan.learning.common.bean.SensitiveDataSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserVo {

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

    public static UserVo newInstance(User user) {
        UserVo userVo = new UserVo();
        userVo.setBizId(user.getBizId());
        userVo.setName(user.getName());
        userVo.setAge(user.getAge());
        userVo.setEmail(user.getEmail());
        userVo.setGender(user.getGender().getDictDataName());
        userVo.setRole(user.getRole().getDesc());
        userVo.setGmtCreate(user.getGmtCreate());
        return userVo;
    }
}
