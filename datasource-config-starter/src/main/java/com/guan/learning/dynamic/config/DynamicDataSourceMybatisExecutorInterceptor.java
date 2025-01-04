package com.guan.learning.dynamic.config;

import com.guan.learning.dynamic.anno.DataSourceFlag;
import com.guan.learning.dynamic.context.DataSourceContext;
import com.guan.learning.dynamic.enums.DataSourceFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.sql.DataSource;


/**
 * 1.maven打包 过滤某些类路径下的非java文件 默认不打包非java
 * 2.mybatis拦截器通过判断是否有注解来判断切换数据源
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class})
})
@Slf4j
public class DynamicDataSourceMybatisExecutorInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 这里的invocation都是代理对象
        // invocation.getTarget()后可能还是个代理对象 要操作的话慎用
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];

        printInfo(ms);

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

    /**
     * 现在的情况就是 这种方式必须得获取一个链接后才能获取对应数据源信息
     * todo 反射获取动态数据源或数据源
     *
     * @param ms ms
     */
    private void printInfo(MappedStatement ms) {
        Configuration configuration = ms.getConfiguration();
        Environment environment = configuration.getEnvironment();
        DataSource dataSource = environment.getDataSource();
        log.info("当前数据源为{}", dataSource.getClass().getName());
    }
}
