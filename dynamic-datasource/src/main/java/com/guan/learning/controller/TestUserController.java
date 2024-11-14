package com.guan.learning.controller;

import com.guan.learning.context.DataSourceContext;
import com.guan.learning.mapper.TestUserMapper;
import com.guan.learning.pojo.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserController {

    @Autowired
    private TestUserMapper testUserMapper;

    @GetMapping("/{datasourceName}")
    public String getMasterData(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContext.setDataSource(datasourceName);
        TestUser testUser = testUserMapper.selectOne(null);
        DataSourceContext.removeDataSource();
        return testUser.getUserName();
    }

    @GetMapping("")
    public String annoWay() {
        TestUser testUser = testUserMapper.selectOne(null);
        return testUser.getUserName();
    }
}
