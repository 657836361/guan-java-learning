package com.guan.learning.dict.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.learning.LearningCombinerApplication;
import com.guan.learning.anno.EnableDataSource;
import com.guan.learning.common.config.MapperScanConfig;
import com.guan.learning.dict.mapper.SysDictDataMapper;
import com.guan.learning.dict.model.SysDictData;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(MapperScanConfig.class)
public class DictCacheUtil implements ApplicationContextAware, SmartInitializingSingleton {

    public static final int CACHE_QUEUE_SIZE = 10;

    private static final LFUCache<String, SysDictData> CACHE_DATA_CODE = CacheUtil.newLFUCache(CACHE_QUEUE_SIZE);

    private static SysDictDataMapper mapper;

    public static SysDictData get(String dataCode) {
        SysDictData sysDictData = CACHE_DATA_CODE.get(dataCode);
        if (sysDictData != null) {
            return sysDictData;
        }
        sysDictData = mapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictDataCode, dataCode));
        if (sysDictData != null) {
            try {
                return sysDictData;
            } finally {
                CACHE_DATA_CODE.put(dataCode, sysDictData);
            }
        }
        return null;
    }

    public static void cacheRefresh() {
        CACHE_DATA_CODE.clear();
        init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EnableDataSource enableDataSource = AnnotationUtils.findAnnotation(LearningCombinerApplication.class, EnableDataSource.class);
        if (enableDataSource != null) {
            mapper = applicationContext.getBean(SysDictDataMapper.class);
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        init();
    }

    private static void init() {
        if (mapper != null) {
            mapper.selectList(Wrappers.emptyWrapper(), resultContext -> {
                if (resultContext.getResultCount() > CACHE_QUEUE_SIZE) {
                    return;
                }
                SysDictData sysDictData = resultContext.getResultObject();
                CACHE_DATA_CODE.put(sysDictData.getDictDataCode(), sysDictData);
            });
        }
    }
}
