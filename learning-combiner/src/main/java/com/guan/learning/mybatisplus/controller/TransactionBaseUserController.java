package com.guan.learning.mybatisplus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guan.learning.common.enums.response.CommonErrorResponseEnum;
import com.guan.learning.common.exceptions.BusinessException;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.mybatisplus.mapper.BaseUserMapper;
import com.guan.learning.mybatisplus.mapper.DynamicDataSourceMasterUserMapper;
import com.guan.learning.mybatisplus.mapper.DynamicDataSourceSlaveUserMapper;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transaction/user")
public class TransactionBaseUserController {

    @Autowired(required = false)
    private TransactionTemplate transactionTemplate;

    @Autowired(required = false)
    private DynamicDataSourceMasterUserMapper masterUserMapper;

    @Autowired(required = false)
    private DynamicDataSourceSlaveUserMapper slaveUserMapper;

    @Autowired(required = false)
    private BaseUserMapper baseUserMapper;


    @PostMapping("/case1")
    public BaseResponse<Void> transactionTheSameSearch() {
        if (transactionTemplate == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        transactionTemplate.executeWithoutResult((status) -> {
            BaseUser baseUser = BaseUser.generateRandomUser();
            baseUserMapper.insert(baseUser);
            BaseUser another = baseUserMapper.selectOne(Wrappers.<BaseUser>lambdaQuery().eq(BaseUser::getBizId, baseUser.getBizId()));
            BaseUser another1 = baseUserMapper.selectOne(Wrappers.<BaseUser>lambdaQuery().eq(BaseUser::getBizId, baseUser.getBizId()));
            log.info("在一个事务中查询同一个sql 是否命中一级缓存 {}", another == another1);
            baseUserMapper.insert(BaseUser.generateRandomUser());
            BaseUser another2 = baseUserMapper.selectOne(Wrappers.<BaseUser>lambdaQuery().eq(BaseUser::getBizId, baseUser.getBizId()));
            log.info("在一个事务中有update操作后 是否命中一级缓存 {}", another == another2);
        });
        return CommonResponse.withSuccess();
    }


    /**
     * @return
     * @since 3.5.4
     */
    @PostMapping("/case2")
    public BaseResponse<Void> transaction() {
        if (transactionTemplate == null) {
            throw new BusinessException(CommonErrorResponseEnum.DATASOURCE_UNABLE);
        }
        transactionTemplate.executeWithoutResult((status) -> {
            masterUserMapper.insert(BaseUser.generateRandomUser());
            slaveUserMapper.insert(BaseUser.generateRandomUser());
        });
        return CommonResponse.withSuccess();
    }


}
