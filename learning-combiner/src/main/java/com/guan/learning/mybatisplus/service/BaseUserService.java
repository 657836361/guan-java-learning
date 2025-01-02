package com.guan.learning.mybatisplus.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.common.enums.SysRoleEnum;
import com.guan.learning.common.enums.response.CommonErrorResponseEnum;
import com.guan.learning.common.exceptions.BusinessException;
import com.guan.learning.mybatisplus.mapper.BaseUserMapper;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import com.guan.learning.mybatisplus.pojo.BaseUserRequest;
import com.guan.learning.mybatisplus.pojo.BaseUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class BaseUserService {

    private static final List<BaseUser> LIST = new CopyOnWriteArrayList<>();

    @Autowired(required = false)
    private BaseUserMapper userMapper;

    public BaseUser insert() {
        BaseUser user = BaseUser.generateRandomUser();
        if (userMapper != null) {
            userMapper.insert(user);
        } else {
            user = BaseUser.generateRandomUser(user);
            LIST.add(user);
        }
        return user;
    }

    public BaseUser get(String bizId) {
        if (userMapper != null) {
            return userMapper.selectOne(Wrappers.<BaseUser>lambdaQuery().eq(BaseUser::getBizId, bizId));
        }
        return LIST.stream().filter(user -> Objects.equals(user.getBizId(), bizId)).findFirst().orElse(null);
    }


    public int delete(String bizId) {
        if (userMapper != null) {
            return userMapper.delete((Wrappers.lambdaUpdate(BaseUser.class).eq(BaseUser::getBizId, bizId)));
        } else {
            return LIST.removeIf(user -> Objects.equals(user.getBizId(), bizId)) ? 1 : 0;
        }
    }

    /**
     * 一下子查了很多很多 这种情况可能会oom
     * 推荐使用流式处理
     *
     * @return
     */
    public List<BaseUser> getAll() {
        if (userMapper != null) {
            return userMapper.selectList(new QueryWrapper<>());
        }
        return LIST;
    }

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    public void getAllStream() {
        if (userMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        userMapper.selectList(Wrappers.emptyWrapper(),
                resultContext -> {
                    log.info("开始处理第{}条数据", resultContext.getResultCount());
                    BaseUserVo.newInstance(resultContext.getResultObject());
                });
    }

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    public void getAllStreamAnother() {
        if (userMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        userMapper.customQuery(Wrappers.emptyWrapper(),
                resultContext -> {
                    log.info("自定义开始处理第{}条数据", resultContext.getResultCount());
                    BaseUserVo.newInstance(resultContext.getResultObject());
                });
    }

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    public void pageStream() {
        if (userMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        userMapper.selectList(Page.of(1, 10000),
                Wrappers.emptyWrapper(),
                resultContext -> {
                    log.info("开始处理第{}条数据", resultContext.getResultCount());
                    BaseUserVo.newInstance(resultContext.getResultObject());
                });
    }

    public IPage<BaseUser> page(BaseUserRequest userRequest) {
        if (userMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        return userMapper.selectPage(
                Page.of(userRequest.getPageNo(), userRequest.getPageSize()),
                Wrappers.lambdaQuery(BaseUser.class).
                        eq(StrUtil.isNotEmpty(userRequest.getName()), BaseUser::getName, userRequest.getName()).
                        between(userRequest.getStartAge() != null && userRequest.getEndAge() != null,
                                BaseUser::getAge, userRequest.getStartAge(), userRequest.getEndAge()).
                        likeRight(StrUtil.isNotEmpty(userRequest.getEmail()), BaseUser::getEmail, userRequest.getEmail()).
                        eq(StrUtil.isNotEmpty(userRequest.getGender()), BaseUser::getGender, userRequest.getGender()).
                        eq(userRequest.getRole() != null, BaseUser::getRole, Optional.ofNullable(userRequest.getRole()).map(SysRoleEnum::getCode).orElse(""))
        );
    }

    public IPage<BaseUser> pageDefault(BaseUserRequest userRequest) {
        if (userMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        return userMapper.selectPage(
                Page.of(Optional.ofNullable(userRequest.getPageNo()).orElse(1), Optional.ofNullable(userRequest.getPageSize()).orElse(20)),
                Wrappers.lambdaQuery(BaseUser.class).
                        eq(StrUtil.isNotEmpty(userRequest.getName()), BaseUser::getName, userRequest.getName()).
                        between(userRequest.getStartAge() != null && userRequest.getEndAge() != null,
                                BaseUser::getAge, userRequest.getStartAge(), userRequest.getEndAge()).
                        likeRight(StrUtil.isNotEmpty(userRequest.getEmail()), BaseUser::getEmail, userRequest.getEmail()).
                        eq(StrUtil.isNotEmpty(userRequest.getGender()), BaseUser::getGender, userRequest.getGender()).
                        eq(userRequest.getRole() != null, BaseUser::getRole, Optional.ofNullable(userRequest.getRole()).map(SysRoleEnum::getCode).orElse(""))
        );
    }
}
