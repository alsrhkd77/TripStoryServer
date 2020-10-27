USE tripstory;

CREATE TABLE member (
    member_id VARCHAR(12) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    profile_image_path VARCHAR(200) NULL
);

CREATE TABLE auth (
    member_id VARCHAR(12) PRIMARY KEY,
    password VARCHAR(18) NOT NULL,
    FOREIGN KEY(member_id) REFERENCES member(member_id)
);

CREATE TABLE tag (
    tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    member_id VARCHAR(12) NOT NULL,
    FOREIGN KEY(member_id) REFERENCES member(member_id),
    CONSTRAINT TAG_NAME_MEMBER_UNIQUE UNIQUE (name, member_id)
);

CREATE TABLE post (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_time DATETIME NOT NULL,
    content VARCHAR(255) ,
    member_id VARCHAR(12) NOT NULL ,
    FOREIGN KEY(member_id) REFERENCES member(member_id)
);

