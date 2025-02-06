package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String name;
    private String email;
    private String password;

    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
