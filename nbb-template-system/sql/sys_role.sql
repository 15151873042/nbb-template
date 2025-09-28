create table sys_role
(
    id                  bigint                 not null comment '角色ID'
        primary key,
    role_name           varchar(30)            not null comment '角色名称',
    role_key            varchar(100)           not null comment '角色权限字符串',
    role_sort           int                    not null comment '显示顺序',
    data_scope          char       default '1' null comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    menu_check_strictly tinyint(1) default 1   null comment '菜单树选择项是否关联显示',
    dept_check_strictly tinyint(1) default 1   null comment '部门树选择项是否关联显示',
    status              char                   not null comment '角色状态（0正常 1停用）',
    del_flag            char       default '0' null comment '删除标志（0代表存在 2代表删除）',
    remark              varchar(500)           null comment '备注',
    create_id           bigint                 null comment '创建者',
    create_time         datetime               null comment '创建时间',
    update_id           bigint                 null comment '更新者',
    update_time         datetime               null comment '更新时间'
)
    comment '角色信息表';

INSERT INTO `nbb-template-system`.sys_role (id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, remark, create_id, create_time, update_id, update_time) VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', '超级管理员', null, '2025-09-26 10:24:57', null, null);
INSERT INTO `nbb-template-system`.sys_role (id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, remark, create_id, create_time, update_id, update_time) VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', '普通角色', null, '2025-09-26 10:24:57', null, null);
