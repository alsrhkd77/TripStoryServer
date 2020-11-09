USE tripstory;

CREATE TABLE member
(
    member_id          VARCHAR(255) PRIMARY KEY,
    name               VARCHAR(20)  NOT NULL,
    email              VARCHAR(40)  NOT NULL,
    profile_image_path VARCHAR(200) NULL,
    nick_name          VARCHAR(30)  NOT NULL UNIQUE
);

CREATE TABLE auth
(
    member_id VARCHAR(255) PRIMARY KEY,
    password  VARCHAR(18) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE tag
(
    tag_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(30)  NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE,
    CONSTRAINT TAG_NAME_MEMBER_UNIQUE UNIQUE (name, member_id)
);

CREATE TABLE post
(
    post_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_time DATETIME     NOT NULL,
    content      VARCHAR(255),
    member_id    VARCHAR(255) NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    scope VARCHAR(10) NOT NULL DEFAULT 'PUBLIC',
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE post_tag
(
    post_tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT,
    tag_id      BIGINT,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);

CREATE TABLE post_image
(
    post_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    path          VARCHAR(255) NOT NULL UNIQUE,
    post_id       BIGINT       NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
);

CREATE TABLE travel_post
(
    post_id      BIGINT PRIMARY KEY,
    travel_start DATE,
    travel_end   DATE,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
);

CREATE TABLE normal_post
(
    post_id     BIGINT PRIMARY KEY,
    visit_start DATE,
    visit_end   DATE,
    travel_id   BIGINT,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
    FOREIGN KEY (travel_id) REFERENCES travel_post (post_id)
);

CREATE TABLE post_like
(
    post_id   BIGINT NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, member_id)
);

CREATE TABLE travel_course
(
    travel_id BIGINT NOT NULL,
    lat DOUBLE NOT NULL ,
    lng DOUBLE NOT NULL ,
    pass_date DATETIME NOT NULL,
    FOREIGN KEY (travel_id) REFERENCES post(post_id),
    PRIMARY KEY (lat, lng, pass_date)
);

CREATE TABLE follow
(
    follow_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(255) ,
    nick_name VARCHAR(30)  ,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (nick_name) REFERENCES member(nick_name) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT MEMBER_ID_NICK_NAME_UNIQUE UNIQUE (member_id, nick_name)
);