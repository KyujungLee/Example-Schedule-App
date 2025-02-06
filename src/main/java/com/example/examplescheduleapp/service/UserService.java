package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.UserResponseDto;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto save(String name, String email) {

        User user = new User(name, email);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreated_at(),
                savedUser.getUpdated_at()
        );

    }

    public List<UserResponseDto> findAll() {

        return userRepository.findAll().stream().map(UserResponseDto::toDtoUser).toList();

    }

    public UserResponseDto findById(Long id) {

        User findByIdUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다"));

        return new UserResponseDto(
                findByIdUser.getId(),
                findByIdUser.getName(),
                findByIdUser.getEmail(),
                findByIdUser.getCreated_at(),
                findByIdUser.getUpdated_at()
        );

    }

    @Transactional
    public UserResponseDto update(Long id, String name){

        User findByIdUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다"));

        findByIdUser.updateName(name);

        User savedUser = userRepository.save(findByIdUser);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreated_at(),
                savedUser.getUpdated_at()
        );

    }

    public void delete(Long id) {

        User findByIdUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디가 존재하지 않습니다"));

        userRepository.delete(findByIdUser);

    }
}
