package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {

    private final Long id;
    private final String contents;
    private final String nickname;
    private final LocalDateTime updated_at;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.nickname = reply.getUser().getNickname();
        this.updated_at = reply.getUpdated_at();
    }

    public static ReplyResponseDto toDtoReply(Reply reply){
        return new ReplyResponseDto(reply);
    }
}