#!/bin/bash

# 定义节点数量和容器名前缀
NODE_COUNT=6
CONTAINER_PREFIX="redis-node"

# 清空旧目录（谨慎操作）
# rm -rf ${CONTAINER_PREFIX}*

# 创建节点目录及配置文件
for i in $(seq 1 ${NODE_COUNT}); do
    NODE_DIR="${CONTAINER_PREFIX}${i}"
    mkdir -p "${NODE_DIR}/data" "${NODE_DIR}/conf"

    # 生成 Redis 配置文件（兼容容器名通信）
    cat > "${NODE_DIR}/conf/redis.conf" <<EOF
bind 0.0.0.0
port 6379
requirepass gp8525456
masterauth gp8525456

cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.29.31.155
cluster-announce-port 700${i}
cluster-announce-bus-port 1700${i}

dir /data
daemonize no
protected-mode no
databases 1
maxmemory 256mb

save 900 1
save 300 10
save 60 10000
appendonly yes
appendfsync everysec

EOF

    echo "生成目录: ${NODE_DIR}"
done

echo "所有节点配置已生成！"
