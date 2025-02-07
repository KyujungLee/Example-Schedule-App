package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignUpResponseDto {

    private final String nickname;

    public UserSignUpResponseDto(String nickname) {
        this.nickname = nickname;
    }

}
