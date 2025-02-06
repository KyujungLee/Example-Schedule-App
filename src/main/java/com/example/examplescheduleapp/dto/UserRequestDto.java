package com.example.examplescheduleapp.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String name;
    private String email;

    public UserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
