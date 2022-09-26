DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`
(
    `id`         int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`       varchar(200)     NOT NULL COMMENT '名称',
    `image`      varchar(500)     NULL     DEFAULT NULL COMMENT '图片',
    `letter`     char(1)          NOT NULL COMMENT '品牌首字母',
    `created_at` datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    `updated_at` datetime         not null default CURRENT_TIMESTAMP comment '修改时间',
    `deleted_at` datetime         null     default null comment '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unq_name` (`name`) USING BTREE,
    INDEX `idx_letter` (`letter`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13 COMMENT = '品牌表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`         int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`       varchar(200)     NOT NULL COMMENT '分类名称',
    `parent_id`  int(10) UNSIGNED NULL     DEFAULT NULL COMMENT '上级分类ID',
    `path`       varchar(500)     not null default '-' comment '祖先节点',
    `sort`       int(10) UNSIGNED not null default 0 COMMENT '排序',
    `created_at` datetime         not null default CURRENT_TIMESTAMP comment '创建时间',
    `updated_at` datetime         not null default CURRENT_TIMESTAMP comment '修改时间',
    `deleted_at` datetime         null     default null comment '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id`) USING BTREE,
    INDEX `idx_sort` (`sort`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '商品分类表'
  ROW_FORMAT = Dynamic;


create table spec_group
(
    id     int unsigned primary key auto_increment comment '主键',
    spg_id int unsigned not null comment '品类编号',
    `name` varchar(200) not null comment '品类名称',
    unique index unq_spg_id (spg_id),
    unique index unq_name (`name`),
    index idx_spg_id (spg_id)
) comment ='品类表';


create table spec_param
(
    id        int unsigned primary key auto_increment comment '主键',
    spg_id    int unsigned not null comment '品类编号',
    spp_id    int unsigned not null comment '参数编号',
    `name`    varchar(200) not null comment '参数名称',
    `numeric` tinyint(1)   not null comment '是否为数字参数',
    unit      varchar(200) comment '单位（量词语）',
    generic   tinyint(1)   not null comment '是否为通用参数',
    searching boolean      not null comment '是否用于通用搜素',
    segments  varchar(500) comment '参数值',
    index idx_spg_id (spg_id),
    index idx_spp_id (spp_id)
) comment ='参数表';


create table if not exists `shop_product`
(
    `id`          bigint unsigned auto_increment,
    `title`       varchar(200)            not null COMMENT '标题',
    `sub_title`   varchar(200)            null     default null comment '副标题',
    `category_id` int(10) unsigned        not null comment '分类ID',
    `brand_id`    int(10) unsigned        null     default null comment '品牌ID',
    `spg_id`      int unsigned comment '品类ID',
    `images`      json                    null     default null comment '商品图片',
    `price`       decimal(10, 2) UNSIGNED not null comment '价格',
    `status`      tinyint(1)              not null default 1 comment '商品状态',
    `created_at`  datetime                not null default CURRENT_TIMESTAMP comment '创建时间',
    `updated_at`  datetime                not null default CURRENT_TIMESTAMP comment '修改时间',
    `deleted_at`  datetime                null     default null comment '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_brand_id` (`brand_id`) USING BTREE,
    INDEX `idx_category_id` (`category_id`) USING BTREE,
    INDEX `idx_spg_id` (`spg_id`) USING BTREE,
    INDEX `idx_status` (`status`) USING BTREE
) comment '商品表' ENGINE = InnoDB
                   AUTO_INCREMENT = 100;