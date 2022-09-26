create table `user`
(
    id             bigint                                        not null comment '主键id' primary key,
    username       varchar(100)                                  not null comment '登录账号',
    nickname       varchar(100)                                  not null comment '昵称',
    `password`     varchar(255)                                  not null comment '密码',
    salt           varchar(45)                                   null comment '密码加盐',
    avatar         varchar(255)                                  null comment '头像',
    birthday       datetime                                      null comment '生日',
    sex            tinyint(1) unsigned default 0                 not null comment '性别1男，2女',
    phone          varchar(100)                                  null,
    email          varchar(100)                                  null,
    remember_token varchar(255)                                  null,
    `type`         varchar(50)         default 'NORMAL'          not null comment '用户类型',
    `status`       tinyint unsigned    default 1                 null comment '用户状态,1正常',
    created_at     datetime            default CURRENT_TIMESTAMP not null comment '创建日期',
    updated_at     datetime            default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改日期',
    deleted_at     datetime                                      null comment '删除日期',
    create_by      varchar(32)                                   null comment '创建人',
    update_by      varchar(32)                                   null comment '更新人',
    tenant_id      int(8)                                        not null default 000000 comment '租户id',
    constraint uni_username unique (`username`)
) comment '用户表' charset = utf8;