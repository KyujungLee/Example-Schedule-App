package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleTitleAndContentsRequestDto {

    private final String title;
    private final String contents;

    public ScheduleTitleAndContentsRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
