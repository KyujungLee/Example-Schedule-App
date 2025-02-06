package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String username;
    private final String title;
    private final String contents;

    public ScheduleRequestDto(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
