package com.guan.learning.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 增加一个动态数据源的mapper
 * 可以通过threadlocal修改
 */
@Mapper
public interface DynamivDataSourceUserMapper extends BaseMapper<BaseUser> {
}
