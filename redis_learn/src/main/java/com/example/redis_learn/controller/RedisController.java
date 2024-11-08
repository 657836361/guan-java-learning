package com.example.redis_learn.controller;

import com.example.redis_learn.config.RedisSubConfig;
import com.example.redis_learn.service.RedisService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GG
 * @date 2022/4/13
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/set/{key}/{value}")
    public void set(@PathVariable String key, @PathVariable String value) {
        redisService.setCacheObject(key, value);
    }

    @GetMapping("/set")
    public void set() {
        User user = new User();
        user.setId(1L);
        user.setName("关培");
        redisService.setCacheObject("user", user);
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return redisService.getCacheObject(key);
    }

    @GetMapping("/pub/{msg}")
    public void pub(@PathVariable String msg) {
        redisService.pub(RedisSubConfig.SUB_KEY, msg);
    }

    @Getter
    @Setter
    public static class User {

        private Long id;
        private String name;
    }
}
