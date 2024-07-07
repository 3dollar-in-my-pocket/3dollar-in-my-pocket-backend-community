CREATE TABLE `sticker`
(
    `id`    BIGINT  NOT NULL AUTO_INCREMENT,
    `sticker_group` VARCHAR(100)    NOT NULL,
    `workspace_id`  VARCHAR(100)    NOT NULL,
    `name`          VARCHAR(100)    NOT NULL,
    `image_url`     VARCHAR(200)    NOT NULL,
    `status`        VARCHAR(30)     NOT NULL,
    `priority`      INTEGER         NOT NULL,
    `created_at`    DATETIME(6)     DEFAULT NULL,
    `updated_at`    DATETIME(6)     DEFAULT NULL,
    PRIMARY KEY (`id`)

) ENGINE = InnoDB;

CREATE TABLE `sticker_action`
(
    `id`    BIGINT  NOT NULL AUTO_INCREMENT,
    `sticker_group`     VARCHAR(50) NOT NULL,
    `workspace_id`      VARCHAR(100) NOT NULL,
    `sticker_ids`       VARCHAR(500) NOT NULL,
    `account_id`        VARCHAR(100)  NOT NULL,
    `target_id`         VARCHAR(100)  NOT NULL,
    `created_at`        DATETIME(6)   DEFAULT NULL,
    `updated_at`        DATETIME(6)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_sticker_action_1` (`account_id`, `target_id`, `sticker_group`, `workspace_id`)

) ENGINE = InnoDB;


CREATE TABLE `api_key`
(
    `id`    BIGINT  NOT NULL AUTO_INCREMENT,
    `workspace_id`  VARCHAR(100)    NOT NULL,
    `api_key`       VARCHAR(100)    NOT NULL,
    `description`   VARCHAR(200)    NOT NULL,
    `status`        VARCHAR(30)     NOT NULL,
    `created_at`    DATETIME(6)     DEFAULT NULL,
    `updated_at`    DATETIME(6)     DEFAULT NULL,
    PRIMARY KEY(`id`),
    UNIQUE KEY (`api_key`)
) ENGINE = InnoDB;

create table `post`
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    workspace_id varchar(50)       NOT NULL,
    post_group   varchar(30)        NOT NULL,
    target_id    varchar(50)       NOT NULL,
    parent_id    bigint             DEFAULT NULL,
    account_id   varchar(100)       NOT NULL ,
    title        varchar(100)       DEFAULT NULL,
    content      varchar(3000)      NOT NULL,
    status       varchar(30)        NOT NULL,
    created_at   datetime(6)        DEFAULT  NULL,
    updated_at   datetime(6),
    KEY (`workspace_id`, `post_group`, `target_id`, `status`)
);


create table `post_section`
(
    id           bigint auto_increment PRIMARY KEY,
    post_id      bigint       not null,
    url          varchar(500) not null,
    ratio        double       not null,
    section_type varchar(30) not null,
    created_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
);
