package com.guan.datasource.dynamic.enums;

import com.guan.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.common.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceFlagEnum {

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

    public static DataSourceFlagEnum getEnumByName(String flag) throws BusinessException {
        for (DataSourceFlagEnum anEnum : DataSourceFlagEnum.values()) {
            if (anEnum.getName().equals(flag)) {
                return anEnum;
            }
        }
        throw new BusinessException(ArgumentErrorResponseEnum.VALID_ERROR, "数据源参数错误");
    }

}
