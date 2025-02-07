package com.example.examplescheduleapp.dto;

import com.example.examplescheduleapp.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final LocalDateTime created_at;
    private final LocalDateTime updated_at;

    public UserResponseDto(Long id, String username, String nickname, String email, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public static UserResponseDto toDtoUser (User user){
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getCreated_at(),
                user.getUpdated_at()
        );
    }
}
