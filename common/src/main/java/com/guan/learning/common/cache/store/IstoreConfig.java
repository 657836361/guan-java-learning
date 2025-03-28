package com.guan.learning.common.cache.store;


import java.util.Map;

public interface IstoreConfig {

    /**
     * 缓存任务
     *
     * @return
     */
    Map<String, IStore> cacheTask();
}
