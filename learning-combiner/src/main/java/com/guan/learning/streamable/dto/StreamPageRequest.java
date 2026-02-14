package com.guan.learning.streamable.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 流式分页请求
 */
@Data
public class StreamPageRequest {

    /**
     * 页码
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小必须大于0")
    @Max(value = 1000, message = "每页大小不能超过1000")
    private Integer pageSize = 20;

    /**
     * 数据量
     */
    @Min(value = 1, message = "数据量必须大于0")
    @Max(value = 100000, message = "数据量不能超过100000")
    private Integer dataSize = 1000;

    /**
     * 排序字段（name, age, email, role）
     */
    private String sortBy = "age";
}
