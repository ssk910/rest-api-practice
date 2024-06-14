---- 스키마 생성 ------
CREATE SCHEMA student;


---- 테이블 생성 ------
CREATE TABLE student.student
(
    `id`                 BIGINT         NOT NULL COMMENT '아이디',
    `student_number`     VARCHAR(100)   NOT NULL COMMENT '학번',
    `name`               VARCHAR(100)   NOT NULL COMMENT '이름',
    `email`              VARCHAR(255)   NOT NULL COMMENT '이메일',
    PRIMARY KEY (id)
);