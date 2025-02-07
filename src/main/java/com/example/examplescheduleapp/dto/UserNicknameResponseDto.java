package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class UserNicknameResponseDto {

    private final String nickname;

    public UserNicknameResponseDto(String nickname) {
        this.nickname = nickname;
    }

}
