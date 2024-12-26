package com.guan.learning.jdk;

/**
 * @author GG
 * @date 2022/5/8
 */
public class SayImpl implements ISayHello, ISayBye {
    @Override
    public void sayHello() {
        System.out.println("say hello");
    }

    @Override
    public void sayBye() {
        System.out.println("say bye");
    }
}
