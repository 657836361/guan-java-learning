package com.guan.learning.designPattern.chain.service.impl;


import com.guan.learning.designPattern.chain.pojo.ProcessContext;
import com.guan.learning.designPattern.chain.pojo.ProcessResult;
import com.guan.learning.designPattern.chain.service.AbstractProcessHandler;
import com.guan.learning.designPattern.chain.service.ProcessClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateProcessHandler extends AbstractProcessHandler {

    @Override
    protected void doInternalHandler(ProcessContext request, ProcessResult result, ProcessClient processClient) {
        log.info("UpdateProcessHandler");
    }

    @Override
    public int getOrder() {
        return 1000;
    }

}
