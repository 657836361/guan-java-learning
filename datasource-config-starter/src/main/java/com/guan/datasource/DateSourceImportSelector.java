package com.guan.datasource;

import com.guan.datasource.anno.EnableDataSource;
import com.guan.datasource.enums.DataSourceMode;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Optional;

public class DateSourceImportSelector implements ImportSelector {

    public static final String DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME = "mode";
    public static final String MUST_1 = "com.guan.datasource.common.AutoFillingMetaObjectHandler";
    public static final String MUST_2 = "com.guan.datasource.common.MybatisPlusConfig";
    public static final String MUST_3 = "com.guan.datasource.dynamic.config.DynamicDataSourceMybatisExecutorInterceptor";
    public static final String DYNAMIC_MUST = "com.guan.datasource.dynamic.config.DynamicDateSourceConfig";
    public static final String NORMAL_MUST = "com.guan.datasource.normal.config.NormalDataSourceConfig";
    private static final String[] DYNAMIC_DATA_SOURCE_CONFIG_ARRAY = {DYNAMIC_MUST, MUST_1, MUST_2, MUST_3};
    private static final String[] NORMAL_DATA_SOURCE_CONFIG_ARRAY = {NORMAL_MUST, MUST_1, MUST_2, MUST_3};

    /**
     * 获取@EnableDataSource注解
     * 有就加载数据源 没有就不加载
     *
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //        EnableDataSource enableDataSource = AnnotationUtils.findAnnotation(LearningCombinerApplication.class, EnableDataSource.class);
        MergedAnnotations mergedAnnotations = importingClassMetadata.getAnnotations();
        MergedAnnotation<EnableDataSource> mergedAnnotation = mergedAnnotations.get(EnableDataSource.class);
        Optional<DataSourceMode> dataSourceMode = mergedAnnotation.getValue(DEFAULT_ADVICE_MODE_ATTRIBUTE_NAME, DataSourceMode.class);

        return dataSourceMode.map(mode -> {
            if (mode.equals(DataSourceMode.DYNAMIC)) {
                return DYNAMIC_DATA_SOURCE_CONFIG_ARRAY;
            } else {
                return NORMAL_DATA_SOURCE_CONFIG_ARRAY;
            }
        }).orElse(new String[]{});
    }

}
