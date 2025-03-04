package com.guan.learning.redis.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;


    @GetMapping("/test")
    public String redisTest() {
        if (redisTemplate != null) {
            return "redis inited";
        }
        return "redis fail to init";
    }

    @GetMapping("")
    public String add() {
        for (int i = 0; i < 10000; i++) {
            String uuid = IdUtil.fastUUID();
            redisTemplate.opsForValue().set(uuid, uuid);
        }
        return "ok";
    }
}
