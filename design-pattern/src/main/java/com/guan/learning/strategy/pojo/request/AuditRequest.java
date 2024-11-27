package com.guan.learning.strategy.pojo.request;

import com.guan.learning.strategy.pojo.context.AuditContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditRequest extends BaseRequest {
    private String auditResult;


    public AuditContext context() {
        return new AuditContext(this.getId(), this.getAuditResult());
    }
}
