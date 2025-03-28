package com.guan.learning.mybatisplus.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.guan.common.enums.BaseEnumTypeHandler;
import com.guan.common.enums.SysRoleEnum;
import com.guan.common.pojo.BaseIdBizIdCreateUpdateDeleletedModel;
import com.guan.datasource.dict.DictDataTypeHandler;
import com.guan.datasource.dict.model.BaseDictModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@TableName(value = "`base_user`", autoResultMap = true)
public class BaseUser extends BaseIdBizIdCreateUpdateDeleletedModel {
    private String name;
    private Integer age;
    private String email;
    @TableField(typeHandler = DictDataTypeHandler.class)
    private BaseDictModel gender;
    @TableField(typeHandler = BaseEnumTypeHandler.class)
    private SysRoleEnum role;

    public static BaseUser generateRandomUser() {
        BaseUser user = new BaseUser();
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
        BaseDictModel gender = new BaseDictModel();
        gender.setDictDataCode(RandomUtil.randomBoolean() ? "female" : "male");
        user.setGender(gender);
        user.setRole(RandomUtil.randomBoolean() ? SysRoleEnum.USER : SysRoleEnum.ADMIN);
        return user;
    }

    public static BaseUser generateRandomUser(BaseUser user) {
        user.setId(IdWorker.getId());
        user.setBizId(IdUtil.fastSimpleUUID());
        BaseDictModel gender = user.getGender();
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

    public static BaseUser generateRandomUserFullField() {
        BaseUser user = generateRandomUser();
        user.setId(IdWorker.getId());
        BaseDictModel gender = user.getGender();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser baseUser = (BaseUser) o;
        return Objects.equals(getBizId(), baseUser.getBizId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBizId());
    }
}
