package com.guan.learning.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.common.dict.model.BaseSysDictDataVo;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.context.DataSourceContext;
import com.guan.learning.mapper.UserMapper;
import com.guan.learning.pojo.User;
import com.guan.learning.pojo.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("")
    public BaseResponse<User> insert() {
        User user = new User();
        user.setAge(RandomUtil.randomInt(0, 99));
        user.setName(RandomUtil.randomChinese() + "" + RandomUtil.randomChinese());
        user.setEmail(RandomUtil.randomString(9) + "@qq.com");
        BaseSysDictDataVo gender = new BaseSysDictDataVo();
        gender.setDictDataCode(RandomUtil.randomBoolean() ? "female" : "male");
        user.setGender(gender);
        user.setRole(RandomUtil.randomBoolean() ? SysRoleEnum.USER : SysRoleEnum.ADMIN);
        userMapper.insert(user);
        return CommonResponse.withSuccess(user);
    }

    @GetMapping("/{bizId}")
    public BaseResponse<User> get(@PathVariable String bizId) {
        return CommonResponse.withSuccess(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getBizId, bizId)));
    }

    @DeleteMapping("/{bizId}")
    public BaseResponse<String> delete(@PathVariable String bizId) {
        userMapper.delete((Wrappers.lambdaUpdate(User.class).eq(User::getBizId, bizId)));
        return CommonResponse.withSuccess();
    }

    @GetMapping("/all")
    public BaseResponse<List<User>> getAll() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return CommonResponse.withSuccess(users);
    }

    @GetMapping("/page")
    public BaseResponse<IPage<User>> page(UserRequest userRequest) {
        IPage<User> userIPage = userMapper.selectPage(
                Page.of(userRequest.getPageNo(), userRequest.getPageSize()),
                Wrappers.lambdaQuery(User.class).
                        eq(StrUtil.isNotEmpty(userRequest.getName()), User::getName, userRequest.getName()).
                        between(userRequest.getStartAge() != null && userRequest.getEndAge() != null,
                                User::getAge, userRequest.getStartAge(), userRequest.getEndAge()).
                        likeRight(StrUtil.isNotEmpty(userRequest.getEmail()), User::getEmail, userRequest.getEmail()).
                        eq(StrUtil.isNotEmpty(userRequest.getGender()), User::getGender, userRequest.getGender()).
                        eq(userRequest.getRole() != null, User::getRole, userRequest.getRole().getCode())
        );
        return CommonResponse.withSuccess(userIPage);
    }

    @GetMapping("/datasource/{datasourceName}")
    public BaseResponse<List<User>> getMasterData(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContext.setDataSource(datasourceName);
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        DataSourceContext.removeDataSource();
        return CommonResponse.withSuccess(users);
    }

    @GetMapping("/datasource/annoWay")
    public BaseResponse<List<User>> annoWay() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        return CommonResponse.withSuccess(users);
    }
}
