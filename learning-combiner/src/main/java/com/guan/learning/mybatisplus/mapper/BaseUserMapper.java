package com.guan.learning.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    /**
     * mysql fetchSize必须是-2147483648才能启动流式查询
     * 详情请查看https://github.com/baomidou/mybatis-plus/issues/6485
     *
     * @param wrapper
     * @param handler
     */
    @Select("select * from base_user t ${ew.customSqlSegment}")
    // ResultSetType.FORWARD_ONLY 表示游标只向前滚动 fetchSize 每次获取量
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    @ResultType(BaseUser.class)
    void customQuery(@Param(Constants.WRAPPER) QueryWrapper<BaseUser> wrapper, ResultHandler<BaseUser> handler);

}
