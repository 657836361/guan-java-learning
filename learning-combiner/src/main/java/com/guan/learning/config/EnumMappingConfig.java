package com.guan.learning.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.guan.learning.common.cache.enums.Storable;
import com.guan.learning.common.cache.store.IStore;
import com.guan.learning.common.cache.store.IstoreConfig;
import com.guan.learning.common.cache.store.impl.EnumStore;
import com.guan.learning.common.enums.DeleteStatusEnum;
import com.guan.learning.common.enums.StatusEnum;
import com.guan.learning.common.enums.SysRoleEnum;
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
        HashMap<String, IStore> storesMap = MapUtil.newHashMap(3);
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
        if (StrUtil.isEmpty(key)) {
            key = StrUtil.toUnderlineCase(enumClass.getSimpleName()).toUpperCase(Locale.ROOT);
        }
        IStore store = new EnumStore(enumClass);
        storesMap.put(key, store);
    }
}
