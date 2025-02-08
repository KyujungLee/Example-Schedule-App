package com.example.examplescheduleapp.dto.response;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserFindByNicknameResponseDto {

    private final String username;
    private final String nickname;
    private final String email;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public UserFindByNicknameResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
    }

}
