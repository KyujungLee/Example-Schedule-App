package com.example.examplescheduleapp.dto.response;

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
    private final Long replyNumber;

    public ScheduleResponseDto(Schedule schedule) {
        this.nickname = schedule.getUser().getNickname();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.created_at = schedule.getCreatedAt();
        this.updated_at = schedule.getUpdatedAt();
        this.replyNumber = schedule.getReplyNumber();
    }

    public static ScheduleResponseDto toDtoSchedule(Schedule schedule){
        return new ScheduleResponseDto(schedule);
    }
}
