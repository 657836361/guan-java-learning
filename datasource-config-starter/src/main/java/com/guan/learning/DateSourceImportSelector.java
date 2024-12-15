package com.guan.learning;

import com.guan.learning.anno.EnableDataSource;
import com.guan.learning.enums.DataSourceMode;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;

public class DateSourceImportSelector implements ImportSelector {

    public static final String DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME = "mode";

    private static final String DYNAMIC_DATA_SOURCE_CONFIG = "com.guan.learning.dynamic.config.DynamicDateSourceConfig";
    private static final String NORMAL_DATA_SOURCE_CONFIG = "com.guan.learning.normal.config.NormalDataSourceConfig";

    /**
     * 获取@EnableDataSource注解
     * 有就加载数据源 没有就不加载
     *
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        MergedAnnotations mergedAnnotations = importingClassMetadata.getAnnotations();
        MergedAnnotation<EnableDataSource> mergedAnnotation = mergedAnnotations.get(EnableDataSource.class);
        Optional<DataSourceMode> dataSourceMode = mergedAnnotation.getValue(DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME, DataSourceMode.class);

        return dataSourceMode.map(mode -> {
            if (mode.equals(DataSourceMode.DYNAMIC)) {
                return new String[]{DYNAMIC_DATA_SOURCE_CONFIG};
            } else {
                return new String[]{NORMAL_DATA_SOURCE_CONFIG};
            }
        }).orElse(new String[]{});

    }
}
