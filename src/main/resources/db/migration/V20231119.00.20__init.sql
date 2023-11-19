CREATE TABLE `article`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `uid` bigint NOT NULL,
                            `title` varchar(255)  NOT NULL DEFAULT '',
                            `create_time` timestamp NOT NULL,
                            `type` varchar(255) NOT NULL DEFAULT '',
                            `content` text  NOT NULL,
                            `pubed` tinyint NULL DEFAULT NULL,
                            `render` text  NULL,
                            `update_time` timestamp NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE
);
CREATE TABLE `article_tag`  (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `name` varchar(25) NULL DEFAULT NULL,
        PRIMARY KEY (`id`) USING BTREE
);
CREATE TABLE `follow_detail`  (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint NULL DEFAULT NULL,
                                  `fan_id` bigint NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE `rel_article_tag`  (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `aid` bigint NULL DEFAULT NULL,
                                    `tid` bigint NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE `rel_user_role`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `uid` bigint NULL DEFAULT NULL,
                                  `rid` bigint NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE `role`  (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255)  NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE `user`  (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255)  NULL DEFAULT NULL,
                         `avatar` varchar(255)  NULL DEFAULT NULL,
                         `create_time` timestamp NULL DEFAULT NULL,
                         `update_time` timestamp NULL DEFAULT NULL,
                         `account` varchar(255)  NULL DEFAULT NULL,
                         `password` varchar(255)  NULL DEFAULT NULL,
                         `sex` varchar(25)  NULL DEFAULT NULL,
                         `mail` varchar(255) NULL DEFAULT NULL,
                         `nick_name` varchar(255)  NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
);


