package com.guan.learning.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.learning.common.dict.model.BaseSysDictDataVo;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.context.DataSourceContext;
import com.guan.learning.mapper.UserMapper;
import com.guan.learning.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("")
    public ResponseEntity<?> insert() {
        User user = new User();
        user.setAge(RandomUtil.randomInt(0, 99));
        user.setName(RandomUtil.randomChinese() + "" + RandomUtil.randomChinese());
        user.setEmail(RandomUtil.randomString(9) + "@qq.com");
        BaseSysDictDataVo gender = new BaseSysDictDataVo();
        gender.setDictDataCode(RandomUtil.randomBoolean() ? "female" : "male");
        user.setGender(gender);
        user.setRole(RandomUtil.randomBoolean() ? SysRoleEnum.USER : SysRoleEnum.ADMIN);
        userMapper.insert(user);
        return ResponseEntity.ofNullable(user);
    }

    @GetMapping("/{bizId}")
    public ResponseEntity<?> get(@PathVariable String bizId) {
        return ResponseEntity.ofNullable(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getBizId, bizId)));
    }

    @DeleteMapping("/{bizId}")
    public ResponseEntity<?> delete(@PathVariable String bizId) {
        userMapper.delete((Wrappers.lambdaUpdate(User.class).eq(User::getBizId, bizId)));
        return ResponseEntity.ofNullable("ok");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return ResponseEntity.ofNullable(users);
    }

    @GetMapping("/{datasourceName}")
    public ResponseEntity<?> getMasterData(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContext.setDataSource(datasourceName);
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        DataSourceContext.removeDataSource();
        return ResponseEntity.ofNullable(users);
    }

    @GetMapping("/annoWay")
    public ResponseEntity<?> annoWay() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return ResponseEntity.ofNullable(users);
    }
}
