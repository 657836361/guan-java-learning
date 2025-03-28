package com.guan.learning.cache;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.common.cache.store.IStore;
import com.guan.datasource.dict.mapper.SysDictDataMapper;
import com.guan.datasource.dict.model.SysDictData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictDataStore implements IStore {
    private String dictType;
    private SysDictDataMapper mapper;

    public DictDataStore(String dictType, SysDictDataMapper mapper) {
        Assert.notEmpty(dictType, "dictType is empty");
        Assert.notNull(mapper, "mapper is null");
        this.dictType = dictType;
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> load() {
        List<SysDictData> list = mapper.selectList(
                Wrappers.lambdaQuery(SysDictData.class).
                        select(SysDictData::getDictDataCode, SysDictData::getDictDataName).
                        eq(SysDictData::getDictTypeCode, dictType)
        );
        return list.stream().
                collect(Collectors.toMap(SysDictData::getDictDataCode, SysDictData::getDictDataName));
    }
}
