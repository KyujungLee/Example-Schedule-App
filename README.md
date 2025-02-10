# Example-Schedule-App

### 1. ERD 작성
![](https://velog.velcdn.com/images/nmhjyu876/post/d2843c92-81a3-4821-9816-cf90c45f7ece/image.png)

### 2. SQL
```sql
CREATE TABLE `Schedule` (
    `id` bigint auto_increment primary key comment '고유값',
    `user_id` bigint not null comment '유저 고유 식별자',
    `title`	varchar(100) not null comment '할일 제목',
    `contents` longtext	comment '할일 내용',
    `created_at` datetime comment '작성일',
    `updated_at` datetime comment '수정일'
);

CREATE TABLE `user` (
    `id` bigint	auto_increment primary key comment '고유값',
    `username` varchar(10) comment '유저명',
    `email`	varchar(100) not null unique comment '이메일',
    `created_at` datetime comment '작성일',
    `updated_at` datetime comment '수정일',
    `nickname` varchar(10) not null unique comment '닉네임',
    `password` varchar(255) not null comment '비밀번호'
);

CREATE TABLE `reply` (
    `id` bigint	auto_increment primary key comment '고유값',
    `contents` longtext comment '댓글내용',
    `created_at` datetime comment '작성일',
    `updated_at` datetime comment '수정일',
    `user_id` bigint not null comment '유저 고유 식별자',
    `schedule_id` bigint not null comment '일정 고유 식별자'
);

alter table schedule add constraint `FK_user_TO_schedule_1` foreign key (`user_id`) references `user` (`id`);

alter table reply add constraint `FK_schedule_TO_reply_1` foreign key (`schedule_id`) references schedule (id);

alter table reply add constraint `FK_user_TO_reply_1` foreign key (`user_id`) references user (id);
```

### 3. API 명세서 작성
https://documenter.getpostman.com/view/15624547/2sAYX6qMxW

>#### 1. 스케쥴

|기능|Method|URL|Parameter| Request Body                                    |Response|상태코드|
|--|--|--|--|-------------------------------------------------|--|--|
|스케쥴 생성|`POST`|`/schedule`|-| `{ "title": String, `<br>`"contents": String }` |`{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`|`201 Created`|
|스케쥴 목록 조회|`GET`|`/schedule`|Query: -`page` <br>-`size`| - |`[{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime, `<br>`"updated_at": LocalDateTime }...]`|`200 OK`|
|스케쥴 단건 조회|`GET`|`/schedule/{id}`|Path:-`id`| -                                               |`{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`|`200 OK`|
|스케쥴 수정|`PATCH`|`/schedule/{id}`|Path:-`id`| `{ "title":String, "contents":String }`         |`{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`|`200 OK`|
|스케쥴 삭제|`DELETE`|`/schedule/{id}`|Path:-`id`| -                                               |-|`200 OK`|

>#### 2. 유저

|기능|Method|URL|Parameter| Request Body                                    |Response|상태코드|
|--|--|--|--|-------------------------------------------------|--|--|
|회원가입|`POST`|`/users/signup`|-| `{ "username": String, `<br>`"nickname": String }{ "email": String, `<br>`"password": String }`|`{ "nickname": String }`|`201 Created`|
|로그인|`PATCH`|`/users/login`|-| `{ "email": String, `<br>`"password": String }`|`{ "nickname": String }`|`202 Accepted`|
|유저정보 검색|`GET`|`/users`|-|-|`{ "username": String, `<br>`"nickname": String, `<br>`"email": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`|`200 OK`|
|유저정보 갱신|`PATCH`|`/users`|-| `{ "username": String, `<br>`"nickname": String, `<br>`"email": String, `<br>`"password": String }` |`{ "nickname": String }`|`200 OK`|
|유저 삭제|`DELETE`|`/users`|-| `{ "password": String }` |-|`200 OK`|

>#### 3. 댓글

|기능|Method|URL|Parameter| Request Body |Response|상태코드|
|--|--|--|--|-------------------------------------------------|--|--|
|댓글 생성|`POST`|`/reply/{id}`|Path: -`id`| `{ "contents": String }` |`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }` | `201 Created`|
|댓글 조회|`GET`|`/reply/{id}`|Query: -`page` - `size` <br> Path: -`id`| - |`[{ "contents": String }` |`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }...]`|`200 OK`|
|댓글 수정|`PATCH`|`/reply/{id}`|Path: -`id`| `{ "contents": String }`|`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }`|`200 OK`|
|댓글 삭제|`DELETE`|`/reply/{id}`|Path: -`id`| - |-|`200 OK`|