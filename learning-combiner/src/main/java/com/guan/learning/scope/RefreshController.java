package com.guan.learning.scope;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;

@RequestMapping("/scope")
@RestController
public class RefreshController {

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private ConfigurableEnvironment environment;


    @GetMapping("/refresh/datasource")
    public String refresh() {
        MutablePropertySources propertySources = environment.getPropertySources();
        HashMap<String, Object> map = new HashMap<>();
        map.put("spring.config.datasource.jdbcUrl", "${DB_SLAVE_URL}");
        propertySources.addFirst(new MapPropertySource("news", map));
        // 指定要刷新的Bean名称
        String beanName = "dataSource"; // 替换为你的Bean名称
        // 仅刷新指定的Bean
        boolean success = refreshScope.refresh(beanName);

        return "Bean '" + beanName + "' refreshed result" + success;
    }

    @GetMapping("/check")
    public String check() throws SQLException {
        return sqlSession.getConnection().getMetaData().getURL();
    }
}
