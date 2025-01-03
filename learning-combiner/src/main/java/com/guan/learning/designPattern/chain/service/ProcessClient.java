package com.guan.learning.designPattern.chain.service;


import com.guan.learning.designPattern.chain.pojo.ProcessContext;
import com.guan.learning.designPattern.chain.pojo.ProcessResult;

public interface ProcessClient {

    void processNode(ProcessContext request, ProcessResult result);

}
