package com.guan.learning.common.cache.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.guan.learning.common.cache.store.IStore;
import com.guan.learning.common.cache.store.IstoreConfig;
import com.guan.learning.common.cache.util.CacheUtil;
import com.guan.learning.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController implements ApplicationRunner {


    @Autowired(required = false)
    private List<IstoreConfig> storeConfigList;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (CollectionUtil.isNotEmpty(storeConfigList)) {
            log.info("load cache data...");
            for (IstoreConfig config : storeConfigList) {
                System.out.println(config.getClass().getName());
                System.out.println(Arrays.toString(config.getClass().getInterfaces()));
                Map<String, IStore> map = config.cacheTask();
                if (MapUtil.isNotEmpty(map)) {
                    CacheUtil.addTaskMap(map);
                }
            }
            CacheUtil.load();
            log.info("load cache data success...");
        }
    }

    @GetMapping("")
    public BaseResponse<Map<String, Map<String, Object>>> get(String type) {
        if (StrUtil.isEmpty(type)) {
            return BaseResponse.withResponse(ArgumentErrorResponseEnum.VALID_ERROR);
        }
        Map<String, Map<String, Object>> maps = CacheUtil.get(type.split(","));
        return CommonResponse.withSuccess(maps);
    }

    @PostMapping("/flush")
    public BaseResponse<String> flush() {
        CacheUtil.load();
        return CommonResponse.withSuccessMsg("全量刷新成功");
    }
}
