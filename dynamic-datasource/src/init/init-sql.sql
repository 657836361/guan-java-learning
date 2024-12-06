DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    biz_id VARCHAR(64) NULL DEFAULT NULL COMMENT '业务主键',
    role VARCHAR(30) NULL DEFAULT NULL COMMENT '角色',
    gender VARCHAR(30) NULL DEFAULT NULL COMMENT '性别',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    deleted datetime NULL DEFAULT NULL COMMENT '是否删除',
    gmt_create datetime NULL DEFAULT NULL COMMENT '创建时间',
    gmt_modify datetime NULL DEFAULT NULL COMMENT '修改时间',
    create_user VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人',
    modify_user VARCHAR(50) NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    dict_data_code VARCHAR(30) NULL DEFAULT NULL COMMENT '字典数据code',
    dict_data_name VARCHAR(30) NULL DEFAULT NULL COMMENT '字典数据名称',
    dict_type_code VARCHAR(30) NULL DEFAULT NULL COMMENT '字典类型code',
    dict_type_name VARCHAR(30) NULL DEFAULT NULL COMMENT '字典类型名称',
    gmt_create datetime NULL DEFAULT NULL COMMENT '创建时间',
    gmt_modify datetime NULL DEFAULT NULL COMMENT '修改时间',
    create_user VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人',
    modify_user VARCHAR(50) NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (id),
    index idx_type_code(dict_type_code,dict_data_code,dict_data_name,dict_type_name)
);


INSERT INTO `sys_dict_data` VALUES (1, 'male', '男', 'person_gender', '人类性别', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (2, 'female', '女', 'person_gender', '人类性别', NULL, NULL, NULL, NULL);


INSERT INTO `user` (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');