# common模块
## 介绍

### 注意 spring spi机制 
在SpringBoot2.x版本下在res/META-INF/spring.factories文件下定义
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.xxx.xxx.Xxx
```

在SpringBoot3.x中，默认不再是扫描META-INF/spring.factories了，
而是META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
一行一个全限定类名
```properties
com.example.xxx.Xxx
com.example.Yyyy.yyy
```

