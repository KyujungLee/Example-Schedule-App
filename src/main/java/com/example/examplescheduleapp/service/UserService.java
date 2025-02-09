package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.PasswordEncoder;
import com.example.examplescheduleapp.dto.response.UserFindByNicknameResponseDto;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignUpResponseDto signUp(String username, String nickname, String email, String password, HttpServletRequest request) {

        if (userRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이미 사용중인 이메일입니다.");
        } else if (userRepository.existsByNickname(nickname)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, nickname, email, encodedPassword);

        User savedUser = userRepository.save(user);

        initializedSession(request, savedUser);

        return new UserSignUpResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password, HttpServletRequest request) {

        User verifiedUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(password, verifiedUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        initializedSession(request, verifiedUser);

        return new UserLoginResponseDto(verifiedUser);
    }

    @Transactional(readOnly = true)
    public UserFindByNicknameResponseDto findByNickname(String nickname) {

        User findByNicknameUser = getFindByNicknameUser(nickname);

        return new UserFindByNicknameResponseDto(findByNicknameUser);
    }

    @Transactional
    public UserUpdateResponseDto update(String updateUsername, String updateNickname, String updateEmail, String updatePassword, HttpServletRequest request) {

        String originalNickname = request.getSession(false).getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = getFindByNicknameUser(originalNickname);

        if (isEqualsToDB(updateUsername, updateNickname, updateEmail, updatePassword, findByNicknameUser)
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경할 내용이 없습니다.");
        }

        if (userRepository.existsByNickname(updateNickname) || userRepository.existsByEmail(updateEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일 또는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(updatePassword);

        findByNicknameUser.updateInformation(updateUsername, updateNickname, updateEmail, encodedPassword);

        User updatedUser = userRepository.save(findByNicknameUser);

        initializedSession(request, updatedUser);

        return new UserUpdateResponseDto(updatedUser);
    }


    @Transactional
    public void withdrawal(String password, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String nickname = session.getAttribute(Const.LOGIN_USER).toString();

        User findByNicknameUser = getFindByNicknameUser(nickname);

        if (!findByNicknameUser.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        session.invalidate();

        userRepository.delete(findByNicknameUser);
    }


    private void initializedSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(Const.LOGIN_USER, user.getNickname());
    }

    private boolean isEqualsToDB(String updateUsername, String updateNickname, String updateEmail, String updatePassword, User findByNicknameUser) {
        return findByNicknameUser.getNickname().equals(updateNickname)
                && findByNicknameUser.getEmail().equals(updateEmail)
                && passwordEncoder.matches(updatePassword, findByNicknameUser.getPassword())
                && findByNicknameUser.getUsername().equals(updateUsername);
    }

    private User getFindByNicknameUser(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."));
    }

}
