package com.guan.learning.streamable.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 流式过滤请求
 */
@Data
public class StreamFilterRequest {

    /**
     * 数据量
     */
    @Min(value = 1, message = "数据量必须大于0")
    @Max(value = 100000, message = "数据量不能超过100000")
    private Integer dataSize = 1000;

    /**
     * 最小年龄
     */
    @Min(value = 0, message = "最小年龄不能小于0")
    @Max(value = 150, message = "最小年龄不能大于150")
    private Integer minAge;

    /**
     * 最大年龄
     */
    @Min(value = 0, message = "最大年龄不能小于0")
    @Max(value = 150, message = "最大年龄不能大于150")
    private Integer maxAge;

    /**
     * 姓名关键字
     */
    private String nameKeyword;
}
