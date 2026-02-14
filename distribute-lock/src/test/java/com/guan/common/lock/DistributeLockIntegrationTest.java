package com.guan.common.lock;

import com.guan.distribute.core.DistributedLockTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 分布式锁集成测试
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class DistributeLockIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(DistributeLockIntegrationTest.class);

    @Autowired
    private DistributedLockTemplate lockTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String TEST_LOCK_KEY = "test:lock:key";

    @BeforeEach
    void setUp() {
        // 清理测试数据
        redisTemplate.delete(TEST_LOCK_KEY);
    }

    @Test
    void testBasicLockAndUnlock() {
        // 测试基本的加锁和解锁功能
        boolean acquired = lockTemplate.tryExecute(TEST_LOCK_KEY, 10, TimeUnit.SECONDS, () -> {
            log.info("执行业务逻辑");
        });

        assertTrue(acquired, "应该能够成功获取锁");

        // 验证锁已经被释放
        String lockValueAfterUnlock = redisTemplate.opsForValue().get(TEST_LOCK_KEY);
        assertNull(lockValueAfterUnlock, "锁应该已经被释放");
    }

    @Test
    void testLockWithReturnValue() {
        // 测试带返回值的锁模板
        String result = lockTemplate.execute(TEST_LOCK_KEY, 5, TimeUnit.SECONDS, () -> {
            return "执行结果";
        });

        assertNotNull(result, "应该返回执行结果");
        assertEquals("执行结果", result, "返回值应该正确");
    }

    @Test
    void testConcurrentLockAccess() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        // 创建多个线程同时尝试获取同一个锁
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    boolean acquired = lockTemplate.tryExecute(TEST_LOCK_KEY, 5, TimeUnit.SECONDS, () -> {
                        successCount.incrementAndGet();
                        // 模拟业务处理
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(15, TimeUnit.SECONDS);
        assertEquals(threadCount, successCount.get(), "所有线程都应该能够成功获取锁");
    }

    @Test
    void testLockWithWaitTime() throws InterruptedException {
        // 测试带等待时间的锁获取
        String lockKey = "test:lock:wait";

        // 第一个线程获取锁并持有2秒
        Thread holderThread = new Thread(() -> {
            lockTemplate.execute(lockKey, 3, TimeUnit.SECONDS, () -> {
                try {
                    Thread.sleep(2000); // 持有锁2秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        holderThread.start();

        // 等待第一个线程获取锁
        Thread.sleep(100);

        // 第二个线程尝试获取锁，等待3秒
        long startTime = System.currentTimeMillis();
        boolean acquired = lockTemplate.execute(lockKey, 3, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, () -> {
            log.info("获取到锁了");
        });
        long endTime = System.currentTimeMillis();

        assertTrue(acquired, "应该能够成功获取到锁");
        assertTrue(endTime - startTime >= 1500, "应该等待了足够的时间");

        redisTemplate.delete(lockKey);
        holderThread.join();
    }

    @Test
    void testLockTemplateExecutionFailure() {
        // 先获取锁
        boolean firstAcquired = lockTemplate.tryExecute(TEST_LOCK_KEY, 10, TimeUnit.SECONDS, () -> {
            log.info("第一次获取锁");
        });
        assertTrue(firstAcquired, "第一次应该能够获取锁");

        // 第二次尝试获取同一个锁（在第一个锁释放前），应该失败
        AtomicInteger executionCount = new AtomicInteger(0);
        String result = lockTemplate.execute(TEST_LOCK_KEY, 5, TimeUnit.SECONDS, () -> {
            executionCount.incrementAndGet();
            return "不应该执行到这里";
        });

        assertNull(result, "锁获取失败时应该返回null");
        assertEquals(0, executionCount.get(), "任务不应该被执行");
    }

    @Test
    void testMultipleLockOperations() {
        // 测试连续多次锁操作
        for (int i = 0; i < 5; i++) {
            final int index = i; // 使用 final 变量供 lambda 使用
            String key = "test:lock:multiple:" + index;
            String result = lockTemplate.execute(key, 5, TimeUnit.SECONDS, () -> {
                return "执行次数: " + index;
            });
            assertEquals("执行次数: " + index, result, "第" + index + "次执行结果应该正确");
            redisTemplate.delete(key);
        }
    }

    @Test
    void testLockIsLocked() {
        // 测试检查锁状态
        String key = "test:lock:check";

        // 初始状态
        redisTemplate.delete(key);

        // 获取锁
        lockTemplate.execute(key, 10, TimeUnit.SECONDS, () -> {
            // 锁应该被持有
            String value = redisTemplate.opsForValue().get(key);
            assertNotNull(value, "锁应该存在于Redis中");
        });

        // 释放后
        String valueAfterRelease = redisTemplate.opsForValue().get(key);
        assertNull(valueAfterRelease, "锁应该已经被释放");
    }

    @Test
    void testLockExpiration() throws InterruptedException {
        // 测试锁过期机制
        String key = "test:lock:expiration";
        long expireTime = 2; // 2秒过期

        // 获取锁
        lockTemplate.execute(key, expireTime, TimeUnit.SECONDS, () -> {
            log.info("获取到短期锁");
        });

        // 等待锁过期
        Thread.sleep(2500);

        // 现在应该可以立即获取锁
        boolean acquired = lockTemplate.tryExecute(key, 5, TimeUnit.SECONDS, () -> {
            log.info("重新获取到锁");
        });

        assertTrue(acquired, "锁过期后应该能够重新获取");
        redisTemplate.delete(key);
    }
}
