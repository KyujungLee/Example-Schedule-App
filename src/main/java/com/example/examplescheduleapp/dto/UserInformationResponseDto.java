package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInformationResponseDto {

    private final String username;
    private final String nickname;
    private final String email;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public UserInformationResponseDto(String username, String nickname, String email, LocalDateTime created_at, LocalDateTime updated_at) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
