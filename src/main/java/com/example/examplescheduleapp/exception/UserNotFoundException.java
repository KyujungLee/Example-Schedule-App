package com.example.examplescheduleapp.exception;

public class UserNotFoundException extends RuntimeException {

    // 기본 에러 메시지
    private static final String DEFAULT_MESSAGE = "존재하지 않는 유저입니다.";

    // 기본 메시지를 사용하는 생성자
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    // 커스텀 메시지를 받을 수 있는 생성자
    public UserNotFoundException(String message) {
        super(message);
    }
}
