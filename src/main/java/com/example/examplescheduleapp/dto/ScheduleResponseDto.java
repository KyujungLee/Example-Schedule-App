package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final String nickname;
    private final String title;
    private final String contents;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public ScheduleResponseDto(String nickname, String title, String contents, LocalDateTime created_at, LocalDateTime updated_at) {
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public static ScheduleResponseDto toDtoSchedule(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getUser().getNickname(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreated_at(),
                schedule.getUpdated_at()
        );
    }
}
