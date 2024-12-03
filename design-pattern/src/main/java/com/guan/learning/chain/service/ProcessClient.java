package com.guan.learning.chain.service;

import com.guan.learning.chain.pojo.ProcessContext;
import com.guan.learning.chain.pojo.ProcessResult;


public interface ProcessClient {

    void processNode(ProcessContext request, ProcessResult result);

}
