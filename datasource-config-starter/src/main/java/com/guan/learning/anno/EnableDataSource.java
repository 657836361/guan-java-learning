package com.guan.learning.anno;

import com.guan.learning.DateSourceImportSelector;
import com.guan.learning.enums.DataSourceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DateSourceImportSelector.class)
public @interface EnableDataSource {

    /**
     * 数据源模式
     */
    DataSourceMode mode() default DataSourceMode.NORMAL;
}
