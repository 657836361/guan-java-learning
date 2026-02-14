package com.guan.learning.config;

import com.google.common.collect.Maps;
import com.guan.common.cache.enums.Storable;
import com.guan.common.cache.store.IStore;
import com.guan.common.cache.store.IstoreConfig;
import com.guan.common.cache.store.impl.EnumStore;
import com.guan.common.enums.DeleteStatusEnum;
import com.guan.common.enums.StatusEnum;
import com.guan.common.enums.SysRoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 枚举的缓存配置
 */
@Component
public class EnumMappingConfig implements IstoreConfig {
    @Override
    public Map<String, IStore> cacheTask() {
        HashMap<String, IStore> storesMap = Maps.newHashMapWithExpectedSize(3);
        addEnumStore(storesMap, StatusEnum.class, null);
        addEnumStore(storesMap, DeleteStatusEnum.class, null);
        addEnumStore(storesMap, SysRoleEnum.class, null);
        return storesMap;
    }

    /**
     * 添加枚举缓存
     *
     * @param storesMap
     * @param enumClass
     * @param enumKey
     */
    private void addEnumStore(HashMap<String, IStore> storesMap, Class<? extends Storable> enumClass, String enumKey) {
        String key = enumKey;
        if (StringUtils.isEmpty(key)) {
            String camelCase = CaseUtils.toCamelCase(enumClass.getSimpleName(), false);
            key = camelCase.toUpperCase(Locale.ROOT);
        }
        IStore store = new EnumStore(enumClass);
        storesMap.put(key, store);
    }
}
