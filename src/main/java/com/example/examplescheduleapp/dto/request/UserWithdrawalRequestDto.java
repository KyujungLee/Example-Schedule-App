package com.example.examplescheduleapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserWithdrawalRequestDto {

    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 양식이 맞지 않습니다")
    private final String email;

    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @NotBlank(message = "공백은 불가능합니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하이어야 합니다")
    private final String password;

    public UserWithdrawalRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
