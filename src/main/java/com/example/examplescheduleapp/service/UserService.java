package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.UserInformationResponseDto;
import com.example.examplescheduleapp.dto.UserNicknameResponseDto;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.filter.Const;
import com.example.examplescheduleapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserNicknameResponseDto signUp(String username, String nickname, String email, String password, HttpServletRequest request) {

        User user = new User(username, nickname, email, password);

        User savedUser;

        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일 또는 닉네임입니다.");
        }

        HttpSession session = request.getSession(true);
        session.invalidate();
        session.setAttribute(Const.LOGIN_USER, savedUser.getNickname());

        return new UserNicknameResponseDto(
                savedUser.getNickname()
        );
    }

    public UserNicknameResponseDto login(String email, String password, HttpServletRequest request) {

        User findByEmailUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "계정이 존재하지 않습니다."));

        if (!findByEmailUser.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession(true);
        session.invalidate();
        session.setAttribute(Const.LOGIN_USER, findByEmailUser.getNickname());

        return new UserNicknameResponseDto(
                findByEmailUser.getNickname()
        );
    }

    public UserInformationResponseDto findByNickname(String nickname) {

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));

        return new UserInformationResponseDto(
                findByNicknameUser.getUsername(),
                findByNicknameUser.getNickname(),
                findByNicknameUser.getEmail(),
                findByNicknameUser.getCreated_at(),
                findByNicknameUser.getUpdated_at()
        );

    }

    @Transactional
    public UserNicknameResponseDto update(String originalNickname, String updateUsername, String updateNickname, String updateEmail, String updatePassword, HttpServletRequest request) {

        User findByNicknameUser = userRepository.findByNickname(originalNickname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));

        if (findByNicknameUser.getNickname().equals(updateNickname)
                && findByNicknameUser.getEmail().equals(updateEmail)
                && findByNicknameUser.getPassword().equals(updatePassword)
                && findByNicknameUser.getUsername().equals(updateUsername)
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");
        }

        findByNicknameUser.setUsername(updateUsername);
        findByNicknameUser.setNickname(updateNickname);
        findByNicknameUser.setEmail(updateEmail);
        findByNicknameUser.setPassword(updatePassword);

        User updatedUser;

        try {
            updatedUser = userRepository.save(findByNicknameUser);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일 또는 닉네임입니다.");
        }

        HttpSession session = request.getSession(false);
        session.invalidate();
        session.setAttribute(Const.LOGIN_USER, updatedUser.getNickname());

        return new UserNicknameResponseDto(
                updatedUser.getNickname()
        );
    }

    public void withdrawal(String nickname, String password, HttpServletRequest request) {

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));

        if (!findByNicknameUser.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(findByNicknameUser);

        HttpSession session = request.getSession(false);
        session.invalidate();
    }

}
