package com.guan.learning.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guan.learning.context.DataSourceContext;
import com.guan.learning.mapper.UserMapper;
import com.guan.learning.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{datasourceName}")
    public ResponseEntity<?> getMasterData(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContext.setDataSource(datasourceName);
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        DataSourceContext.removeDataSource();
        return ResponseEntity.ofNullable(users);
    }

    @GetMapping("")
    public ResponseEntity<?> annoWay() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return ResponseEntity.ofNullable(users);
    }
}
