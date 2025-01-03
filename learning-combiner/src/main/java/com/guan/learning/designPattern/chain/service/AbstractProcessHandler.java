package com.guan.learning.designPattern.chain.service;

import com.guan.learning.designPattern.chain.pojo.ProcessContext;
import com.guan.learning.designPattern.chain.pojo.ProcessResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractProcessHandler implements InitializingBean, Ordered {

    /**
     * 流程节点执行
     *
     * @param request
     * @param result
     * @param processClient
     */
    public final void doHandler(ProcessContext request, ProcessResult result, ProcessClient processClient) {
        if (processClient == null) {
            return;
        }
        this.doInternalHandler(request, result, processClient);
        processClient.processNode(request, result);
    }

    protected abstract void doInternalHandler(ProcessContext request, ProcessResult result, ProcessClient processClient);

    @Override
    public void afterPropertiesSet() throws Exception {
        ProcessChainFactory.register(this);
    }
}
