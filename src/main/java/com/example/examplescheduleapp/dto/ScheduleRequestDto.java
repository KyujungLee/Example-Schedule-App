package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String name;
    private final String email;
    private final String title;
    private final String contents;

    public ScheduleRequestDto(String name, String email, String title, String contents) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.contents = contents;
    }
}
