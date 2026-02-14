package com.guan.learning.streamable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 流式处理结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamProcessResult {

    /**
     * 总数据量
     */
    private Long totalCount;

    /**
     * 处理后的数据量
     */
    private Long processedCount;

    /**
     * 处理后的数据列表
     */
    private List<?> data;

    /**
     * 统计信息
     */
    private Map<String, Object> statistics;

    /**
     * 处理耗时（毫秒）
     */
    private Long processTimeMs;
}
