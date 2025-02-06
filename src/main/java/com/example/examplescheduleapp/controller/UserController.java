package com.example.examplescheduleapp.controller;

import com.example.examplescheduleapp.dto.UserRequestDto;
import com.example.examplescheduleapp.dto.UserResponseDto;
import com.example.examplescheduleapp.service.UserService;
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

    @PostMapping
    public ResponseEntity<UserResponseDto> save(
            @RequestBody UserRequestDto dto
    ){
        UserResponseDto savedUser = userService.save(dto.getName(), dto.getEmail());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<UserResponseDto> findAllUser = userService.findAll();
        return new ResponseEntity<>(findAllUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable Long id
    ){
        UserResponseDto findByIdUser = userService.findById(id);
        return new ResponseEntity<>(findByIdUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ){
        UserResponseDto updatedUser = userService.update(id, dto.getName(), dto.getEmail());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
