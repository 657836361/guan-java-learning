package com.guan.learning.designPattern.chain.controller;


import com.guan.learning.designPattern.chain.pojo.ProcessContext;
import com.guan.learning.designPattern.chain.pojo.ProcessResult;
import com.guan.learning.designPattern.chain.service.ProcessClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("chain")
public class ChainController {

    @Autowired
    private ProcessClient processClient;

    @GetMapping("")
    public void deal() {
        processClient.processNode(new ProcessContext(), new ProcessResult());
    }
}
