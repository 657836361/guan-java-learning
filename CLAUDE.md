# guan-java-learning 项目文档

## 项目概述

这是一个多模块 Java 学习项目，基于 Spring Boot 3.3.5 + Java 17 构建，整合了常见企业级开发中的核心功能模块。

### 技术栈

| 技术           | 版本         | 说明                  |
|--------------|------------|---------------------|
| Spring Boot  | 3.3.5      | 基础框架                |
| Java         | 17         | 编译目标                |
| MyBatis-Plus | 3.5.7      | ORM 框架              |
| MySQL        | 8.3.0      | 数据库                 |
| Redisson     | 3.47.0     | Redis 客户端（分布式锁）     |
| Curator      | 5.8.0      | ZooKeeper 客户端（分布式锁） |
| Guava        | 32.0.1-jre | 缓存工具                |
| Lombok       | 1.18.34    | 代码简化                |
| Fastexcel    | 1.0.0      | Excel 处理            |

---

## 模块架构

```
root (父POM)
├── common                          # 基础公共功能
│   ├── cache/                      # 缓存策略模式实现
│   ├── enums/                      # 枚举处理
│   ├── exceptions/                 # 异常处理
│   └── filter/                     # 过滤器
│
├── datasource-config-starter       # 数据源配置模块（依赖common）
│   ├── datasource/                 # 动态数据源
│   ├── dict/                       # 字典缓存
│   ├── enums/                      # BaseEnumTypeHandler
│   └── mybatis/                    # MyBatis配置
│
├── learning-combiner                # 主应用模块（依赖datasource-config-starter）
│   ├── cache/                      # 字典数据缓存
│   ├── config/                     # 配置类
│   ├── controller/                 # 控制器
│   └── LearningCombinerApplication  # 启动类
│
├── distribute-lock                 # 分布式锁模块（独立）
├── spring-enviroment               # Spring环境模块（独立）
└── notes/                          # 学习子模块集合
    ├── proxy_learn/
    ├── rabbitmq_learn/
    └── redis_learn/
```

---

## 构建命令

```bash
# 编译整个项目
mvn clean compile

# 打包整个项目
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests

# 指定环境打包
mvn clean package -P dev
mvn clean package -P prd

# 安装到本地仓库
mvn clean install

# 只构建特定模块
mvn clean package -pl learning-combiner -am
```

---

## 运行命令

### learning-combiner（主应用）

```bash
# 开发环境
cd learning-combiner
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或使用 java -jar
java -jar learning-combiner/target/learning-combiner-1.0.0.jar --spring.profiles.active=dev

# 默认端口: 8080
# Context Path: /learning
```

### distribute-lock（分布式锁模块）

```bash
cd distribute-lock
mvn spring-boot:run

# 运行测试
mvn test
```

### spring-enviroment（Spring环境模块）

```bash
cd spring-enviroment
mvn spring-boot:run
```

### notes 子模块

```bash
# 运行特定学习模块
cd notes/redis_learn
mvn spring-boot:run
```

---

## 核心架构设计

### 1. 缓存架构（策略模式）

**位置**: `common/src/main/java/com/guan/common/cache/`

- **CacheUtil**: 统一缓存工具类，使用 Guava Cache
- **IStore**: 存储策略接口
- **实现类**:
    - `EnumStore`: 枚举数据存储
    - `DictDataStore`: 字典数据存储

**配置示例**:

```java
// 添加缓存任务
CacheUtil.addTaskMap(Map.of(
    "ENUM_STORE", new EnumStore(),
    "DICT_STORE", new DictDataStore()
));

// 加载缓存
CacheUtil.load();

// 获取缓存数据
Map<String, Object> data = CacheUtil.get("ENUM_STORE");
```

### 2. 枚举处理

**位置**: `datasource-config-starter/src/main/java/com/guan/datasource/enums/`

- **BaseEnum**: 所有枚举的基类接口
- **BaseEnumTypeHandler**: MyBatis 类型处理器，自动实现枚举与数据库字符串的转换

**使用方式**:

```java
public enum MyEnum implements BaseEnum {
    VALUE1("v1", "值1"),
    VALUE2("v2", "值2");

    private final String code;
    private final String text;

    // ... getter 和 fromCode 方法
}
```

MyBatis 会自动使用 `BaseEnumTypeHandler` 进行枚举转换。

### 3. 动态数据源

**位置**: `datasource-config-starter/src/main/java/com/guan/datasource/dynamic/`

**注解**: `@DataSourceFlag` - 标记在方法上，切换数据源

**枚举**:

- `DataSourceFlagEnum.MASTER`: 主库（写操作）
- `DataSourceFlagEnum.SLAVE`: 从库（读操作）

**使用示例**:

```java
@DataSourceFlag(DataSourceFlagEnum.SLAVE)
public List<User> queryUsers() {
    return userMapper.selectList(null);
}

@DataSourceFlag(DataSourceFlagEnum.MASTER)
public void saveUser(User user) {
    userMapper.insert(user);
}
```

**配置**: 在 `application.yml` 中配置主从数据源连接信息。

### 4. 分布式锁模块

**位置**: `distribute-lock/src/main/java/com/guan/distribute/`

**三种实现**:

1. **RedisDistributedLock**: 基于 Redis 实现
2. **RedissonDistributedLock**: 基于 Redisson 实现
3. **ZookeeperDistributedLock**: 基于 ZooKeeper 实现

**配置自动加载**:

- 引入 `spring-boot-starter-data-redis` → 自动配置 Redis 锁
- 引入 `redisson-spring-boot-starter` → 自动配置 Redisson 锁
- 引入 `curator-recipes` → 自动配置 ZooKeeper 锁

**使用示例**:

```java
@Autowired
private DistributedLockTemplate lockTemplate;

// 无返回值
lockTemplate.execute("my-lock-key", 30, TimeUnit.SECONDS, () -> {
    // 需要加锁执行的代码
});

// 有返回值
String result = lockTemplate.execute("my-lock-key", 30, TimeUnit.SECONDS, () -> {
    return "success";
});

// 尝试执行（获取锁失败不抛异常）
boolean success = lockTemplate.tryExecute("my-lock-key", 30, TimeUnit.SECONDS, () -> {
    // 任务代码
});
```

### 5. 字典缓存

**位置**: `datasource-config-starter/src/main/java/com/guan/datasource/dict/`

- **DictCacheUtil**: 字典缓存工具类
- **DictDataMappingConfig**: 字典映射配置
- **DictDataStore**: 字典数据存储实现

### 6. 全局异常处理

**位置**: `common/src/main/java/com/guan/common/exceptions/`

- **BusinessException**: 业务异常基类
- 使用 `@ControllerAdvice` 统一处理异常

---

## 配置文件说明

### 环境配置

- `application.yml`: 主配置
- `application-dev.yml`: 开发环境
- `application-prd.yml`: 生产环境

### 关键配置项

| 配置项                               | 说明              |
|-----------------------------------|-----------------|
| `server.port`                     | 服务端口（默认 8080）   |
| `server.servlet.context-path`     | 应用上下文路径         |
| `spring.profiles.active`          | 激活的环境（dev/prd）  |
| `spring.datasource.*`             | 数据源配置           |
| `spring.data.redis.cluster.nodes` | Redis 集群节点      |
| `mybatis-plus.*`                  | MyBatis-Plus 配置 |

### Docker 构建

learning-combiner 模块支持 Docker 构建，使用 `dockerfile-maven-plugin`。

---

## 启动类

### learning-combiner

**位置**: `learning-combiner/src/main/java/com/guan/learning/LearningCombinerApplication.java`

**关键注解**:

- `@EnableDataSource`: 启用动态数据源
- `@EnableAsync`: 启用异步支持
- `@ConfigurationPropertiesScan`: 扫描配置属性
- `@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)`: 排除默认数据源自动配置

---

## 代码规范

1. **枚举类必须实现** `BaseEnum` 接口
2. **需要切换数据源的方法** 使用 `@DataSourceFlag` 注解
3. **分布式锁** 优先使用 `DistributedLockTemplate`
4. **缓存数据** 实现 `IStore` 接口并注册到 `CacheUtil`

---

## 环境变量

| 变量名                   | 说明               |
|-----------------------|------------------|
| `REDIS_PASSWORD`      | Redis 密码         |
| `REDIS_NODES`         | Redis 集群节点（逗号分隔） |
| `DATASOURCE_URL`      | 默认数据源 URL        |
| `DATASOURCE_USER`     | 默认数据源用户名         |
| `DATASOURCE_PASSWORD` | 默认数据源密码          |
| `DB_MASTER_URL`       | 主库 URL           |
| `DB_MASTER_USER`      | 主库用户名            |
| `DB_MASTER_PASSWORD`  | 主库密码             |
| `DB_SLAVE_URL`        | 从库 URL           |
| `DB_SLAVE_USER`       | 从库用户名            |
| `DB_SLAVE_PASSWORD`   | 从库密码             |
