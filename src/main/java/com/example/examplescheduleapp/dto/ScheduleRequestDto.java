package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String nickname;
    private final String title;
    private final String contents;

    public ScheduleRequestDto(String nickname, String title, String contents) {
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
    }
}
