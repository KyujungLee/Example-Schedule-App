package com.example.examplescheduleapp.dto.request;

import lombok.Getter;

@Getter
public class ReplyRequestDto {

    private final String contents;

    public ReplyRequestDto(String contents) {
        this.contents = contents;
    }
}
