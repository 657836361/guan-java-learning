package com.guan.learning.config;

import cn.hutool.core.util.StrUtil;
import com.guan.learning.anno.ThisDynamicDataSource;
import com.guan.learning.context.DataSourceContext;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class DynamicDataSourceInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取Mapper接口的全限定名和方法名
        String mappedStatementId = mappedStatement.getId();
        String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        // 使用反射加载类并获取方法
        Class<?> mapperClass = Class.forName(className);
        ThisDynamicDataSource dataSource = mapperClass.getAnnotation(ThisDynamicDataSource.class);
        if (dataSource == null) {
            return invocation.proceed();
        }
        String dataSourceName = dataSource.dataSourceName();
        if (StrUtil.isEmpty(dataSourceName)) {
            dataSourceName = "master";
        }
        // 在此处可以读取注解，进行动态数据源切换
        try {
            DataSourceContext.setDataSource(dataSourceName); // 示例
            return invocation.proceed();
        } finally {
            DataSourceContext.removeDataSource();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
