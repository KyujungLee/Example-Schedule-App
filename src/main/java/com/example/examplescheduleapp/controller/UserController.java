package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.*;
import com.example.examplescheduleapp.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signUp(
            @RequestBody UserSignUpRequestDto dto
    ){
        UserSignUpResponseDto savedUser = userService.signUp(dto.getUsername(),dto.getNickname(), dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserSignUpResponseDto> login(
            @ModelAttribute UserLoginRequestDto dto
    ){
        UserSignUpResponseDto loginUser = userService.login(dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(loginUser, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<UserResponseDto> findAllUser = userService.findAll();
        return new ResponseEntity<>(findAllUser, HttpStatus.OK);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<UserResponseDto> findByNickname(
            @PathVariable String nickname
    ){
        UserResponseDto findByIdUser = userService.findByNickname(nickname);
        return new ResponseEntity<>(findByIdUser, HttpStatus.OK);
    }

    @PatchMapping("/{nickname}")
    public ResponseEntity<UserSignUpResponseDto> update(
            @PathVariable String nickname,
            @RequestBody UserRequestDto dto
    ){
        UserSignUpResponseDto updatedUser = userService.update(nickname, dto.getUsername(), dto.getNickname() , dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> withdrawal(
            @PathVariable String nickname,
            @ModelAttribute UserLoginRequestDto dto
    ){
        userService.withdrawal(nickname, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
