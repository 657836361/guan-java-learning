package com.guan.learning.mybatisplus.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.guan.learning.common.enums.BaseEnumTypeHandler;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.common.pojo.BaseDeletedModel;
import com.guan.learning.dict.DictDataTypeHandler;
import com.guan.learning.dict.model.BaseSysDictDataVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "`user`", autoResultMap = true)
public class User extends BaseDeletedModel {
    private String name;
    private Integer age;
    private String email;
    @TableField(typeHandler = DictDataTypeHandler.class)
    private BaseSysDictDataVo gender;
    @TableField(typeHandler = BaseEnumTypeHandler.class)
    private SysRoleEnum role;

    public static User generateRandomUser() {
        User user = new User();
        user.setAge(RandomUtil.randomInt(0, 99));
        StringBuilder name = new StringBuilder();
        int randomInt = RandomUtil.randomInt(2, 6);
        for (int i = 0; i < randomInt; i++) {
            name.append(RandomUtil.randomChinese());
        }
        user.setName(name.toString());
        user.setEmail(RandomUtil.randomString(RandomUtil.randomInt(6, 10)) +
                "@" +
                RandomUtil.randomString(RandomUtil.randomInt(2, 5)) +
                ".com" + (RandomUtil.randomBoolean() ? ".cn" : ""));
        BaseSysDictDataVo gender = new BaseSysDictDataVo();
        gender.setDictDataCode(RandomUtil.randomBoolean() ? "female" : "male");
        user.setGender(gender);
        user.setRole(RandomUtil.randomBoolean() ? SysRoleEnum.USER : SysRoleEnum.ADMIN);
        return user;
    }

    public static User generateRandomUserFullField() {
        User user = generateRandomUser();
        user.setId(IdWorker.getId());
        BaseSysDictDataVo gender = user.getGender();
        if (gender.getDictDataCode().equals("female")) {
            gender.setDictDataName("女");
        } else {
            gender.setDictDataName("男");
        }
        user.setCreateUser("generate");
        user.setGmtCreate(DateUtil.date());
        user.setModifyUser("generate");
        user.setGmtModify(DateUtil.date());
        return user;
    }
}
