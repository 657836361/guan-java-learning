package com.guan.learning.designPattern.strategy.pojo.request;

import com.guan.learning.designPattern.strategy.pojo.context.DealContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealRequest extends BaseRequest {
    private String remark;

    public DealContext context() {
        return new DealContext(this.getId(), this.getRemark());
    }
}
