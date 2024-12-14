package com.guan.learning.mybatisplus.controller;

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
import com.guan.learning.mybatisplus.mapper.UserMapper;
import com.guan.learning.mybatisplus.pojo.User;
import com.guan.learning.mybatisplus.pojo.UserRequest;
import com.guan.learning.mybatisplus.pojo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
        StringBuilder name = new StringBuilder();
        int randomInt = RandomUtil.randomInt(2, 6);
        for (int i = 0; i < randomInt; i++) {
            name.append(RandomUtil.randomChinese());
        }
        user.setName(name.toString());
        user.setEmail(RandomUtil.randomString(RandomUtil.randomInt(6, 10)) +
                "@" +
                RandomUtil.randomString(RandomUtil.randomInt(2, 5)) +
                ".com" + (RandomUtil.randomBoolean() ? ".cn" : ""));
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

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    @GetMapping("/all/lambda")
    public BaseResponse<List<UserVo>> getAllLambda() {
        List<UserVo> users = new ArrayList<>();
        userMapper.selectList(Wrappers.emptyWrapper(), resultContext -> {
            log.info("开始处理第{}条数据", resultContext.getResultCount());
            User user = resultContext.getResultObject();
            users.add(UserVo.newInstance(user));
        });
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
                        eq(userRequest.getRole() != null, User::getRole, Optional.ofNullable(userRequest.getRole()).map(SysRoleEnum::getCode).orElse(""))
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