create table if not exists fileupload
(
    `id`         varchar(64) NOT NULL,
    `file_name`  varchar(255) DEFAULT NULL,
    `file_state` int          DEFAULT NULL,
    `file_url`   varchar(255) DEFAULT NULL,
    `file_type`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
