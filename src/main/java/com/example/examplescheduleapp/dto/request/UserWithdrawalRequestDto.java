package com.example.examplescheduleapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserWithdrawalRequestDto {

    @NotBlank(message = "비밀번호는 필수 입력 항목이며, 공백은 불가능합니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하이어야 합니다")
    private final String password;

    public UserWithdrawalRequestDto(String password) {
        this.password = password;
    }
}
