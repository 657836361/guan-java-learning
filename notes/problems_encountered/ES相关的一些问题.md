## ES 相关的一些问题

### `term` 查询与 `match` 查询的区别

| **特性**   | **`term` 查询**                   | **`match` 查询**                      |
|:---------|:--------------------------------|:------------------------------------|
| **分词处理** | 不对输入值分词，直接精确匹配倒排索引。             | 对输入值分词，转为多个词项的组合查询。                 |
| **适用字段** | `keyword`、`long`、`date` 等未分词字段。 | `text` 类型（分词字段）。                    |
| **性能**   | 更高（直接查找词项）。                     | 较低（需解析分词结果并计算相关性）。                  |
| **典型场景** | 精确匹配（如 `status: "active"`）。     | 全文搜索（如 `message: "error timeout"`）。 |

### es 的一些需要记住的知识点

- 不管是 `keyword` 还是 `text` 类型的 `field`，都是以倒排索引的方式保存的
- `正排索引（Doc Values）` 是 `field` 的一个属性，设置后会创建正排索引，支持该字段的排序，聚合等操作
- `正排索引（Doc Values）` 列式存储（压缩编码 + 批量读取），比倒排索引遍历更高效，适合海量数据分析。
- es 根据 `field` 不同的属性创建不同的数据结构来满足不同的场景
- 所有 `keyword` 和分词的 `text` 字段默认通过倒排索引支持 `term` 精确查询。
- 无法直接修改已有索引的分词器，可以通过以下方法操作
  1. 创建新索引并指定新分词器
  ```json
  PUT /new_index
  {
      "settings": {
          "analysis": {
              "analyzer": {
                  "my_ik_analyzer": { // 自定义分词器名称
                      "type": "custom",
                      "tokenizer": "ik_max_word" // 使用 IK 分词器
                  }
              }
          }
      },
      "mappings": {
          "properties": {
              "content": {
                  "type": "text",
                  "analyzer": "my_ik_analyzer", // 指定新分词器
                  "search_analyzer": "ik_smart" // 可选：指定搜索分词器
              }
          }
      }
  }
  ```
  2. 迁移数据到新索引, 使用 `_reindex` 命令
  ```json
  POST _reindex
  {
    "source": { "index": "old_index" },
    "dest": { "index": "new_index" }
  }
  ```

## ES 存储文档怎么样才能节省空间

### 模糊查询的字段

- **适用场景**：通配符（`*abc`）、部分匹配（`app` → `apple`）、拼写容错（`bown~` → `brown`）。

- 推荐类型：

    - **高频通配符**：`wildcard` 类型（`"type": "wildcard"`）。
    - **部分前缀匹配**：`text` 类型 + `ngram` 分词器。
    - **简单模糊**：`keyword` 类型 + `fuzzy` 查询。

- **配置示例**：

  ```json
  "field": {
    "type": "wildcard"  // 或 "type": "text", "analyzer": "ngram_analyzer"
  }
  ```

### 类似数据库 IN 查询的字段

- **对应 ES 操作**：`terms` 查询（多值精确匹配）。

- **推荐类型**：`keyword`。

- **优化配置**：

  ```json
  "field": {
    "type": "keyword",
    "doc_values": false  // 若无排序/聚合需求
  }
  ```

### 精准查询的字段

- **适用场景**：完全匹配（如 `status = "success"`）。

- **推荐类型**：`keyword`。

- **配置示例**：

  ```json
  "field": {
    "type": "keyword",
    "doc_values": false,
    "norms": false
  }
  ```

### 需要修改的字段

- 优化建议：

    - **选择低更新开销类型**：`keyword`、`long`、`boolean`。
    - **禁用非必要索引**：`doc_values: false`（若无需排序/聚合）。

- **避免类型**：`text`（分词索引重建开销高）、`nested`（父子文档更新复杂）。

- **示例**：

  ```json
  "counter": {
    "type": "long",
    "doc_values": false
  }
  ```

### 需要排序的字段

- **必选配置**：启用 `doc_values`。

- **推荐类型**：`date`（时间排序）、`long`/`integer`（数值排序）。

- **示例**

  ```json
  "createDate": {
    "type": "date"  // 默认启用 doc_values
  }
  ```

### 范围查询的字段

- **必选配置**：启用 `doc_values`。

- **推荐类型**：`date`、`long`、`integer`、`ip`（IP 范围）。

- **示例**：

  ```json
  "price": {
    "type": "long"
  }
  ```

## ES 集群的一些问题

### ES 集群性能优化的方向

#### 1.让 ES 不使用操作系统的交换分区（Swap）
  **什么是交换分区（Swap）？**

  - 交换分区是磁盘上的一块空间，当物理内存（RAM）不足时，操作系统会将部分内存数据暂时转移到交换分区中，以腾出物理内存供其他进程使用。
  - **问题**：磁盘的读写速度远低于内存（机械硬盘约慢 10 万倍，SSD 约慢 1000 倍），频繁使用交换分区会导致 ES 性能急剧下降。

  **为什么要禁用交换分区？**

  - **避免 GC 冻结**：ES 基于 Java，依赖垃圾回收（GC）管理内存。若内存不足触发交换分区，GC 可能因磁盘延迟而长时间暂停（Stop-The-World），导致节点响应超时，甚至被集群误判为宕机。
  - **防止节点失联**：ES 节点间依赖心跳通信，若节点因交换分区导致通信延迟，可能触发主节点重新分配分片，引发不必要的分片迁移和负载波动。

  **怎么禁用？**

  在 elasticsearch.yml 中设置：
  ```yaml 
  bootstrap.memory_lock: true
  ```
  此配置会强制 ES 进程锁定内存，禁止操作系统将其交换到磁盘。

#### 2.独立磁盘（避免 IO 竞争）

