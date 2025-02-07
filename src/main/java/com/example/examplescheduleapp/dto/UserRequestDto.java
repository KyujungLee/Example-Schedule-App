package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String username;
    private final String nickname;
    private final String email;
    private final String password;

    public UserRequestDto(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
