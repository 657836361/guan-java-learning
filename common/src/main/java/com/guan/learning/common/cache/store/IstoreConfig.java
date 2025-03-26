package com.guan.learning.common.cache.store;


import cn.hutool.core.util.StrUtil;
import com.guan.learning.common.cache.enums.Storable;
import com.guan.learning.common.cache.store.impl.EnumStore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public interface IstoreConfig {

    /**
     * 缓存任务
     *
     * @return
     */
    Map<String, IStore> cacheTask();

    /**
     * 添加枚举缓存
     *
     * @param storesMap
     * @param enumClass
     */
    default void addEnumStore(HashMap<String, IStore> storesMap, Class<? extends Storable> enumClass) {
        String key = StrUtil.toUnderlineCase(enumClass.getSimpleName()).toUpperCase(Locale.ROOT);
        IStore store = new EnumStore(enumClass);
        storesMap.put(key, store);

    }
}
