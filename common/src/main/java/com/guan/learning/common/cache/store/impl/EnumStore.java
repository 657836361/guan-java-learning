package com.guan.learning.common.cache.store.impl;


import com.guan.learning.common.cache.enums.Storable;
import com.guan.learning.common.cache.store.IStore;

import java.util.HashMap;
import java.util.Map;

public class EnumStore implements IStore {

    private final Map<String, Object> configMap;

    public EnumStore(Class<? extends Storable> clazz) {
        configMap = new HashMap<>();
        for (Storable constant : clazz.getEnumConstants()) {
            configMap.put(constant.getCode(), constant.getText());
        }
    }

    @Override
    public Map<String, Object> load() {
        return configMap;
    }
}
