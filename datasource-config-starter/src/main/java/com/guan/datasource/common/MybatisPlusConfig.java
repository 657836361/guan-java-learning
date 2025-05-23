package com.guan.datasource.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * 使用多个插件时，需要注意它们的顺序。建议的顺序是：
 * 多租户、动态表名
 * 分页、乐观锁
 * SQL 性能规范、防止全表更新与删除
 * 总结：对 SQL 进行单次改造的插件应优先放入，不对 SQL 进行改造的插件最后放入。
 */
@Slf4j
public class MybatisPlusConfig {

    @PostConstruct
    public void init() {
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
        // 攻击 SQL 阻断解析器,防止全表更新与删
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

}
