package com.guan.learning.config;

import com.guan.learning.anno.ThisDynamicDataSource;
import com.guan.learning.context.DataSourceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
//@Component
public class DynamicDataSourceAspect {

    @Pointcut("@within(com.guan.learning.anno.ThisDynamicDataSource)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        ThisDynamicDataSource thisDynamicDataSource = targetClass.getAnnotation(ThisDynamicDataSource.class);
        String datasourceName = thisDynamicDataSource.dataSourceName();
        try {
            DataSourceContext.setDataSource(datasourceName);
            return joinPoint.proceed();
        } finally {
            DataSourceContext.removeDataSource();
        }
    }
}
