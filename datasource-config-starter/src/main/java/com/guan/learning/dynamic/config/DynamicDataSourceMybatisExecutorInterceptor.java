package com.guan.learning.dynamic.config;

import com.guan.learning.dynamic.anno.DataSourceFlag;
import com.guan.learning.dynamic.context.DataSourceContext;
import com.guan.learning.dynamic.enums.DataSourceFlagEnum;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


/**
 * 1.maven打包 过滤某些类路径下的非java文件
 * 2.mybatis拦截器通过判断是否有注解来判断切换数据源
 * todo 3.验证mybatisplus 或 mybatis的流式调用是否会走拦截器的queryCursor
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class})
})
public class DynamicDataSourceMybatisExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 这里的invocation都是代理对象
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        String id = ms.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        Class<?> mapperInterface = Class.forName(className);

        DataSourceFlag anno = mapperInterface.getAnnotation(DataSourceFlag.class);
        if (anno == null) {
            return invocation.proceed();
        }
        try {
            if (anno.flagEnum().equals(DataSourceFlagEnum.SLAVE)) {
                DataSourceContext.set(DataSourceFlagEnum.SLAVE);
            }
            return invocation.proceed();
        } finally {
            DataSourceContext.remove();
        }
    }
}
