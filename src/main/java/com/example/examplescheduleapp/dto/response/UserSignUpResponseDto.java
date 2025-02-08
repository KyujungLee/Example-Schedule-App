package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

@Getter
public class UserSignUpResponseDto {

    private final String nickname;

    public UserSignUpResponseDto(User user) {
        this.nickname = user.getNickname();
    }

}
