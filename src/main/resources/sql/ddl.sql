# 회원 정보를 저장하는 테이블
# 소셜 로그인, TS 로그인 구분없이 모두 해당 테이블에 저장
CREATE TABLE member
(
    member_id          VARCHAR(255) PRIMARY KEY,
    name               VARCHAR(20) NOT NULL,
    email              VARCHAR(40) NOT NULL,
    profile_image_path VARCHAR(200) NOT NULL DEFAULT 'https://i.stack.imgur.com/l60Hf.png',
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
    member_id    VARCHAR(255) NOT NULL,
    following_id VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY PK_followTBL (member_id, following_id),
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES member (member_id)
        ON DELETE CASCADE
);

# 게시물 정보 테이블
# 일반 게시물, 여행 게시물이 가지는 공통적인 부분만 저장
# type 은 해당 게시물이 여행, 일반인지 구분
# scope 는 공개범위 PUBLIC : 전체 공개, PRIVATE : 완전 비공개, FRIEND : 친구 공개
CREATE TABLE post
(
    post_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_time DATETIME     NOT NULL,
    content      TEXT         NULL,
    member_id    VARCHAR(255) NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    scope        VARCHAR(10)  NOT NULL DEFAULT 'PUBLIC',
    FOREIGN KEY (member_id) REFERENCES member (member_id) ON DELETE CASCADE
);

# 태그 정보 테이블
# 사용자가 사용한 태그를 저장함
# (태그 이름 + 회원 ID)은 중복될 수 없음
CREATE TABLE tag
(
    tag_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag_name  VARCHAR(30)  NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON DELETE CASCADE
);

# 게시물에 사용된 태그 정보 테이블
# 게시물 ID와 태그 ID로 기본키 구성
# 관련된 게시물 삭제 시 해당 테이블의 데이터 제거
CREATE TABLE post_tag
(
    post_id BIGINT NOT NULL,
    tag_id  BIGINT NOT NULL,
    CONSTRAINT PRIMARY KEY PK_post_tagTBL (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);

# 게시물에 사용된 이미지 정보 테이블
# 관련된 게시물 삭제 시 해당 테이블의 데이터 제거
CREATE TABLE post_image
(
    post_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    path          VARCHAR(255) NOT NULL UNIQUE,
    post_id       BIGINT       NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (post_id)
        ON DELETE CASCADE
);

# 게시물에 눌러진 좋아요 정보 테이블
# 한 회원은 하나의 게시물에 대해서 하나의 좋아요만 표기 가능
CREATE TABLE post_like
(
    post_id   BIGINT       NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    CONSTRAINT PRIMARY KEY PK_post_likeTBL (post_id, member_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
        ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON DELETE CASCADE
);

# 일반 게시물 정보 테이블
# post 테이블에 저장된 자료의 성격을 나타냄
# 다른 여행 게시물에 포함될 수 있으며
# 여행 게시물에 포함된 일반 게시물은 여행 게시물이 삭제되어도 제거되지 않음
CREATE TABLE normal_post
(
    post_id     BIGINT PRIMARY KEY,
    visit_start DATE   NULL,
    visit_end   DATE   NULL,
    travel_id   BIGINT NULL,
    FOREIGN KEY (post_id) REFERENCES post (post_id)
        ON DELETE CASCADE,
    FOREIGN KEY (travel_id) REFERENCES travel_post (post_id)
        ON DELETE SET NULL
);

# 여행 게시물 정보 테이블
# 다른 일반 게시물을 포함할 수 있음
CREATE TABLE travel_post
(
    post_id      BIGINT PRIMARY KEY,
    travel_start DATE,
    travel_end   DATE,
    FOREIGN KEY (post_id) REFERENCES post (post_id)
        ON DELETE CASCADE
);

# 여행 코스 정보 테이블
# 위도, 경도, 방문일 정보를 가짐
CREATE TABLE travel_course
(
    travel_course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    travel_id        BIGINT   NOT NULL,
    lat              DOUBLE   NOT NULL,
    lng              DOUBLE   NOT NULL,
    pass_date        DATETIME NOT NULL,
    FOREIGN KEY (travel_id) REFERENCES travel_post (post_id)
        ON DELETE CASCADE
);

# 댓글 정보 테이블
# 작성자와 댓글이 달린 게시물, 작성일시 정보를 가짐
CREATE TABLE post_comment
(
    post_comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(255) NOT NULL ,
    content TEXT NOT NULL ,
    created_time DATETIME NOT NULL ,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id)
        ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
        ON DELETE CASCADE
);