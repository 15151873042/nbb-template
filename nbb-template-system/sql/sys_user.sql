create table sys_user
(
    id              bigint                    not null comment '用户ID'
        primary key,
    dept_id         bigint                    null comment '部门ID',
    user_name       varchar(30)               not null comment '用户账号',
    nick_name       varchar(30)               not null comment '用户昵称',
    user_type       varchar(2)   default '00' null comment '用户类型（00系统用户）',
    email           varchar(50)  default ''   null comment '用户邮箱',
    phonenumber     varchar(11)  default ''   null comment '手机号码',
    sex             char         default '0'  null comment '用户性别（0男 1女 2未知）',
    avatar          varchar(100) default ''   null comment '头像地址',
    password        varchar(100) default ''   null comment '密码',
    status          char         default '0'  null comment '账号状态（0正常 1停用）',
    login_ip        varchar(128) default ''   null comment '最后登录IP',
    login_date      datetime                  null comment '最后登录时间',
    pwd_update_date datetime                  null comment '密码最后更新时间',
    remark          varchar(500)              null comment '备注',
    deleted         bigint       default 0    null comment '删除标志（0代表未删除 非0代表删除）',
    create_id       bigint                    null comment '创建者id',
    create_time     datetime                  null comment '创建时间',
    update_id       bigint                    null comment '更新者id',
    update_time     datetime                  null comment '更新时间'
)
    comment '用户信息表';

INSERT INTO `nbb-template-system`.sys_user (id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, login_ip, login_date, pwd_update_date, remark, deleted, create_id, create_time, update_id, update_time) VALUES (1, 103, 'admin', '管理员', '00', '15151873042@163.com', '15151873042', '0', '', '$2a$10$wyEonQinqTK.lPqDnSmlJ.qJsxr97TEXVuc7YSCDrXyogDSbLk8bC', '0', '127.0.0.1', '2025-09-24 10:52:46', '2025-09-24 10:52:56', null, 0, 1, '2025-09-24 10:53:13', 1, '2025-09-24 10:53:16');
INSERT INTO `nbb-template-system`.sys_user (id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, login_ip, login_date, pwd_update_date, remark, deleted, create_id, create_time, update_id, update_time) VALUES (2, 103, 'hupeng', '胡鹏', '00', '15151873042@163.com', '15151873042', '0', '', '$2a$10$g7a6/uz6e//9oIKl7Jbw/e.CvXmHjyHTifrTXHI0jh24QsjFcZ8RG', '0', '127.0.0.1', '2025-09-28 09:40:08', '2025-09-28 09:40:12', null, 0, 1, '2025-09-28 09:40:20', 1, '2025-09-28 09:40:26');
