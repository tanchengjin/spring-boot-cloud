drop table if exists `user`;
create table users
(
    id             bigint unsigned auto_increment primary key,
    `name`         varchar(100) not null,
    `password`     varchar(100) not null,
    phone          varchar(100) null,
    email          varchar(100) null,
    remember_token varchar(255) null,
    `role`         varchar(50)           default 'NORMAL' not null comment '用户角色',
    `status`       tinyint unsigned      default 1 null comment '用户状态',
    `created_at`   datetime     not null default CURRENT_TIMESTAMP comment '创建时间',
    `updated_at`   datetime     not null default CURRENT_TIMESTAMP comment '修改时间',
    `deleted_at`   datetime     null     default null comment '删除时间',
    unique (`name`, phone, email)
) charset = utf8
  ENGINE = InnoDB
  AUTO_INCREMENT = 13 COMMENT = '品牌表'
  ROW_FORMAT = Dynamic;