package com.guan.learning.spring.lifecycle.imports;

import com.guan.learning.spring.lifecycle.imports.bean.MytBeanImporConfiguration;
import org.springframework.context.annotation.Import;

@Import(MytBeanImporConfiguration.class)
public class MyImportConfiguration {
}
