package com.guan.learning.cache;

import cn.hutool.core.map.MapUtil;
import com.guan.common.cache.store.IStore;
import com.guan.common.cache.store.IstoreConfig;
import com.guan.datasource.dict.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictDataMappingConfig implements IstoreConfig {

    @Autowired(required = false)
    private SysDictDataMapper mapper;

    @Override
    public Map<String, IStore> cacheTask() {
        if (mapper != null) {
            HashMap<String, IStore> storesMap = MapUtil.newHashMap(2);
            addDictDataStore(storesMap, "SYS_USER_GENDER");
            addDictDataStore(storesMap, "SYS_COMMON_STATUS");
            return storesMap;
        }
        return null;
    }


    private void addDictDataStore(HashMap<String, IStore> storesMap, String dictType) {
        DictDataStore dictDataStore = new DictDataStore(dictType, mapper);
        storesMap.put(dictType, dictDataStore);
    }
}
