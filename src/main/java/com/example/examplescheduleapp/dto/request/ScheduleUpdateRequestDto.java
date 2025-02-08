package com.example.examplescheduleapp.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class ScheduleUpdateRequestDto {

    @NotEmpty(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 100, message = "제목은 100자 이하이어야 합니다.")
    private final String title;

    private final String contents;

    public ScheduleUpdateRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
