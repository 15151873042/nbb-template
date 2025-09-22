-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
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

) comment '参数配置表' row_format = DYNAMIC;

