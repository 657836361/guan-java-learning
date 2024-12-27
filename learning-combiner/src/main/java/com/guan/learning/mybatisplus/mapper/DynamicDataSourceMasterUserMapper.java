package com.guan.learning.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.learning.dynamic.anno.DataSourceFlag;
import com.guan.learning.dynamic.enums.DataSourceFlagEnum;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 增加一个动态数据源的mapper
 * 可以通过threadlocal修改
 */
@Mapper
@DataSourceFlag(flagEnum = DataSourceFlagEnum.MASTER)
public interface DynamicDataSourceMasterUserMapper extends BaseMapper<BaseUser> {
}
