package com.guan.learning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guan.learning.anno.ThisDynamicDataSource;
import com.guan.learning.pojo.TestUser;
import org.springframework.core.annotation.Order;

@Order(Integer.MIN_VALUE)
@ThisDynamicDataSource(dataSourceName = "slave")
public interface TestUserMapper extends BaseMapper<TestUser> {
}
