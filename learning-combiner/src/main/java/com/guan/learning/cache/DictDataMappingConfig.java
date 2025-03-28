package com.guan.learning.cache;

import com.guan.learning.common.cache.store.IStore;
import com.guan.learning.common.cache.store.IstoreConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictDataMappingConfig implements IstoreConfig {

//    @Autowired(required = false)
//    private SysDictDataMapper mapper;

    @Override
    public Map<String, IStore> cacheTask() {
//        if (mapper != null) {
//            HashMap<String, IStore> storesMap = MapUtil.newHashMap(2);
//            addDictDataStore(storesMap, "sys_user_gender");
//            addDictDataStore(storesMap, "sys_common_status");
//            return storesMap;
//        }
        return null;
    }


    private void addDictDataStore(HashMap<String, IStore> storesMap, String dictType) {
//        DictDataStore dictDataStore = new DictDataStore(dictType, mapper);
//        storesMap.put(dictType, dictDataStore);
    }
}
