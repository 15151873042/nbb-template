DROP TABLE IF EXISTS `sys_config`;
create table sys_config
(
    id           bigint not null comment '参数主键' primary key,
    config_name  varchar(100) comment '参数名称',
    config_key   varchar(100) comment '参数键名',
    config_value varchar(500) comment '参数键值',
    internal     bit(1) default false comment '是否系统内置',
    remark       varchar(500) null comment '备注',
    create_id    bigint comment '创建者id',
    create_time  datetime null comment '创建时间',
    update_id    bigint comment '更新者id',
    update_time  datetime comment '更新时间'

) comment '参数配置表';


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user
(
    id              bigint(20) not null comment '用户ID' primary key,
    dept_id         bigint(20) default null comment '部门ID',
    user_name       varchar(30) not null comment '用户账号',
    nick_name       varchar(30) not null comment '用户昵称',
    user_type       varchar(2)   default '00' comment '用户类型（00系统用户）',
    email           varchar(50)  default '' comment '用户邮箱',
    phonenumber     varchar(11)  default '' comment '手机号码',
    sex             char(1)      default '0' comment '用户性别（0男 1女 2未知）',
    avatar          varchar(100) default '' comment '头像地址',
    password        varchar(100) default '' comment '密码',
    status          char(1)      default '0' comment '账号状态（0正常 1停用）',
    login_ip        varchar(128) default '' comment '最后登录IP',
    login_date      datetime comment '最后登录时间',
    pwd_update_date datetime comment '密码最后更新时间',
    remark          varchar(500) null comment '备注',
    deleted         bigint       default 0 comment '删除标志（0代表未删除 非0代表删除）',
    create_id       bigint comment '创建者id',
    create_time     datetime null comment '创建时间',
    update_id       bigint comment '更新者id',
    update_time     datetime comment '更新时间'
) comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
INSERT INTO sys_user (id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status,
                      login_ip, login_date, pwd_update_date, remark, deleted, create_id, create_time, update_id,
                      update_time)
VALUES (1, 103, 'admin', '管理员', '00', '15151873042@163.com', '15151873042', '0', '',
        '$2a$10$wyEonQinqTK.lPqDnSmlJ.qJsxr97TEXVuc7YSCDrXyogDSbLk8bC', '0',
        '127.0.0.1', '2025-09-24 10:52:46', '2025-09-24 10:52:56', null, 0, 1, '2025-09-24 10:53:13', 1, '2025-09-24 10:53:16');

