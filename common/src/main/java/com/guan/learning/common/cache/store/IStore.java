package com.guan.learning.common.cache.store;

import java.util.Map;

public interface IStore {

    Map<String, Object> load();
}
