package com.guan.learning.spring.lifecycle;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 这是自定义过滤器 过滤了某个类路径下的所有文件
 * 详情见启动类的@ComponentScan
 */
public class SpringLifeTypeFilter implements TypeFilter {

    private static final String EXCLUDED_PACKAGE = "com.guan.learning.spring.lifecycle";

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String className = classMetadata.getClassName();
        return className.startsWith(EXCLUDED_PACKAGE);
    }
}
