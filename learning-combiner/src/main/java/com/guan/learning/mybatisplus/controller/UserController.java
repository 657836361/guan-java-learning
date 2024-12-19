package com.guan.learning.mybatisplus.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.dynamic.context.DataSourceContext;
import com.guan.learning.mybatisplus.mapper.UserMapper;
import com.guan.learning.mybatisplus.pojo.User;
import com.guan.learning.mybatisplus.pojo.UserRequest;
import com.guan.learning.mybatisplus.pojo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
@ConditionalOnBean(DataSource.class)
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("")
    public BaseResponse<User> insert() {
        User user = User.generateRandomUser();
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

    /**
     * 一下子查了很多很多 这种情况可能会oom
     * 推荐使用流式处理
     *
     * @return
     */
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
    @GetMapping("/all/stream")
    public BaseResponse<Void> getAllStream() {
        userMapper.selectList(Wrappers.emptyWrapper(),
                resultContext -> {
                    log.info("开始处理第{}条数据", resultContext.getResultCount());
                    UserVo.newInstance(resultContext.getResultObject());
                });
        return CommonResponse.withSuccess();
    }

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    @GetMapping("/page/stream")
    public BaseResponse<Void> pageStream() {
        userMapper.selectList(Page.of(1, 10000),
                Wrappers.emptyWrapper(),
                resultContext -> {
                    log.info("开始处理第{}条数据", resultContext.getResultCount());
                    UserVo.newInstance(resultContext.getResultObject());
                });
        return CommonResponse.withSuccess();
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

    @GetMapping("/page/default")
    public BaseResponse<IPage<User>> pageDefault(UserRequest userRequest) {
        IPage<User> userIPage = userMapper.selectPage(
                Page.of(Optional.ofNullable(userRequest.getPageNo()).orElse(1), Optional.ofNullable(userRequest.getPageSize()).orElse(20)),
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
