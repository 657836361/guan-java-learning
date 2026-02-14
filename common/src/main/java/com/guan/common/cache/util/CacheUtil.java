package com.guan.common.cache.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.guan.common.cache.store.IStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class CacheUtil {

    private static final int CACHE_QUEUE_SIZE = 500;
    private static final int EXECUTOR_QUEUE_SIZE = 300;

    private static final Map<String, IStore> TASK_MAP = Maps.newHashMapWithExpectedSize(50);

    private static final List<String> SKIP_CACHE_LIST = new ArrayList<>(10);


    private static final Cache<String, Map<String, Object>> CACHE_DATA_MAP = CacheBuilder.newBuilder()
            .initialCapacity(CACHE_QUEUE_SIZE / 5)
            .maximumSize(CACHE_QUEUE_SIZE)
            .expireAfterAccess(Duration.ofSeconds(3600))
            .expireAfterWrite(Duration.ofHours(2))
            .build();


    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(20,
            20,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(EXECUTOR_QUEUE_SIZE),
            r -> {
                Thread t = new Thread(r);
                t.setName("cache-load-thread");
                return t;
            },
            (r, executor) -> log.error("queue is full.reject this task.")
    );

    public static void addSkipCacheList(String... keys) {
        if (keys != null && keys.length > 0) {
            SKIP_CACHE_LIST.addAll(Arrays.asList(keys));
        }
    }


    public static void addTaskMap(Map<String, IStore> taskMap) {
        if (!MapUtils.isEmpty(taskMap)) {
            TASK_MAP.putAll(taskMap);
        }
    }

    public static void load() {
        TASK_MAP.forEach((key, value) -> {
            if (!SKIP_CACHE_LIST.contains(key)) {
                CACHE_DATA_MAP.put(key, value.load());
            }
        });
    }

    public static Map<String, Object> get(String key) {
        return get(key, true);
    }

    public static Map<String, Map<String, Object>> get(String... keys) {
        return Arrays.stream(keys)
                .collect(Collectors.toMap(
                        Function.identity(),
                        key -> get(key, true),
                        (existing, replacement) -> replacement,
                        () -> Maps.newHashMapWithExpectedSize(keys.length)
                ));
    }

    public static Map<String, Object> get(String key, boolean silent) {
        if (SKIP_CACHE_LIST.contains(key)) {
            return TASK_MAP.get(key).load();
        }

        try {
            return CACHE_DATA_MAP.get(key, () -> {
                if (TASK_MAP.containsKey(key)) {
                    Future<Map<String, Object>> future = EXECUTOR.submit(() -> TASK_MAP.get(key).load());
                    try {
                        return future.get(5, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        if (silent) {
                            log.warn("cache key [" + key + "] load error", e);
                            return Collections.emptyMap();
                        }
                        throw e;
                    }
                } else {
                    if (!silent) {
                        throw new IllegalArgumentException("cache key [" + key + "] not found");
                    }
                    log.warn("cache key [{}] not found", key);
                    return Collections.emptyMap();
                }
            });
        } catch (Exception e) {
            if (!silent) {
                throw new IllegalArgumentException("cache key [" + key + "] not found");
            }
            log.warn("cache key [{}] init error", key);
            return Collections.emptyMap();
        }
    }
}
