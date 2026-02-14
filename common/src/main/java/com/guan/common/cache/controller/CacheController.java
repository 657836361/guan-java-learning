package com.guan.common.cache.controller;


import com.guan.common.cache.store.IStore;
import com.guan.common.cache.store.IstoreConfig;
import com.guan.common.cache.util.CacheUtil;
import com.guan.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.common.pojo.response.BaseResponse;
import com.guan.common.pojo.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (CollectionUtils.isNotEmpty(storeConfigList)) {
            log.info("load cache data...");
            for (IstoreConfig config : storeConfigList) {
                Map<String, IStore> map = config.cacheTask();
                if (MapUtils.isNotEmpty(map)) {
                    CacheUtil.addTaskMap(map);
                }
            }
            CacheUtil.load();
            log.info("load cache data success...");
        }
    }

    @GetMapping("")
    public BaseResponse<Map<String, Map<String, Object>>> get(String type) {
        if (StringUtils.isEmpty(type)) {
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
