package com.guan.learning.excel.dto;


import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.dict.model.BaseDictModel;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@Getter
@Setter
public class BaseUserDto {


    @ExcelProperty(value = "姓名", index = 0)
    @ColumnWidth(11)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "电子邮箱", index = 2)
    @ColumnWidth(26)
    private String email;

    @ExcelProperty(value = "性别", index = 3)
    private String gender;

    @ExcelProperty(value = "角色", index = 4)
    private String role;

    @ExcelProperty(value = "创建时间", index = 5)
    @ColumnWidth(17)
    private Date gmtCreate;

    @ExcelProperty(value = "修改时间", index = 6)
    @ColumnWidth(17)
    private Date gmtModify;

    @ExcelProperty(value = "创建人", index = 7)
    private String createUser;

    @ExcelProperty(value = "修改人", index = 8)
    private String modifyUser;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    public static BaseUserDto userToUserDto(BaseUser user) {
        BaseUserDto userDto = new BaseUserDto();
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setGender(Optional.ofNullable(user.getGender()).map(BaseDictModel::getDictDataName).orElse(null));
        userDto.setRole(Optional.ofNullable(user.getRole()).map(SysRoleEnum::getDesc).orElse(null));
        userDto.setGmtCreate(user.getGmtCreate());
        userDto.setGmtModify(user.getGmtModify());
        userDto.setCreateUser(user.getCreateUser());
        userDto.setModifyUser(user.getModifyUser());
        return userDto;
    }
}
