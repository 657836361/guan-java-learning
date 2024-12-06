package com.guan.learning.common.bean;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Date;

@Slf4j
@Component
@MapperScan("com.guan.learning.common.dict.mapper")
@ConditionalOnBean(DataSource.class)
public class AutoFillingMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "bizId", String.class, IdUtil.fastSimpleUUID());
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "createUser", String.class, "auto");
        this.strictInsertFill(metaObject, "gmtModify", Date.class, DateUtil.date());
        this.strictInsertFill(metaObject, "modifyUser", String.class, "auto");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, "gmtModify", Date.class, DateUtil.date());
        this.strictUpdateFill(metaObject, "modifyUser", String.class, "auto");
    }
}
