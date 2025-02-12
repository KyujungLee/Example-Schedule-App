package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.PasswordEncoder;
import com.example.examplescheduleapp.dto.response.UserInformationResponseDto;
import com.example.examplescheduleapp.dto.response.UserLoginResponseDto;
import com.example.examplescheduleapp.dto.response.UserUpdateResponseDto;
import com.example.examplescheduleapp.dto.response.UserSignUpResponseDto;
import com.example.examplescheduleapp.entity.User;
import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService implements CommonMethods {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignUpResponseDto signUp(String username, String nickname, String email, String password, HttpServletRequest request) {

        checkDuplicationsOfUniqueValue(nickname, email);

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, nickname, email, encodedPassword);
        User savedUser = userRepository.save(user);

        initializedSession(request, savedUser);

        return new UserSignUpResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password, HttpServletRequest request) {

        User loginUser = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        ifTrueThenThrow(!passwordEncoder.matches(password, loginUser.getPassword()), HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        initializedSession(request, loginUser);

        return new UserLoginResponseDto(loginUser);
    }

    public void logout(HttpServletRequest request) {

        request.getSession().invalidate();
    }

    @Transactional(readOnly = true)
    public UserInformationResponseDto findInformation(HttpServletRequest request) {

        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        User findByNicknameUser = userRepository.findByNickname(nickname).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        return new UserInformationResponseDto(findByNicknameUser);
    }

    @Transactional
    public UserUpdateResponseDto update(String updateUsername, String updateNickname, String updateEmail, String updatePassword, String oldPassword, HttpServletRequest request) {

        // checkAuthorizationAtUser
        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        User findByNicknameUser = userRepository.findByNickname(nickname).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        ifTrueThenThrow(!passwordEncoder.matches(oldPassword, findByNicknameUser.getPassword()), HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        checkDuplicationsOfUniqueValue(updateNickname, updateEmail);

        ifTrueThenThrow(isEqualsToDB(updateUsername,updateNickname,updateEmail,updatePassword,findByNicknameUser), HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");

        String encodedPassword = passwordEncoder.encode(updatePassword);
        findByNicknameUser.updateInformation(updateUsername, updateNickname, updateEmail, encodedPassword);
        User updatedUser = userRepository.save(findByNicknameUser);

        initializedSession(request, updatedUser);

        return new UserUpdateResponseDto(updatedUser);
    }

    @Transactional
    public void withdrawal(String password, HttpServletRequest request) {

        // checkAuthorizationAtUser
        String nickname = request.getSession().getAttribute(Const.LOGIN_USER).toString();
        User findByNicknameUser = userRepository.findByNickname(nickname).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        ifTrueThenThrow(!passwordEncoder.matches(password, findByNicknameUser.getPassword()), HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

        userRepository.delete(findByNicknameUser);
        request.getSession().invalidate();
    }


    private void checkDuplicationsOfUniqueValue(String nickname, String email) {
        ifTrueThenThrow(userRepository.existsByNickname(nickname), HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다.");
        ifTrueThenThrow(userRepository.existsByEmail(email), HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
    }

    private boolean isEqualsToDB(String updateUsername, String updateNickname, String updateEmail, String updatePassword, User findByNicknameUser) {
        return findByNicknameUser.getNickname().equals(updateNickname)
                && findByNicknameUser.getEmail().equals(updateEmail)
                && passwordEncoder.matches(findByNicknameUser.getPassword(), updatePassword)
                && findByNicknameUser.getUsername().equals(updateUsername);
    }

    private void initializedSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(Const.LOGIN_USER, user.getNickname());
    }

}
