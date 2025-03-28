package com.guan.datasource.dynamic.anno;

import com.guan.datasource.dynamic.enums.DataSourceFlagEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceFlag {

    /**
     * 数据源标志
     */
    DataSourceFlagEnum flagEnum();
}
