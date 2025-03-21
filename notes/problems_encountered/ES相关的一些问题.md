## ES相关的一些问题

### `term` 查询与 `match` 查询的区别

| **特性**   | **`term` 查询**                   | **`match` 查询**                      |
|:---------|:--------------------------------|:------------------------------------|
| **分词处理** | 不对输入值分词，直接精确匹配倒排索引。             | 对输入值分词，转为多个词项的组合查询。                 |
| **适用字段** | `keyword`、`long`、`date` 等未分词字段。 | `text` 类型（分词字段）。                    |
| **性能**   | 更高（直接查找词项）。                     | 较低（需解析分词结果并计算相关性）。                  |
| **典型场景** | 精确匹配（如 `status: "active"`）。     | 全文搜索（如 `message: "error timeout"`）。 |

### es的一些需要记住的知识点

- 不管是`keyword`还是`text`类型的`field`，都是以倒排索引的方式保存的
- `正排索引（Doc Values）`是`field`的一个属性，设置后会创建正排索引，支持该字段的排序，聚合等操作
- `正排索引（Doc Values）`列式存储（压缩编码 + 批量读取），比倒排索引遍历更高效，适合海量数据分析。
- es根据`field`不同的属性创建不同的数据结构来满足不同的场景
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
  2. 迁移数据到新索引,使用 `_reindex` 命令
  ```json
  POST _reindex
  {
    "source": { "index": "old_index" },
    "dest": { "index": "new_index" }
  }
  ```

## ES存储文档怎么样才能节省空间

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

- **推荐类型**：`date`、`long`、`integer`、`ip`（IP范围）。

- **示例**：

  ```json
  "price": {
    "type": "long"
  }
  ```

