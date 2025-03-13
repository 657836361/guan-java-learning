package com.guan.learning.redis.controller;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClusterConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController implements ApplicationRunner {

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;


    @GetMapping("")
    public String add() {
        for (int i = 0; i < 30000; i++) {
            String uuid = IdUtil.fastUUID();
            redisTemplate.opsForValue().set(uuid, uuid);
        }
        return "ok";
    }

    @Async
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (redisTemplate == null) {
            return;
        }
        if (!checkAllMastersAlive()) {
            log.info("redis cluster is not alive");
            return;
        }
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions().build())) {
            if (!cursor.hasNext()) {
                add();
                log.info("redis cluster random key sets");
            }
        }
        log.info("redis cluster is alive");
    }

    private boolean checkAllMastersAlive() {
        try {
            LettuceClusterConnection clusterConnection = (LettuceClusterConnection) redisTemplate.getConnectionFactory().getClusterConnection();
            return clusterConnection.clusterGetNodes().stream().filter(RedisNode::isMaster).allMatch(node -> {
                try {
                    return "PONG".equals(clusterConnection.ping(node));
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }
}
