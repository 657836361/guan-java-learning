package com.guan.learning.dynamic.context;

import com.guan.learning.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.learning.common.exceptions.BusinessException;
import com.guan.learning.dynamic.enums.DataSourceFlagEnum;

/**
 * 此类提供线程局部变量。这些变量不同于它们的正常对应关系是每个线程访问一个线程(通过get、set方法),有自己的独立初始化变量的副本。
 */
public class DataSourceContext {
    private static final ThreadLocal<DataSourceFlagEnum> DATASOURCE_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源
     *
     * @param dataSourceName 数据源名称
     */
    public static void set(String dataSourceFlagName) {
        DATASOURCE_HOLDER.set(DataSourceFlagEnum.getEnumByName(dataSourceFlagName));
    }

    /**
     * 设置数据源
     *
     * @param flagEnum 数据源标志
     */
    public static void set(DataSourceFlagEnum flagEnum) {
        if (flagEnum == null) {
            throw new BusinessException(ArgumentErrorResponseEnum.VALID_ERROR, "数据源参数错误");
        }
        DATASOURCE_HOLDER.set(flagEnum);
    }

    /**
     * 获取当前线程的数据源
     *
     * @return 数据源名称
     */
    public static DataSourceFlagEnum get() {
        return DATASOURCE_HOLDER.get();
    }

    /**
     * 删除当前数据源
     */
    public static void remove() {
        DATASOURCE_HOLDER.remove();
    }
}
