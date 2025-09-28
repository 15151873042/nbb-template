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
        '127.0.0.1', '2025-09-24 10:52:46', '2025-09-24 10:52:56', null, 0, 1, '2025-09-24 10:53:13', 1,
        '2025-09-24 10:53:16');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu
(
    id          bigint      not null comment '菜单ID'
        primary key,
    menu_name   varchar(50) not null comment '菜单名称',
    parent_id   bigint       default 0 null comment '父菜单ID',
    order_num   int          default 0 null comment '显示顺序',
    path        varchar(200) default '' null comment '路由地址',
    component   varchar(255) null comment '组件路径',
    query       varchar(255) null comment '路由参数',
    route_name  varchar(50)  default '' null comment '路由名称',
    is_frame    int          default 1 null comment '是否为外链（0是 1否）',
    is_cache    int          default 0 null comment '是否缓存（0缓存 1不缓存）',
    menu_type   char         default '' null comment '菜单类型（M目录 C菜单 F按钮）',
    visible     char         default '0' null comment '菜单状态（0显示 1隐藏）',
    status      char         default '0' null comment '菜单状态（0正常 1停用）',
    perms       varchar(100) null comment '权限标识',
    icon        varchar(100) default '#' null comment '菜单图标',
    remark      varchar(500) default '' null comment '备注',
    create_id   bigint null comment '创建者',
    create_time datetime null comment '创建时间',
    update_id   bigint null comment '更新者',
    update_time datetime null comment '更新时间'
) comment '菜单权限表';


INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1, '系统管理', 0, 1, 'system', null, '', '', 1, 0, 'M', '0', '0', '', 'system', '系统管理目录', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (2, '系统监控', 0, 2, 'monitor', null, '', '', 1, 0, 'M', '0', '0', '', 'monitor', '系统监控目录', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (3, '系统工具', 0, 3, 'tool', null, '', '', 1, 0, 'M', '0', '0', '', 'tool', '系统工具目录', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (4, '若依官网', 0, 4, 'http://ruoyi.vip', null, '', '', 0, 0, 'M', '0', '0', '', 'guide', '若依官网地址', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', '用户管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', '角色管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', '菜单管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', '部门管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', '岗位管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', '字典管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', '参数设置菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', '通知公告菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', '日志管理菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', '在线用户菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', '定时任务菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', '数据监控菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', '服务监控菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', '缓存监控菜单', 1, '2025-09-26 09:50:08', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', '缓存列表菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', '表单构建菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', '代码生成菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', '系统接口菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', '操作日志菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', '登录日志菜单', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1025, '字典查询', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1026, '字典新增', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1027, '字典修改', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1028, '字典删除', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1029, '字典导出', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1030, '参数查询', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1031, '参数新增', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1032, '参数修改', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1033, '参数删除', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1034, '参数导出', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1035, '公告查询', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1036, '公告新增', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1037, '公告修改', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1038, '公告删除', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1039, '操作查询', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1040, '操作删除', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1041, '日志导出', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1042, '登录查询', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1043, '登录删除', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1044, '日志导出', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1045, '账户解锁', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1046, '在线查询', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', '', 1, '2025-09-26 09:50:09', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1047, '批量强退', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1048, '单条强退', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1049, '任务查询', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1050, '任务新增', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1051, '任务修改', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1052, '任务删除', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1053, '状态修改', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1054, '任务导出', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1055, '生成查询', 116, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1056, '生成修改', 116, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1057, '生成删除', 116, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1058, '导入代码', 116, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1059, '预览代码', 116, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', '', 1, '2025-09-26 09:50:10', 1, null);
INSERT INTO sys_menu (id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, remark, create_id, create_time, update_id, update_time) VALUES (1060, '生成代码', 116, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', '', 1, '2025-09-26 09:50:10', 1, null);

