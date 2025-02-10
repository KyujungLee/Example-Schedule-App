package com.example.examplescheduleapp.exception;

public class ScheduleNotFoundException extends RuntimeException {

    // 기본 에러 메시지
    private static final String DEFAULT_MESSAGE = "일정을 찾을 수 없습니다.";

    // 기본 메시지를 사용하는 생성자
    public ScheduleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    // 커스텀 메시지를 받을 수 있는 생성자
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
