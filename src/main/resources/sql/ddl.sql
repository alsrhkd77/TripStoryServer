DROP TABLE IF EXISTS auth;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    member_id VARCHAR(12) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    profile_image_path VARCHAR(200) NULL
);

CREATE TABLE auth (
    id VARCHAR(12) PRIMARY KEY,
    password VARCHAR(18) NOT NULL,
    FOREIGN KEY(id) REFERENCES member(member_id)
);

INSERT INTO auth values ( 'tester1', 'tesert1' );