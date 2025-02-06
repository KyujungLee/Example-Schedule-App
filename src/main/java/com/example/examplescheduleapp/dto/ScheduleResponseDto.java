package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String title;
    private final String contents;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public ScheduleResponseDto(Long id, String name, String email, String title, String contents, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public static ScheduleResponseDto toDtoSchedule(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getName(),
                schedule.getUser().getEmail(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreated_at(),
                schedule.getUpdated_at()
        );
    }
}
