package com.guan.learning.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 使用多个插件时，需要注意它们的顺序。建议的顺序是：
 * 多租户、动态表名
 * 分页、乐观锁
 * SQL 性能规范、防止全表更新与删除
 * 总结：对 SQL 进行单次改造的插件应优先放入，不对 SQL 进行改造的插件最后放入。
 */
@Slf4j
@Configuration
@ConditionalOnBean(DataSource.class)
public class MybatisPlusConfig {

    @PostConstruct
    public void init(){
        log.info("MybatisPlusConfig inited");
    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
