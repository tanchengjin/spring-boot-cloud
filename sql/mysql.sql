create table if not exists common
(
    id           bigint unsigned not null comment '主键id' primary key,
    `status`     tinyint unsigned         default 1 null comment '状态1正常',
    `created_at` datetime        not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at` datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `deleted_at` datetime        null comment '删除日期',
    `created_by` bigint          not null comment '创建人',
    `updated_by` bigint          not null comment '更新人',
    `tenant_id`  int(6) unsigned not null default 999999 comment '租户id'
) comment '公用字段';

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
    `tenant_id`    int(6)          not null default 888888 comment '租户id',
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
    `tenant_id`  int(6)          not null default 888888 comment '租户id',
    constraint uni_user_id unique (`user_id`, `token`),
    index idx_token (`token`),
    index idx_oauth_type (`oauth_type`),
    index idx_user_id (`user_id`),
    index idx_tenant_id (`tenant_id`)
) comment '第三方认证表' charset = utf8
                         auto_increment = 100;


create table if not exists `article_category`
(
    `id`         int unsigned primary key auto_increment comment '主键',
    `name`       varchar(100)    not null,
    `parent_id`  int unsigned    null     default null comment '上级id',
    `path`       varchar(255)    not null default '' comment '祖先id',
    `sort`       int unsigned    not null default 1 comment '排序',
    `status`     tinyint unsigned         default 1 null comment '状态1正常',
    `created_at` datetime        not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at` datetime        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `deleted_at` datetime        null comment '删除日期',
    `created_by` bigint          not null comment '创建人',
    `updated_by` bigint          not null comment '更新人',
    `tenant_id`  int(6) unsigned not null default 888888 comment '租户id'
) engine = InnoDB
  charset = UTF8 comment '文章分类表'
  auto_increment = 100;

create table if not exists article
(
    id            bigint unsigned auto_increment primary key comment '主键',
    category_id   int unsigned                   not null comment '分类id',
    title         varchar(255)                   not null comment '文章标题',
    `cover`       varchar(500)        default '' not null comment '文章图片',
    sub_title     varchar(100)                   null comment '文章副标题',
    `description` varchar(255)        default '' not null comment '文章描述',
    keywords      varchar(255)        default '' not null comment '文章关键词,以逗号分割可用于实现标签功能',
    author        varchar(30)                    not null comment '文章作者',
    content       text                           not null comment '文章内容',
    `comment`     tinyint unsigned    default 0  not null comment '是否开启评论默认为否',
    `status`      tinyint unsigned    default 1  not null comment '文章状态1正常',
    click         bigint unsigned     default 0  not null comment '点击量',
    virtual_click int unsigned        default 0  not null comment '虚拟访问量',
    sort          int unsigned        default 1  not null comment '排序',
    commend       tinyint(1) unsigned default 0  not null comment '推荐',
    top           tinyint(1) unsigned default 0  not null comment '是否置顶',
    like_count    int unsigned        default 0  not null comment '点赞数量',
    comment_count int unsigned        default 0  not null comment '评论数量',
    `source`      varchar(100)        default '' not null comment '文章来源',
    source_url    varchar(255)        default '' not null comment '文章来源url',
    redirect_url  varchar(255)        default '' not null comment '跳转地址',
    extra         text                           null comment '额外字段',
    `created_at`  datetime                       not null default CURRENT_TIMESTAMP comment '创建日期',
    `updated_at`  datetime                       not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改日期',
    `deleted_at`  datetime                       null comment '删除日期',
    `created_by`  bigint                         not null comment '创建人',
    `updated_by`  bigint                         not null comment '更新人',
    `tenant_id`   int(6) unsigned                not null default 888888 comment '租户id'
) auto_increment = 100 comment '文章表'
  charset = utf8
  engine = InnoDB;

create table if not exists `banner`
(
    `id`         int unsigned primary key auto_increment,
    `title`      varchar(100)    null     default '' comment '标题',
    `platform`   varchar(100)    not null default 'WEB' comment '平台',
    `type`       tinyint(2)      not null default 1 comment 'banner类型，1：图片，2：视频',
    `url`        varchar(255)    not null comment '图片/视频地址',
    `enable`     boolean         not null default 1 comment '是否有效',
    `sort`       int(6) unsigned not null default 0 comment '排序',
    `link`       varchar(255)    null     default null comment '跳转链接',
    `created_at` datetime        not null default CURRENT_TIMESTAMP,
    `updated_at` datetime        not null default CURRENT_TIMESTAMP,
    `deleted_at` datetime        null     default null,
    index idx_platform (`platform`)
) comment 'banner';