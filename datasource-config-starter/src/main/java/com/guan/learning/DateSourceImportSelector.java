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
    public static final String MUST_1 = "com.guan.learning.common.AutoFillingMetaObjectHandler";
    public static final String MUST_2 = "com.guan.learning.common.MybatisPlusConfig";
    public static final String MUST_4 = "com.guan.learning.dict.util.DictCacheUtil";
    public static final String MUST_5 = "com.guan.learning.dynamic.config.DynamicDataSourceMybatisExecutorInterceptor";
    public static final String DYNAMIC_MUST = "com.guan.learning.dynamic.config.DynamicDateSourceConfig";
    public static final String NORMAL_MUST = "com.guan.learning.normal.config.NormalDataSourceConfig";
    private static final String[] DYNAMIC_DATA_SOURCE_CONFIG_ARRAY = {DYNAMIC_MUST, MUST_1, MUST_2, MUST_4, MUST_5};
    private static final String[] NORMAL_DATA_SOURCE_CONFIG_ARRAY = {NORMAL_MUST, MUST_1, MUST_2, MUST_4, MUST_5};

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
