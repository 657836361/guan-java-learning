package com.guan.learning.cglib;

/**
 * @author GG
 * @date 2022/5/8
 */
public class HelloService {

    /**
     * 该方法不能被子类覆盖,Cglib是无法代理final修饰的方法的
     */
    final public void sayOthers(String name) {
        System.out.println("HelloService:sayOthers>>" + name);
    }

    /**
     * 该方法不能被子类覆盖,Cglib是无法代理final修饰的方法的
     */
    protected void sayOthersPrivate(String name) {
        System.out.println("HelloService:sayOthersPrivate>>" + name);
    }

    public void sayHello() {
        System.out.println("HelloService:sayHello");
    }
}

