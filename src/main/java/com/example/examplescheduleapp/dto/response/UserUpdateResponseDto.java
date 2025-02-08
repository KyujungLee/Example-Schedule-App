package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {

    private final String nickname;

    public UserUpdateResponseDto(User user) {
        this.nickname = user.getNickname();
    }

}
