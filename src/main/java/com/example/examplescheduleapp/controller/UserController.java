package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.request.UserLoginRequestDto;
import com.example.examplescheduleapp.dto.request.UserSignUpRequestDto;
import com.example.examplescheduleapp.dto.request.UserUpdateRequestDto;
import com.example.examplescheduleapp.dto.request.UserWithdrawalRequestDto;
import com.example.examplescheduleapp.dto.response.UserFindByNicknameResponseDto;
import com.example.examplescheduleapp.dto.response.UserLoginResponseDto;
import com.example.examplescheduleapp.dto.response.UserSignUpResponseDto;
import com.example.examplescheduleapp.dto.response.UserUpdateResponseDto;
import com.example.examplescheduleapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signUp(
            @Valid @RequestBody UserSignUpRequestDto dto,
            HttpServletRequest request
    ){
        UserSignUpResponseDto savedUser = userService.signUp(dto.getUsername(),dto.getNickname(), dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(
            @Valid @RequestBody UserLoginRequestDto dto,
            HttpServletRequest request
    ){
        UserLoginResponseDto loginUser = userService.login(dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(loginUser, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<UserFindByNicknameResponseDto> findByNickname(
            @PathVariable String nickname,
            HttpServletRequest request
    ){
        UserFindByNicknameResponseDto findByNicknameUser = userService.findByNickname(nickname, request);
        return new ResponseEntity<>(findByNicknameUser, HttpStatus.OK);
    }

    @PatchMapping("/{nickname}")
    public ResponseEntity<UserUpdateResponseDto> update(
            @PathVariable String nickname,
            @Valid @RequestBody UserUpdateRequestDto dto,
            HttpServletRequest request
    ){
        UserUpdateResponseDto updatedUser = userService.update(nickname, dto.getUsername(), dto.getNickname() , dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> withdrawal(
            @PathVariable String nickname,
            @Valid @RequestBody UserWithdrawalRequestDto dto,
            HttpServletRequest request
    ){
        userService.withdrawal(nickname, dto.getPassword(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
