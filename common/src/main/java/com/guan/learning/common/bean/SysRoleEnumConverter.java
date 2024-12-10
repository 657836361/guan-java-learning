package com.guan.learning.common.bean;

import com.guan.learning.common.enums.SysRoleEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SysRoleEnumConverter implements Converter<String, SysRoleEnum> {
    @Override
    public SysRoleEnum convert(String source) {
        try {
            // 先尝试按照枚举常量名称转换
            return SysRoleEnum.valueOf(source);
        } catch (IllegalArgumentException e) {
            // 如果按照名称转换失败，再尝试按照编码查找枚举值
            for (SysRoleEnum value : SysRoleEnum.values()) {
                if (value.getCode().equals(source)) {
                    return value;
                }
            }
            // 如果都没找到，返回null或者抛出异常等进行错误处理，这里返回null并由后续逻辑处理
            return null;
        }
    }
}
