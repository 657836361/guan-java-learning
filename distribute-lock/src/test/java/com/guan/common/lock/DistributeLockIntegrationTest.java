package com.guan.common.lock;

import com.guan.common.lock.annotation.DistributedLock;
import com.guan.common.lock.annotation.EnableDistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@Slf4j
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class DistributeLockIntegrationTest {

    @Autowired
    private LockSupportTemplate lockSupportTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TestLockService testLockService;

    private static final String TEST_LOCK_KEY = "test:lock:key";

    @BeforeEach
    void setUp() {
        // 清理测试数据
        redisTemplate.delete(TEST_LOCK_KEY);
    }

    @Test
    void testBasicLockAndUnlock() {
        // 测试基本的加锁和解锁功能
        boolean acquired = lockSupportTemplate.tryLock(TEST_LOCK_KEY, 10, TimeUnit.SECONDS);
        assertTrue(acquired, "应该能够成功获取锁");

        // 验证锁确实被获取
        String lockValue = redisTemplate.opsForValue().get(TEST_LOCK_KEY);
        assertNotNull(lockValue, "锁应该存在于Redis中");

        // 释放锁
        lockSupportTemplate.unlock(TEST_LOCK_KEY);

        // 验证锁被释放
        String lockValueAfterUnlock = redisTemplate.opsForValue().get(TEST_LOCK_KEY);
        assertNull(lockValueAfterUnlock, "锁应该已经被释放");
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
                    boolean acquired = lockSupportTemplate.tryLock(TEST_LOCK_KEY, 5, TimeUnit.SECONDS);
                    if (acquired) {
                        try {
                            successCount.incrementAndGet();
                            // 模拟业务处理
                            Thread.sleep(100);
                        } finally {
                            lockSupportTemplate.unlock(TEST_LOCK_KEY);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(10, TimeUnit.SECONDS);
        assertEquals(threadCount, successCount.get(), "所有线程都应该能够成功获取锁");
    }

    @Test
    void testLockWithWaitTime() throws InterruptedException {
        // 测试带等待时间的锁获取
        String lockKey = "test:lock:wait";

        // 第一个线程获取锁并持有2秒
        new Thread(() -> {
            boolean acquired = lockSupportTemplate.tryLock(lockKey, 3, TimeUnit.SECONDS);
            if (acquired) {
                try {
                    Thread.sleep(2000); // 持有锁2秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lockSupportTemplate.unlock(lockKey);
                }
            }
        }).start();

        // 等待第一个线程获取锁
        Thread.sleep(100);

        // 第二个线程尝试获取锁，等待3秒
        long startTime = System.currentTimeMillis();
        boolean acquired = lockSupportTemplate.tryLock(lockKey, 3, TimeUnit.SECONDS, 5, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        assertTrue(acquired, "应该能够成功获取到锁");
        assertTrue(endTime - startTime >= 1500, "应该等待了足够的时间");

        lockSupportTemplate.unlock(lockKey);
        redisTemplate.delete(lockKey);
    }

    @Test
    void testAnnotationBasedLock() {
        // 测试注解方式的分布式锁
        String result = testLockService.annotatedMethod("testUser", "123");
        assertNotNull(result, "应该返回执行结果");
        assertTrue(result.contains("testUser"), "结果应该包含用户名");

        // 测试并发访问注解方法
        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger callCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    String result2 = testLockService.annotatedMethod("user" + threadId, "id" + threadId);
                    if (result2 != null) {
                        callCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await(10, TimeUnit.SECONDS);
            assertEquals(threadCount, callCount.get(), "所有调用都应该成功");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void testLockTemplateWithReturn() {
        // 测试带返回值的锁模板
        String result = lockSupportTemplate.tryLockAndExecute(TEST_LOCK_KEY, 5, TimeUnit.SECONDS, () -> {
            return "执行结果";
        });

        assertNotNull(result, "应该返回执行结果");
        assertEquals("执行结果", result, "返回值应该正确");
    }

    @Test
    void testLockTemplateExecutionFailure() {
        // 先获取锁
        boolean firstAcquired = lockSupportTemplate.tryLock(TEST_LOCK_KEY, 10, TimeUnit.SECONDS);
        assertTrue(firstAcquired, "第一次应该能够获取锁");

        // 第二次尝试获取同一个锁，应该失败
        String result = lockSupportTemplate.tryLockAndExecute(TEST_LOCK_KEY, 5, TimeUnit.SECONDS, () -> {
            return "不应该执行到这里";
        });

        assertNull(result, "锁获取失败时应该返回null");

        // 释放锁
        lockSupportTemplate.unlock(TEST_LOCK_KEY);
    }

    /**
     * 测试服务类
     */
    @EnableDistributeLock
    public static class TestLockService {

        @DistributedLock(key = "method:lock:#user.id", expire = 5, timeUnit = TimeUnit.SECONDS)
        public String annotatedMethod(String user, String id) {
            log.info("执行注解方法，用户: {}, ID: {}", user, id);
            try {
                Thread.sleep(100); // 模拟业务处理
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "处理完成: " + user;
        }

        @DistributedLock(key = "fail:test", expire = 1, timeUnit = TimeUnit.SECONDS,
                failStrategy = DistributedLock.FailStrategy.SILENT)
        public String silentFailMethod() {
            return "不应该返回";
        }
    }
}