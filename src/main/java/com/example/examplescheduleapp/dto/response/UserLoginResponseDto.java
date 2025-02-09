package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private final String nickname;

    public UserLoginResponseDto(User user) {
        this.nickname = user.getNickname();
    }

}
