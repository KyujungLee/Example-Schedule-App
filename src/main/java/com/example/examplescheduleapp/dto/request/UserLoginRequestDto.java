package com.example.examplescheduleapp.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 양식이 맞지 않습니다")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목이며, 공백은 불가능합니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하이어야 합니다")
    private final String password;

    public UserLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
