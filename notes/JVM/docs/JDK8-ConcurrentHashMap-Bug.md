# JDK8 ConcurrentHashMap Bug 说明

## Bug 描述

**JDK 版本**: Java 8 所有版本
**修复版本**: Java 9+
**Bug 链接**: https://bugs.openjdk.java.net/browse/JDK-8161372

### 问题现象

在 JDK8 中，调用 `ConcurrentHashMap.computeIfAbsent(Object, Function)` 可能造成**死循环**。

## 复现代码

```java
package com.guan.learning;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前测试类执行后会死循环
 * JDK8中调用ConcurrentHashMap.computeIfAbsent(Object, Function)可能造成的死循环问题。
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
                result += map.computeIfAbsent(i, (key) -> key + key); // 死循环发生在这里
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
```

## 问题原因

JDK8 的 `ConcurrentHashMap.computeIfAbsent()` 实现存在并发问题：

1. 多个线程同时调用 `computeIfAbsent()` 计算同一个 key
2. 内部使用了递归调用来处理并发冲突
3. 在特定条件下，递归无法终止，导致死循环

## 解决方案

### 方案 1: 升级 JDK 版本（推荐）

升级到 **Java 9 或更高版本**，该问题已在 Java 9 中修复。

### 方案 2: 自实现安全的 computeIfAbsent

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SafeConcurrentHashMap {

    /**
     * 线程安全的 computeIfAbsent 实现
     * 避免 JDK8 的死循环 bug
     */
    public static <K, V> V safeComputeIfAbsent(
            ConcurrentHashMap<K, V> map,
            K key,
            Function<K, V> mappingFunction) {

        V value = map.get(key);
        if (value == null) {
            V newValue = mappingFunction.apply(key);
            value = map.putIfAbsent(key, newValue);
            if (value == null) {
                value = newValue;
            }
        }
        return value;
    }

    // 使用示例
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        // 使用安全方法替代原方法
        Integer result = safeComputeIfAbsent(map, 1, k -> k * 2);
    }
}
```

### 方案 3: 使用 synchronized 块（不推荐）

```java
public static <K, V> V synchronizedComputeIfAbsent(
        ConcurrentHashMap<K, V> map,
        K key,
        Function<K, V> mappingFunction) {

    synchronized (map) {
        return map.computeIfAbsent(key, mappingFunction);
    }
}
```

**缺点**: 失去了并发优势，性能较差。

## 注意事项

1. **JDK8 特有问题**: 仅影响 Java 8，Java 9+ 已修复
2. **特定场景触发**: 并发调用 `computeIfAbsent()` 计算已存在的 key 时可能触发
3. **生产环境**: 如果使用 JDK8，建议避免使用 `computeIfAbsent()` 或使用方案 2

## 相关资源

- [JDK-8161372 Bug Report](https://bugs.openjdk.java.net/browse/JDK-8161372)
- [ConcurrentHashMap.computeIfAbsent() JavaDoc](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html#computeIfAbsent-K-java.util.function.Function-)

---

**文档创建日期**: 2026-02-13
**状态**: 原测试类已删除，保留此文档供参考
