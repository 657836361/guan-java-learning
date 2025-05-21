package com.guan.spring.enviroment.aop.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopAspect {

    @Before("@annotation(com.guan.spring.enviroment.aop.config.Mark)")
    public void beforeMethod() {
        log.info("方法执行前的日志记录");
    }

}
