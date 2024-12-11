package com.guan.learning.pojo;

import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.common.pojo.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest extends PageRequest {
    private String name;
    private Integer startAge;
    private Integer endAge;
    private String email;

    private String gender;
    private SysRoleEnum role;
}