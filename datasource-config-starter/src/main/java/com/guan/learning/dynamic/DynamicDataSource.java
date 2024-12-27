package com.guan.learning.dynamic;

import com.guan.learning.dynamic.context.DataSourceContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * todo 需要增加一个数据库拦截器直接根据情况选择数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.get();
    }
}
