DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    biz_id VARCHAR(64) NULL DEFAULT NULL COMMENT '业务主键',
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



INSERT INTO `user` (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');