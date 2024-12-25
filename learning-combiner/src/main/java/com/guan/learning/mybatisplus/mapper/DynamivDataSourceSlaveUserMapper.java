package com.guan.learning.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.learning.dynamic.anno.DataSourceName;
import com.guan.learning.dynamic.enums.DataSourceEnum;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 增加一个动态数据源 注解影响 写死为slave的mapper
 */
@Mapper
@DataSourceName(dataSourceName = DataSourceEnum.SLAVE)
public interface DynamivDataSourceSlaveUserMapper extends BaseMapper<BaseUser> {
}
