package com.guan.learning.dynamic.enums;

import com.guan.learning.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.learning.common.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceEnum {

    /**
     * 主库
     */
    MASTER("master"),

    /**
     * 备库
     */
    SLAVE("slave"),
    ;

    private final String name;

    public static DataSourceEnum getEnumByName(String name) throws BusinessException {
        for (DataSourceEnum anEnum : DataSourceEnum.values()) {
            if (anEnum.getName().equals(name)) {
                return anEnum;
            }
        }
        throw new BusinessException(ArgumentErrorResponseEnum.VALID_ERROR, "数据源参数错误");
    }

}
