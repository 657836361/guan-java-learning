package com.guan.learning.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author GG
 * @date 2022/5/8
 */
public class JdkProxy implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("==========" + method.getName() + "=============");
        System.out.println("------JDK动态代理开始-------");
        Object result = method.invoke(target, args);
        System.out.println("------JDK动态代理结束-------");
        return result;
    }
}
