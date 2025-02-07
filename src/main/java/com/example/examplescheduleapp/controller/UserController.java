package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.*;
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
    public ResponseEntity<UserNicknameResponseDto> signUp(
            @RequestBody UserSignUpRequestDto dto,
            HttpServletRequest request
    ){
        UserNicknameResponseDto savedUser = userService.signUp(dto.getUsername(),dto.getNickname(), dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserNicknameResponseDto> login(
            @ModelAttribute UserLoginRequestDto dto,
            HttpServletRequest request
    ){
        UserNicknameResponseDto loginUser = userService.login(dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(loginUser, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<UserInformationResponseDto> findByNickname(
            @RequestParam String nickname
    ){
        UserInformationResponseDto findByIdUser = userService.findByNickname(nickname);
        return new ResponseEntity<>(findByIdUser, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserNicknameResponseDto> update(
            @RequestParam String nickname,
            @RequestBody UserUpdateRequestDto dto,
            HttpServletRequest request
    ){
        UserNicknameResponseDto updatedUser = userService.update(nickname, dto.getUsername(), dto.getNickname() , dto.getEmail(), dto.getPassword(), request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> withdrawal(
            @RequestParam String nickname,
            @ModelAttribute UserLoginRequestDto dto,
            HttpServletRequest request
    ){
        userService.withdrawal(nickname, dto.getPassword(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
