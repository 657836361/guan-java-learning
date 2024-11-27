package com.guan.learning.strategy.pojo.context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealContext extends BaseContext {
    private String remark;

    public DealContext(Long id, String remark) {
        super(id);
        this.remark = remark;
    }
}
