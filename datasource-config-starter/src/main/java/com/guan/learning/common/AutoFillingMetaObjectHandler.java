package com.guan.learning.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Date;

@Slf4j
@Component
@ConditionalOnBean(DataSource.class)
public class AutoFillingMetaObjectHandler implements MetaObjectHandler {
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
