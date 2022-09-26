create table if not exists common
(
    id           bigint unsigned not null comment '主键id' primary key,
    `status`     tinyint unsigned         default 1 null comment '用户状态1正常',
    `created_at` datetime        not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at` datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `deleted_at` datetime        null comment '删除日期',
    `created_by` bigint          not null comment '创建人',
    `updated_by` bigint          not null comment '更新人',
    `tenant_id`  int(6) zerofill not null default 000000 comment '租户id'
);
create table if not exists `user`
(
    id             bigint unsigned not null comment '主键id' primary key,
    username       varchar(100)    not null comment '登录账号',
    nickname       varchar(100)    not null comment '昵称',
    `password`     varchar(255)    not null comment '密码',
    salt           varchar(45)     not null default '' comment '密码加盐',
    avatar         varchar(255)    not null default '' comment '头像',
    birthday       datetime        null comment '生日',
    sex            tinyint(1) unsigned      default 0 not null comment '性别1男，2女',
    phone          varchar(100)    not null default '',
    email          varchar(100)    not null default '',
    remember_token varchar(255)    null,
    `type`         tinyint(3) unsigned      default 1 not null comment '用户类型1普通用户',
    `status`       tinyint unsigned         default 1 null comment '用户状态,1正常',
    `created_at`   datetime        not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at`   datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `deleted_at`   datetime        null comment '删除日期',
    `tenant_id`    int(6) zerofill not null default 000000 comment '租户id',
    constraint uni_username unique (`username`),
    index idx_username (`nickname`),
    index idx_phone (`phone`),
    index idx_email (`email`),
    index idx_tenant_id (`tenant_id`),
    index idx_d (`nickname`, `username`, `phone`, `email`, `tenant_id`)
) comment '用户表' charset = utf8
                   auto_increment = 100;

create table if not exists `oauth`
(
    `id`         bigint unsigned primary key auto_increment,
    `user_id`    bigint unsigned not null comment '用户id',
    `oauth_type` varchar(100)    not null comment 'oauth类型',
    `token`      varchar(255)    not null comment 'token',
    `created_at` datetime        not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at` datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `tenant_id`  int(6) zerofill not null default 000000 comment '租户id',
    constraint uni_user_id unique (`user_id`, `token`),
    index idx_token (`token`),
    index idx_oauth_type (`oauth_type`),
    index idx_user_id (`user_id`),
    index idx_tenant_id (`tenant_id`)
) comment '第三方认证表' charset = utf8
                         auto_increment = 100;