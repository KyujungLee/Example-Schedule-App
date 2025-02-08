package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.dto.response.UserFindByNicknameResponseDto;
import com.example.examplescheduleapp.dto.response.UserLoginResponseDto;
import com.example.examplescheduleapp.dto.response.UserUpdateResponseDto;
import com.example.examplescheduleapp.dto.response.UserSignUpResponseDto;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.exception.InvalidPasswordException;
import com.example.examplescheduleapp.exception.InvalidEmailException;
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


    @Transactional
    public UserSignUpResponseDto signUp(String username, String nickname, String email, String password, HttpServletRequest request) {

        if (userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("이미 사용중인 이메일입니다.");
        } else if (userRepository.existsByNickname(nickname)) {
            throw new DataIntegrityViolationException("이미 사용중인 닉네임입니다.");
        }

        User user = new User(username, nickname, email, password);

        User savedUser = userRepository.save(user);

        sessionInitialize(request, savedUser);

        return new UserSignUpResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password, HttpServletRequest request) {

        User verifiedUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new InvalidEmailException("존재하지 않는 이메일입니다."));
        if (!verifiedUser.getPassword().equals(password)){
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        sessionInitialize(request, verifiedUser);

        return new UserLoginResponseDto(verifiedUser);
    }

    @Transactional(readOnly = true)
    public UserFindByNicknameResponseDto findByNickname(String nickname) {

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        return new UserFindByNicknameResponseDto(findByNicknameUser);
    }

    @Transactional
    public UserUpdateResponseDto update(String updateUsername, String updateNickname, String updateEmail, String updatePassword, HttpServletRequest request) {

        String originalNickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = userRepository.findByNickname(originalNickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));

        if (findByNicknameUser.getNickname().equals(updateNickname)
                && findByNicknameUser.getEmail().equals(updateEmail)
                && findByNicknameUser.getPassword().equals(updatePassword)
                && findByNicknameUser.getUsername().equals(updateUsername)
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");
        }

        if (userRepository.existsByNickname(updateNickname) || userRepository.existsByEmail(updateEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일 또는 닉네임입니다.");
        }

        findByNicknameUser.updateInformation(updateUsername, updateNickname, updateEmail, updatePassword);

        User updatedUser = userRepository.save(findByNicknameUser);

        sessionInitialize(request, updatedUser);

        return new UserUpdateResponseDto(updatedUser);
    }

    @Transactional(readOnly = true)
    public void withdrawal(String nickname, String password, HttpServletRequest request) {

        User findByNicknameUser = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));;

        if (!findByNicknameUser.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        userRepository.delete(findByNicknameUser);
    }


    private void sessionInitialize(HttpServletRequest request, User user) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null){
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(Const.LOGIN_USER, user.getNickname());
    }

}
