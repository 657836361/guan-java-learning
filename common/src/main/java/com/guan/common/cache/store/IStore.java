package com.guan.common.cache.store;

import java.util.Map;

public interface IStore {

    Map<String, Object> load();
}
