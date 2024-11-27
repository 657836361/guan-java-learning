package com.guan.learning.strategy.pojo.context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditContext extends BaseContext {

    private String auditResult;

    public AuditContext(Long id, String auditResult) {
        super(id);
        this.auditResult = auditResult;
    }
}
