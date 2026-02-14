package com.guan.learning.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.guan.common.enums.SysRoleEnum;
import com.guan.datasource.dict.DictDataTypeHandler;
import com.guan.datasource.dict.model.BaseDictModel;
import com.guan.datasource.enums.BaseEnumTypeHandler;
import com.guan.datasource.pojo.BaseIdBizIdCreateUpdateDeleletedModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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
        RandomUtils insecure = RandomUtils.insecure();
        RandomStringUtils insecure1 = RandomStringUtils.insecure();

        BaseUser user = new BaseUser();
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
        BaseDictModel gender = new BaseDictModel();
        gender.setDictDataCode(insecure.randomBoolean() ? "female" : "male");
        user.setGender(gender);
        user.setRole(insecure.randomBoolean() ? SysRoleEnum.USER : SysRoleEnum.ADMIN);
        return user;
    }

    public static BaseUser generateRandomUser(BaseUser user) {
        user.setId(IdWorker.getId());
        user.setBizId(UUID.randomUUID().toString());
        BaseDictModel gender = user.getGender();
        if (gender.getDictDataCode().equals("female")) {
            gender.setDictDataName("女");
        } else {
            gender.setDictDataName("男");
        }
        user.setCreateUser("generate");
        user.setGmtCreate(Date.from(Instant.now()));
        user.setModifyUser("generate");
        user.setGmtModify(Date.from(Instant.now()));
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
        user.setGmtCreate(Date.from(Instant.now()));
        user.setModifyUser("generate");
        user.setGmtModify(Date.from(Instant.now()));
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
