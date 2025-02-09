package com.example.examplescheduleapp.service;

import com.example.examplescheduleapp.config.Const;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface Check {

    default void checkAuthorization(String nickname, HttpServletRequest request) {
        if (!nickname.equals(request.getSession().getAttribute(Const.LOGIN_USER).toString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
    }

}
