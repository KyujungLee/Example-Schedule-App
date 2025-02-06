package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String username;
    private final String title;
    private final String contents;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public ScheduleResponseDto(Long id, String username, String title, String contents, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public static ScheduleResponseDto toDto(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreated_at(),
                schedule.getUpdated_at()
        );
    }
}
