package com.guan.common.pojo.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    @Min(value = 1L, message = "当前页码不可为空")
    private Integer pageNo;
    @Min(value = 1L, message = "页面数据条数最小为1")
    private Integer pageSize;
}
