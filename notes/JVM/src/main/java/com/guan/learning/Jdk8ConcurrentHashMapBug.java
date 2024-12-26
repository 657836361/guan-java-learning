package com.guan.learning;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前测试类执行后会死循环
 * JDK8中调用ConcurrentHashMap.computeIfAbsent(Object, Function)可能造成的死循环问题。
 * 相关bug见：@see https://bugs.openjdk.java.net/browse/JDK-8161372
 * <p>
 * 该问题在java9被解决 但是java8所有版本都存在
 * 请使用hutool包下的cn.hutool.core.map.SafeConcurrentHashMap 或自行实现
 */
public class Jdk8ConcurrentHashMapBug {
    static final int MAP_SIZE = 20;
    static final int THREADS = 20;
    static final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

    static {
        for (int i = 0; i < MAP_SIZE; i++) map.put(i, i);
    }

    static class TestThread extends Thread {
        public void run() {
            int i = 0;
            int result = 0;
            while (result < Integer.MAX_VALUE) {
                i = (i + 1) % MAP_SIZE;
                result += map.computeIfAbsent(i, (key) -> key + key);
            }
        }
    }

    public static void main(String[] v) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            TestThread t = new TestThread();
            threads.add(t);
            t.start();
        }
        threads.get(0).join();
    }
}

