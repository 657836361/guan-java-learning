package com.guan.learning.spring.lifecycle.imports;

import com.guan.learning.spring.lifecycle.imports.bean.MyBeanImpoSelector;
import org.springframework.context.annotation.Import;

@Import(MyBeanImpoSelector.class)
public class MyImporSelector {
}
