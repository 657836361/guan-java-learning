package com.guan.spring.enviroment;

import com.guan.spring.enviroment.aop.service.IServiceA;
import com.guan.spring.enviroment.aop.service.IServiceC;
import com.guan.spring.enviroment.aop.service.ServiceAFinalMethod;
import com.guan.spring.enviroment.aop.service.ServiceANotFinalMethod;
import com.guan.spring.enviroment.aop.service.ServiceB;
import com.guan.spring.enviroment.aop.service.ServiceCImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServiceB serviceB;

    @Autowired
    private IServiceC serviceC;

    @Autowired
    private ServiceCImpl serviceCImpl;

    @Autowired
    private ServiceAFinalMethod finalMethod;

    @Autowired
    private ServiceANotFinalMethod notFinalMethod;

    @Test
    public void test1() {
        IServiceA withFinal = applicationContext.getBean(ServiceAFinalMethod.class);
        IServiceA noFinal = applicationContext.getBean(ServiceANotFinalMethod.class);
        try {
            withFinal.doServiceA(2);
        } catch (Exception e) {
            log.error("withFinal invoke error", e);
        }
        log.info("---------------------");
        noFinal.doServiceA(2);
    }

    @Test
    public void test2() {
        try {
            finalMethod.doServiceA(2);
        } catch (Exception e) {
            log.error("接口实现类加final调用报错", e);
        }
        log.info("---------------------");
        notFinalMethod.doServiceA(2);
    }

    @Test
    public void test3() {
        try {
            serviceB.doServiceB(2);
        } catch (Exception e) {
            log.error("加final的方法调用报错", e);
        }
        log.info("---------------------");
        serviceB.doServiceB1(2);
        log.info("---------------------");
        serviceC.doServiceC(2);
    }

    @Test
    public void test4() {
        serviceC.doServiceC(2);
        log.info("---------------------");
        serviceC.doServiceC1(2);
        log.info("---------------------");
        serviceC.doServiceC2(2);
        log.info("---------------------");
        serviceCImpl.doServiceC(2);
        log.info("---------------------");
        serviceCImpl.doServiceC1(2);
        log.info("---------------------");
        serviceCImpl.doServiceC2(2);


    }
}