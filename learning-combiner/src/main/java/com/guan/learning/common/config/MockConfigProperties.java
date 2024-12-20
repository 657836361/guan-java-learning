package com.guan.learning.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.StringJoiner;

/**
 * 使用说明：
 * 1.在当前类上增加@Component交给spring管理
 * 2.主启动类中添加@EnableConfigurationProperties(MockConfigProperties.class)
 * 3.在配置类中使用@ConfigurationPropertiesScan
 */
//@Configuration
@Getter
@Setter
@ConfigurationProperties("mock")
public class MockConfigProperties {

    private Account account;
    private String privateKey;

    @Getter
    @Setter
    public static class Account {
        private String name;
        private String userId;

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MockConfigProperties.class.getSimpleName() + "[", "]")
                .add("account=" + account)
                .add("privateKey='" + privateKey + "'")
                .toString();
    }
}
