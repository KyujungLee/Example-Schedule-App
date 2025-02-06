use schedule_app;

CREATE TABLE `Schedule` (
                            `id`	bigint auto_increment primary key 	COMMENT '고유값',
                            `username`	varchar(10)	NOT NULL	COMMENT '작성 유저명',
                            `title`	varchar(100)	NOT NULL	COMMENT '할일 제목',
                            `contents`	longtext	COMMENT '할일 내용',
                            `created_at`	datetime	COMMENT '작성일',
                            `updated_at`	datetime	COMMENT '수정일'
);
