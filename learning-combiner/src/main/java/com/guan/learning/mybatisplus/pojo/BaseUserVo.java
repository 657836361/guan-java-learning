package com.guan.learning.mybatisplus.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guan.learning.common.bean.SensitiveDataSerializer;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.dict.model.BaseDictModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
public class BaseUserVo {

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

    public static BaseUserVo newInstance(BaseUser user) {
        BaseUserVo userVo = new BaseUserVo();
        userVo.setBizId(user.getBizId());
        userVo.setName(user.getName());
        userVo.setAge(user.getAge());
        userVo.setEmail(user.getEmail());
        userVo.setGender(Optional.ofNullable(user.getGender()).map(BaseDictModel::getDictDataName).orElse(null));
        userVo.setRole(Optional.ofNullable(user.getRole()).map(SysRoleEnum::getDesc).orElse(null));
        userVo.setGmtCreate(user.getGmtCreate());
        return userVo;
    }
}
