package com.guan.learning.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDateSourceImportSelector implements ImportSelector {

    private static final String DYNAMIC_DATE_SOURCE_CONFIG = "com.guan.learning.config.DynamicDateSourceConfig";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{DYNAMIC_DATE_SOURCE_CONFIG};
    }
}
