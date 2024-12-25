DROP TABLE IF EXISTS `user`;
CREATE TABLE `base_user`
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

INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `deleted`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613314, '4ed4664dfac5422689adbf72f66bea55', 'admin', 'male', '緀幍锑箢', 61, 'Vtbpln@ML7A.com.cn', '2024-12-12 16:32:45', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613315, 'c0781f74957b4f6fa6486e2c1542c6da', 'admin', 'female', '躤鉛攏饓', 11, 'J329hyD@ojq.com', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email` , `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613316, '54bea6b22a5141b8818fab6bcf2ac701', 'admin', 'female', '薪琅', 68, 'Gw8Oqocmx@PG.com.cn', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613317, '851bbde10187430ca90940ab6d873972', 'admin', 'male', '舝掣戁梬', 90, 'ARVOKX@od.com.cn', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613318, 'd377abbe129e4495891d8da66de68b07', 'admin', 'female', '挟播', 43, 'nFXzVj@8XOL.com.cn', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613319, '7dda25d8126341ddb278e932ed71d106', 'user', 'female', '芯丄哻剶', 64, 'cZW7Vee@jlC.com.cn', now(), now(), 'auto', 'auto');
INSERT INTO `base_user` (`id`, `biz_id`, `role`, `gender`, `name`, `age`, `email`, `gmt_create`, `gmt_modify`, `create_user`, `modify_user`) VALUES (1866769815177613320, '0351cf8eb2894ce59f6e6ef706cdd065', 'user', 'male', '劍襄榲', 69, 'A6jYy1d@hHE.com.cn', now(), now(), 'auto', 'auto');

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
