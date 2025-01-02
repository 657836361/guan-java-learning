package com.guan.learning.mybatisplus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guan.learning.common.enums.response.CommonErrorResponseEnum;
import com.guan.learning.common.exceptions.BusinessException;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.dynamic.context.DataSourceContext;
import com.guan.learning.mybatisplus.mapper.BaseUserMapper;
import com.guan.learning.mybatisplus.mapper.DynamicDataSourceMasterUserMapper;
import com.guan.learning.mybatisplus.mapper.DynamicDataSourceSlaveUserMapper;
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

    @Autowired(required = false)
    private DynamicDataSourceSlaveUserMapper dynamicDataSourceSlaveUserMapper;
    @Autowired(required = false)
    private DynamicDataSourceMasterUserMapper dynamicDataSourceMasterUserMapper;
    @Autowired(required = false)
    private BaseUserMapper baseUserMapper;

    /**
     * 只能通过参数控制数据源
     *
     * @param datasourceName
     * @return
     */
    @GetMapping("/datasource")
    public BaseResponse<List<BaseUser>> getMasterData(@RequestParam("datasourceName") String datasourceName) {
        if (baseUserMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        try {
            DataSourceContext.set(datasourceName);
            List<BaseUser> users = baseUserMapper.selectList(Page.of(10, 10, false), Wrappers.emptyWrapper());
            return CommonResponse.withSuccess(users);
        } finally {
            DataSourceContext.remove();
        }
    }

    /**
     * 通过@DataSourceFlag 注解 写死了是master
     *
     * @return
     */
    @GetMapping("/datasource/annoWay/master")
    public BaseResponse<List<BaseUser>> master() {
        if (dynamicDataSourceMasterUserMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        List<BaseUser> users = dynamicDataSourceMasterUserMapper.selectList(Page.of(10, 10, false), Wrappers.emptyWrapper());
        return CommonResponse.withSuccess(users);
    }

    /**
     * 通过@DataSourceFlag 注解 写死了是slave
     *
     * @return
     */
    @GetMapping("/datasource/annoWay/slave")
    public BaseResponse<List<BaseUser>> slave() {
        if (dynamicDataSourceSlaveUserMapper == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        List<BaseUser> users = dynamicDataSourceSlaveUserMapper.selectList(Page.of(10, 10, false), Wrappers.emptyWrapper());
        return CommonResponse.withSuccess(users);
    }
}
