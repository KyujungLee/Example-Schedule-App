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
            @RequestBody UserSignUpRequestDto dto,
            HttpServletRequest request
    ){
        UserSignUpResponseDto savedUser = userService.signUp(dto.getUsername(),dto.getNickname(), dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(
            @RequestBody UserLoginRequestDto dto,
            HttpServletRequest request
    ){
        UserLoginResponseDto loginUser = userService.login(dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(loginUser, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<UserFindByNicknameResponseDto> findByNickname(
            @RequestParam String nickname
    ){
        UserFindByNicknameResponseDto findByIdUser = userService.findByNickname(nickname);
        return new ResponseEntity<>(findByIdUser, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserUpdateResponseDto> update(
            @RequestBody UserUpdateRequestDto dto,
            HttpServletRequest request
    ){
        UserUpdateResponseDto updatedUser = userService.update(dto.getUsername(), dto.getNickname() , dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> withdrawal(
            @RequestParam String nickname,
            @RequestBody UserWithdrawalRequestDto dto,
            HttpServletRequest request
    ){
        userService.withdrawal(nickname, dto.getPassword(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
