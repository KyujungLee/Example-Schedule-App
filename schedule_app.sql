use schedule_app;

CREATE TABLE `Schedule` (
                            `id` bigint auto_increment primary key 	comment '고유값',
                            `username` varchar(10) not null comment '작성 유저명',
                            `title`	varchar(100) not null comment '할일 제목',
                            `contents` longtext	comment '할일 내용',
                            `created_at` datetime comment '작성일',
                            `updated_at` datetime comment '수정일'
);

truncate table schedule;
alter table schedule drop column username;
alter table schedule add column user_id bigint not null;

CREATE TABLE `user` (
                        `id` bigint	auto_increment primary key comment '고유값',
                        `username` varchar(10) comment '유저명',
                        `email`	varchar(100) not null unique comment '이메일',
                        `created_at` datetime comment '작성일',
                        `updated_at` datetime comment '수정일'
);

ALTER TABLE `schedule` ADD CONSTRAINT `FK_user_TO_schedule_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

alter table user add column password varchar(20) not null comment '비밀번호';

alter table user add column nickname varchar(10) not null unique comment '닉네임';

CREATE TABLE `reply` (
                        `id` bigint	auto_increment primary key comment '고유값',
                        `contents` longtext comment '댓글내용',
                        `created_at` datetime comment '작성일',
                        `updated_at` datetime comment '수정일',
                        `user_id` bigint not null comment '유저 고유 식별자',
                        `schedule_id` bigint not null comment '일정 고유 식별자'

);


alter table reply add constraint `FK_schedule_TO_reply_1` foreign key (`schedule_id`) references schedule (id);

alter table reply add constraint `FK_user_TO_reply_1` foreign key (`user_id`) references user (id);