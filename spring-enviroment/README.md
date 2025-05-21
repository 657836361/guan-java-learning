# 先创建一个spring-boot的环境

## spring aop中被代理方法由final修饰是否会造成代理失效探讨

### 前提

spring aop默认由以下类进行自动配置
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
没有该类的话需要代码中手动使用@EnableAspectJAutoProxy

### 当默认代理方式为JDK动态代理

#### 场景1 当

### 当默认代理方式为CGlib动态代理

1. 提供一个接口 有实现类实现该接口的方法A并在方法上增加final 且该方法没有使用到注入的其他属性

```java
public interface IServiceA {
    void doServiceA(int i);
}
```

```java

@Component
public class ServiceAFinalMethod implements IServiceA {

    @Mark
    @Override
    public final void doServiceA(int i) {
        System.out.println("执行代码");
    }
}
```

执行方法不会报错

2. 提供一个接口 有实现类实现该接口的方法A并在方法上增加final 且该方法使用到注入的其他属性

```java
public interface IServiceA {
    void doServiceA(int i);
}
```

```java

@Component
public class ServiceAFinalMethod implements IServiceA {
    @Autowired
    private TransactionMapper mapper;

    @Mark
    @Override
    public final void doServiceA(int i) {
        mapper.doMapper(i);
    }
}
```

执行方法会报错空指针 该属性为空

本质上还是因为加了final 导致cglib代理该方法失败了

3. 提供一个接口 有实现类实现该接口的方法A并在方法上增加final 且该方法使用到注入的其他属性