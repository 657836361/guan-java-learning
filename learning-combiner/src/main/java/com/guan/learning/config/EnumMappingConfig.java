package com.guan.learning.config;

import com.guan.learning.common.cache.store.IStore;
import com.guan.learning.common.cache.store.IstoreConfig;
import com.guan.learning.common.enums.DeleteStatusEnum;
import com.guan.learning.common.enums.StatusEnum;
import com.guan.learning.common.enums.SysRoleEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举的缓存配置
 */
@Component
public class EnumMappingConfig implements IstoreConfig {
    @Override
    public Map<String, IStore> cacheTask() {
        HashMap<String, IStore> storesMap = new HashMap<>();
        addEnumStore(storesMap, StatusEnum.class);
        addEnumStore(storesMap, DeleteStatusEnum.class);
        addEnumStore(storesMap, SysRoleEnum.class);
        return storesMap;
    }
}
