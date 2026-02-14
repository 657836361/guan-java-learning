package com.guan.datasource.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class AutoFillingMetaObjectHandler implements MetaObjectHandler {

    @PostConstruct
    public void init() {
        log.info("MetaObjectHandler inited");
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "bizId", String.class, UUID.randomUUID().toString());
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, Date.from(Instant.now()));
        this.strictInsertFill(metaObject, "createUser", String.class, "auto");
        this.strictInsertFill(metaObject, "gmtModify", Date.class, Date.from(Instant.now()));
        this.strictInsertFill(metaObject, "modifyUser", String.class, "auto");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModify", Date.class, Date.from(Instant.now()));
        this.strictUpdateFill(metaObject, "modifyUser", String.class, "auto");
    }
}
