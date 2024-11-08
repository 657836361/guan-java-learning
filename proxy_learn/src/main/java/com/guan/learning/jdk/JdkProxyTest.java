package com.guan.learning.jdk;

import java.lang.reflect.Proxy;

/**
 * 使用JDK动态代理的五大步骤:
 * 1.通过实现InvocationHandler接口来自定义自己的InvocationHandler;
 * 2.通过Proxy.getProxyClass获得动态代理类
 * 3.通过反射机制获得代理类的构造方法，方法签名为getConstructor(InvocationHandler.class)
 * 4.通过构造函数获得代理对象并将自定义的InvocationHandler实例对象传为参数传入
 * 5.通过代理对象调用目标方法
 *
 * @author GG
 * @date 2022/5/8
 */
public class JdkProxyTest {

    public static void main(String[] args) throws Exception {
        // 生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ISayHello iSayHello = (ISayHello) Proxy.newProxyInstance(SayImpl.class.getClassLoader(), // 加载接口的类加载器
                SayImpl.class.getInterfaces(), // 一组接口
                new JdkProxy(new SayImpl())); // 自定义的InvocationHandler
        iSayHello.sayHello();
        ISayBye iSayBye = (ISayBye) Proxy.newProxyInstance(SayImpl.class.getClassLoader(), // 加载接口的类加载器
                SayImpl.class.getInterfaces(), // 一组接口
                new JdkProxy(new SayImpl())); // 自定义的InvocationHandler
        iSayBye.sayBye();
//        ISayHello  iSayHello = (ISayHello)Proxy.newProxyInstance(SayImpl.class.getClassLoader(), // 加载接口的类加载器
//                SayImpl.class.getInterfaces(),
////                new Class[]{ISayBye.class}, // 一组接口
//                new JdkProxy(new SayImpl())); // 自定义的InvocationHandler
//        iSayHello.sayHello();
    }
}
