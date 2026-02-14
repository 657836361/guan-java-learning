package com.guan.learning.streamable.controller;

import com.guan.common.pojo.response.BaseResponse;
import com.guan.common.pojo.response.CommonResponse;
import com.guan.learning.streamable.dto.StreamFilterRequest;
import com.guan.learning.streamable.dto.StreamPageRequest;
import com.guan.learning.streamable.dto.StreamProcessResult;
import com.guan.learning.streamable.service.StreamableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流式处理控制器
 */
@Slf4j
@RestController
@RequestMapping("/streamable")
@Validated
public class StreamableController {

    @Autowired
    private StreamableService streamableService;

    /**
     * 流式数据过滤
     * GET /streamable/filter?dataSize=1000&minAge=20&maxAge=40
     */
    @GetMapping("/filter")
    public BaseResponse<StreamProcessResult> filter(@Valid StreamFilterRequest request) {
        log.info("开始流式过滤，数据量: {}, 最小年龄: {}, 最大年龄: {}, 姓名关键字: {}",
                request.getDataSize(), request.getMinAge(), request.getMaxAge(), request.getNameKeyword());
        StreamProcessResult result = streamableService.filter(request);
        return CommonResponse.withSuccess(result);
    }

    /**
     * 流式分页查询
     * GET /streamable/page?pageNo=1&pageSize=20&dataSize=1000&sortBy=age
     */
    @GetMapping("/page")
    public BaseResponse<StreamProcessResult> page(@Valid StreamPageRequest request) {
        log.info("流式分页查询，页码: {}, 大小: {}, 数据量: {}, 排序字段: {}",
                request.getPageNo(), request.getPageSize(), request.getDataSize(), request.getSortBy());
        StreamProcessResult result = streamableService.page(request);
        return CommonResponse.withSuccess(result);
    }

    /**
     * 流式数据统计
     * GET /streamable/statistics?dataSize=1000
     */
    @GetMapping("/statistics")
    public BaseResponse<StreamProcessResult> statistics(
            @RequestParam @Min(value = 1, message = "数据量必须大于0") Integer dataSize) {
        log.info("开始流式统计，数据量: {}", dataSize);
        StreamProcessResult result = streamableService.statistics(dataSize);
        return CommonResponse.withSuccess(result);
    }
}
