package com.guan.learning.dynamic.anno;

import com.guan.learning.dynamic.enums.DataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceName {

    /**
     * 数据源名称
     */
    DataSourceEnum dataSourceName();
}
