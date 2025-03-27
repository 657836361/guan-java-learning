package com.guan.learning.common.cache.store.impl;


import cn.hutool.core.map.MapUtil;
import com.guan.learning.common.cache.enums.Storable;
import com.guan.learning.common.cache.store.IStore;

import java.util.Map;

public class EnumStore implements IStore {

    private final Map<String, Object> configMap;

    public EnumStore(Class<? extends Storable> clazz) {
        if (clazz.isEnum()) {
            Storable[] enumConstants = clazz.getEnumConstants();
            configMap = MapUtil.newHashMap(enumConstants.length);

            for (Storable constant : enumConstants) {
                configMap.put(constant.getCode(), constant.getText());
            }
            return;
        }
        throw new IllegalArgumentException("current class is not enum class.");
    }

    @Override
    public Map<String, Object> load() {
        return configMap;
    }
}
