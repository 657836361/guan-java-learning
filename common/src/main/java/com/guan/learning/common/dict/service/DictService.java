package com.guan.learning.common.dict.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.learning.common.dict.mapper.SysDictDataMapper;
import com.guan.learning.common.dict.model.SysDictData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DictService implements InitializingBean {
    private static final List<SysDictData> CACHE = new CopyOnWriteArrayList<>();
    private static final Map<String, SysDictData> CACHE_DATA_CODE = new HashMap<>();

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    public List<SysDictData> list() {
        if (!CollectionUtils.isEmpty(CACHE)) {
            return CACHE;
        }
        List<SysDictData> sysDictData = sysDictDataMapper.selectList(Wrappers.lambdaQuery());
        CACHE.addAll(sysDictData);
        return sysDictData;
    }

    public void cacheRefresh() {
        CACHE.clear();
        CACHE_DATA_CODE.clear();
        this.list();
        this.cacheDataCode();
    }

    public SysDictData getDictDataByDataCode(String dataCode) {
        SysDictData sysDictData = CACHE_DATA_CODE.get(dataCode);
        if (sysDictData == null) {
            this.cacheDataCode();
        }
        return CACHE_DATA_CODE.get(dataCode);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.cacheDataCode();
    }

    /**
     * 提供给typehandler使用
     *
     * @param dataCode
     * @return
     */
    public static SysDictData getSysDictDataByDataCode(String dataCode) {
        return CACHE_DATA_CODE.get(dataCode);
    }

    /**
     * 构建dataCode:dictdata缓存
     */
    private void cacheDataCode() {
        List<SysDictData> list = this.list();
        list.forEach(value -> {
            CACHE_DATA_CODE.put(value.getDictDataCode(), value);
        });
    }
}
