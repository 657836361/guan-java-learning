package com.guan.learning.streamable.model;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import com.guan.common.enums.SysRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

/**
 * 流式用户数据模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamUser {

    @ExcelProperty(value = "姓名", index = 0)
    @ColumnWidth(15)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "邮箱", index = 2)
    @ColumnWidth(25)
    private String email;

    @ExcelProperty(value = "角色", index = 3)
    private String role;

    @ExcelProperty(value = "创建时间", index = 4)
    @ColumnWidth(20)
    private Date createTime;

    @ExcelIgnore
    private String password;


    public static StreamUser generateRandomUser() {
        RandomUtils insecure = RandomUtils.insecure();
        RandomStringUtils insecure1 = RandomStringUtils.insecure();

        StreamUser user = new StreamUser();
        user.setAge(insecure.randomInt(0, 99));
        StringBuilder name = new StringBuilder();
        int randomInt = insecure.randomInt(2, 6);
        for (int i = 0; i < randomInt; i++) {
            name.append(insecure.randomInt(0x4E00, 0x9FA5 - 0x4E00 + 1));
        }
        user.setName(name.toString());
        user.setEmail(insecure1.nextAlphanumeric(insecure.randomInt(6, 10)) +
                "@" +
                insecure1.nextAlphanumeric(insecure.randomInt(2, 5)) +
                ".com" + (insecure.randomBoolean() ? ".cn" : ""));
        user.setRole(insecure.randomBoolean() ? SysRoleEnum.USER.getCode() : SysRoleEnum.ADMIN.getCode());
        return user;
    }

}
