package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    @NotNull
    private final String nickname;

    public UserLoginResponseDto(User user) {
        this.nickname = user.getNickname();
    }

}
