package com.guan.datasource.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

@Slf4j
public class AutoFillingMetaObjectHandler implements MetaObjectHandler {

    @PostConstruct
    public void init() {
        log.info("MetaObjectHandler inited");
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "bizId", String.class, IdUtil.fastSimpleUUID());
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "createUser", String.class, "auto");
        this.strictInsertFill(metaObject, "gmtModify", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "modifyUser", String.class, "auto");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModify", Date.class, DateUtil.date());
        this.strictUpdateFill(metaObject, "modifyUser", String.class, "auto");
    }
}
