package com.guan.learning.common.pojo.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    @Min(value = 1L, message = "当前页码不可为空")
    private int pageNo;
    @Min(value = 1L, message = "页面数据条数最小为1")
    private int pageSize;
}
