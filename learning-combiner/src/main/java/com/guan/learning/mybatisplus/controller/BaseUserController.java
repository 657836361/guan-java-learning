package com.guan.learning.mybatisplus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guan.learning.common.enums.response.ArgumentErrorResponseEnum;
import com.guan.learning.common.pojo.response.BaseResponse;
import com.guan.learning.common.pojo.response.CommonResponse;
import com.guan.learning.mybatisplus.pojo.BaseUser;
import com.guan.learning.mybatisplus.pojo.BaseUserRequest;
import com.guan.learning.mybatisplus.service.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class BaseUserController {

    @Autowired
    private BaseUserService service;

    @PostMapping("")
    public BaseResponse<BaseUser> insert() {
        return CommonResponse.withSuccess(service.insert());
    }

    @GetMapping("/{bizId}")
    public BaseResponse<BaseUser> get(@PathVariable String bizId) {
        return CommonResponse.withSuccess(service.get(bizId));
    }

    @DeleteMapping("/{bizId}")
    public BaseResponse<String> delete(@PathVariable String bizId) {
        int deleted = service.delete(bizId);
        if (deleted == 0) {
            CommonResponse.withError(ArgumentErrorResponseEnum.DATA_NOT_EXIST);
        }
        return CommonResponse.withSuccess();
    }

    /**
     * 一下子查了很多很多 这种情况可能会oom
     * 推荐使用流式处理
     *
     * @return
     */
    @GetMapping("/all")
    public BaseResponse<List<BaseUser>> getAll() {
        return CommonResponse.withSuccess(service.getAll());
    }

    /**
     * MyBatis-Plus 从 3.5.4 版本开始支持流式查询
     *
     * @return
     * @since 3.5.4
     */
    @GetMapping("/all/stream")
    public BaseResponse<Void> getAllStream() {
        service.getAllStream();
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
        service.pageStream();
        return CommonResponse.withSuccess();
    }

    @GetMapping("/page")
    public BaseResponse<IPage<BaseUser>> page(BaseUserRequest userRequest) {
        return CommonResponse.withSuccess(service.page(userRequest));
    }

    @GetMapping("/page/default")
    public BaseResponse<IPage<BaseUser>> pageDefault(BaseUserRequest userRequest) {
        return CommonResponse.withSuccess(service.pageDefault(userRequest));
    }
// todo 这两个接口需要继续修改

//    @GetMapping("/datasource/{datasourceName}")
//    public BaseResponse<List<BaseUser>> getMasterData(@PathVariable("datasourceName") String datasourceName) {
//        DataSourceContext.setDataSource(datasourceName);
//        List<BaseUser> users = userMapper.selectList(new QueryWrapper<>());
//        DataSourceContext.removeDataSource();
//        return CommonResponse.withSuccess(users);
//    }
//
//    @GetMapping("/datasource/annoWay")
//    public BaseResponse<List<BaseUser>> annoWay() {
//        List<BaseUser> users = userMapper.selectList(new QueryWrapper<>());
//        return CommonResponse.withSuccess(users);
//    }
}
