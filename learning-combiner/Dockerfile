FROM eclipse-temurin:17-jre-jammy
# ubuntu/jre:17-22.04_edge 这个镜像虽然不错但是没有安装bash 不能执行 RUN

# 设置工作目录
WORKDIR /app

# 创建日志目录（关键步骤）
RUN mkdir -p /app/logs
#RUN ["/usr/bin/mkdir", "-p", "/app/logs"]

# 将 Maven 打包的 JAR 文件复制到镜像中
COPY target/*.jar app.jar

# 暴露端口（根据实际端口修改）
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]