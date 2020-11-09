# 회원 정보를 저장하는 테이블
# 소셜 로그인, TS 로그인 구분없이 모두 해당 테이블에 저장
CREATE TABLE member
(
    member_id          VARCHAR(255) PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    email              VARCHAR(40) NOT NULL,
    profile_image_path VARCHAR(200) DEFAULT 'https://i.stack.imgur.com/l60Hf.png',
    nick_name          VARCHAR(30) NOT NULL UNIQUE
);

# 회원 인증 정보 테이블
# 회원 테이블 자체가 사라지면 같이 사라짐
CREATE TABLE auth
(
    member_id VARCHAR(255) PRIMARY KEY,
    password  VARCHAR(18) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON DELETE CASCADE
);

# 팔로우 정보 테이블
# member_id는 고객 ID
# following_id는 고객이 팔로우 하는 사람 ID
CREATE TABLE follow
(
    member_id VARCHAR(255) NOT NULL ,
    following_id VARCHAR(255) NOT NULL ,
    CONSTRAINT PRIMARY KEY PK_followTBL (member_id, following_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
        ON DELETE CASCADE ,
    FOREIGN KEY (following_id) REFERENCES member(member_id)
        ON DELETE CASCADE
);