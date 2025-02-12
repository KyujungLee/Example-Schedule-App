# Example-Schedule-App

### 1. ERD 작성
![](https://velog.velcdn.com/images/nmhjyu876/post/d2843c92-81a3-4821-9816-cf90c45f7ece/image.png)

### 2. SQL
```sql
use schedule_app;

CREATE TABLE `schedule` (
                            `id` bigint auto_increment primary key 	comment '고유값',
                            `user_id` bigint not null comment '유저 고유 식별자',
                            `title`	varchar(100) not null comment '할일 제목',
                            `contents` longtext	comment '할일 내용',
                            `created_at` datetime comment '작성일',
                            `updated_at` datetime comment '수정일',
                            `reply_number` bigint default 0 comment '댓글 개수'
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

alter table `schedule` add constraint `FK_user_TO_schedule_1` foreign key (`user_id`) references `user` (`id`);

alter table reply add constraint `FK_schedule_TO_reply_1` foreign key (`schedule_id`) references schedule (id);

alter table reply add constraint `FK_user_TO_reply_1` foreign key (`user_id`) references user (id);


```

### 3. API 명세서 작성
https://documenter.getpostman.com/view/15624547/2sAYX6qMxW

>#### 1. 스케쥴

|기능|Method| URL                    |Parameter| Request Body                                    | Response                                                                                                                                                                      |상태코드|
|--|--|------------------------|--|-------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--|
|스케쥴 생성|`POST`| `/schedule`            |-| `{ "title": String, `<br>`"contents": String }` | `{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`                               |`201 Created`|
|스케쥴 목록 조회|`GET`| `/schedule/guest`      |Query: -`page` <br>-`size`| - | `[{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime, `<br>`"updated_at": LocalDateTime `<br>`"replyNumber": int }...]` |`200 OK`|
|스케쥴 단건 조회|`GET`| `/schedule/{id}/guest` |Path:-`id`| -                                               | `{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime `<br>`"replyNumber": int }`                               |`200 OK`|
|스케쥴 수정|`PATCH`| `/schedule/{id}`       |Path:-`id`| `{ "title":String, "contents":String }`         | `{ "nickname": String, `<br>`"title": String, `<br>`"contents": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`                               |`200 OK`|
|스케쥴 삭제|`DELETE`| `/schedule/{id}`       |Path:-`id`| -                                               | -                                                                                                                                                                             |`200 OK`|

>#### 2. 유저

|기능|Method|URL|Parameter| Request Body                                    |Response|상태코드|
|--|--|--|--|-------------------------------------------------|--|--|
|회원가입|`POST`|`/users/signup`|-| `{ "username": String, `<br>`"nickname": String }{ "email": String, `<br>`"password": String }`|`{ "nickname": String }`|`201 Created`|
|로그인|`PATCH`|`/users/login`|-| `{ "email": String, `<br>`"password": String }`|`{ "nickname": String }`|`202 Accepted`|
|유저정보 검색|`GET`|`/users`|-|-|`{ "username": String, `<br>`"nickname": String, `<br>`"email": String, `<br>`"created_at": LocalDateTime,`<br>` "updated_at": LocalDateTime }`|`200 OK`|
|유저정보 갱신|`PATCH`|`/users`|-| `{ "username": String, `<br>`"nickname": String, `<br>`"email": String, `<br>`"password": String }` |`{ "nickname": String }`|`200 OK`|
|유저 삭제|`DELETE`|`/users`|-| `{ "password": String }` |-|`200 OK`|

>#### 3. 댓글

|기능|Method| URL                 |Parameter| Request Body |Response|상태코드|
|--|--|---------------------|--|-------------------------------------------------|--|--|
|댓글 생성|`POST`| `/reply/{id}`       |Path: -`id`| `{ "contents": String }` |`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }` | `201 Created`|
|댓글 조회|`GET`| `/reply/{id}/guest` |Query: -`page` - `size` <br> Path: -`id`| - |`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }...]`|`200 OK`|
|댓글 수정|`PATCH`| `/reply/{id}`       |Path: -`id`| `{ "contents": String }`|`{ "id": Long,`<br>`"contents": String, `<br>` "nickname": String, `<br>` "updated_at": LocalDateTime }`|`200 OK`|
|댓글 삭제|`DELETE`| `/reply/{id}`       |Path: -`id`| - |-|`200 OK`|


### 4. 기능설명

#### 1. 코드 흐름
- 정상적인 요청 시 코드 흐름
```
클라이언트 → 필터 → MVC → 필터 → 클라이언트
```

- 예외 발생 시 코드 흐름 (1. 서비스, 2.보안필터)
```
1. 클라이언트 → 로그필터 → 보안필터 → 컨트롤러 → 서비스(예외발생) → 글로벌예외핸들러 → 로그필터 → 클라이언트
2. 클라이언트 → 로그필터 → 보안필터(예외발생) → 예외컨트롤러 → 글로벌예외핸들러 → 로그필터 → 클라이언트
```
#### 2. 추가 기능

1. 예외 발생 시 출력 양식 규격화 (GlobalExceptionHandler, ErrerResponseDto)
2. 보안필터에서 오류 발생 시 에러컨트롤러로 포워딩 (톰캣이 예외를 500으로 강제로 응답하는걸 ControllerAdvice 가 처리하여 규격화)
3. 요청 로그와 응답 로그를 출력 (LoggingFilter)
4. URI 에 따른 권한 분리 (FilterConfig)
5. 페이지 객체의 출력 양식 규격화 (스프링 버전에 따른 출력변화 방지, PageResponseDto)
