package com.guan.learning.mybatisplus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.dynamic.context.DataSourceContext;
import com.guan.learning.mybatisplus.mapper.DynamivDataSourceSlaveUserMapper;
import com.guan.learning.mybatisplus.mapper.DynamivDataSourceUserMapper;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/switch")
public class SwitchDataSourceController {

    @Autowired
    private DynamivDataSourceSlaveUserMapper dynamivDataSourceSlaveUserMapper;
    @Autowired
    private DynamivDataSourceUserMapper dynamivDataSourceUserMapper;

    /**
     * 只能通过参数控制数据源
     *
     * @param datasourceName
     * @return
     */
    @GetMapping("/datasource")
    public BaseResponse<List<BaseUser>> getMasterData(@RequestParam("datasourceName") String datasourceName) {
        try {
            DataSourceContext.setDataSource(datasourceName);
            List<BaseUser> users = dynamivDataSourceUserMapper.
                    selectList(Page.of(10, 10, false), Wrappers.emptyWrapper());
            return CommonResponse.withSuccess(users);
        } finally {
            DataSourceContext.removeDataSource();
        }
    }

    /**
     * 通过@DataSourceName 注解 写死了是slave
     *
     * @return
     */
    @GetMapping("/datasource/annoWay")
    public BaseResponse<List<BaseUser>> annoWay() {
        List<BaseUser> users = dynamivDataSourceUserMapper.
                selectList(Page.of(10, 10, false), Wrappers.emptyWrapper());
        return CommonResponse.withSuccess(users);
    }
}
