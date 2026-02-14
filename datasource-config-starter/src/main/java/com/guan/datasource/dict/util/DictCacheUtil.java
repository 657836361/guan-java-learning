package com.guan.datasource.dict.util;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guan.datasource.dict.mapper.SysDictDataMapper;
import com.guan.datasource.dict.model.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
public class DictCacheUtil implements ApplicationContextAware, SmartInitializingSingleton {


    private static final Cache<String, SysDictData> CACHE_DATA_CODE = CacheBuilder.newBuilder().build();

    private static SysDictDataMapper mapper;

    public static SysDictData get(String dataCode) {
        SysDictData sysDictData = CACHE_DATA_CODE.getIfPresent(dataCode);
        if (sysDictData != null) {
            return sysDictData;
        }
        if (mapper != null) {
            sysDictData = mapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictDataCode, dataCode));
            if (sysDictData != null) {
                try {
                    return sysDictData;
                } finally {
                    CACHE_DATA_CODE.put(dataCode, sysDictData);
                }
            }
        }
        return null;
    }

    public static void cacheRefresh() {
        CACHE_DATA_CODE.invalidateAll();
        init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            mapper = applicationContext.getBean(SysDictDataMapper.class);
            log.info("DictCacheUtil setApplicationContexted");
        } catch (BeansException e) {
            log.error("error:", e);
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        init();
    }

    private static void init() {
        mapper.selectList(Wrappers.emptyWrapper(), resultContext -> {
            SysDictData sysDictData = resultContext.getResultObject();
            CACHE_DATA_CODE.put(sysDictData.getDictDataCode(), sysDictData);
        });
    }
}
