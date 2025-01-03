package com.guan.learning.designPattern.chain.service.impl;


import com.guan.learning.designPattern.chain.pojo.ProcessContext;
import com.guan.learning.designPattern.chain.pojo.ProcessResult;
import com.guan.learning.designPattern.chain.service.AbstractProcessHandler;
import com.guan.learning.designPattern.chain.service.ProcessChainFactory;
import com.guan.learning.designPattern.chain.service.ProcessClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class ProcessClientImpl implements ProcessClient {
    @Override
    public void processNode(ProcessContext request, ProcessResult result) {
        List<AbstractProcessHandler> processChains = ProcessChainFactory.getHandlerChain();

        final AtomicInteger currentPosition = new AtomicInteger(0);
        ProcessClient client = new ProcessClient() {
            @Override
            public void processNode(ProcessContext request, ProcessResult result) {
                if (currentPosition.get() >= processChains.size()) {
                    return;
                }
                AbstractProcessHandler nextHandler = processChains.get(currentPosition.getAndIncrement());
                nextHandler.doHandler(request, result, this);
            }
        };
        client.processNode(request, result);
        log.info("processChains done");
    }
}
