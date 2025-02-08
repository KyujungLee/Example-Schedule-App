package com.example.examplescheduleapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

    @ExceptionHandler({ UnauthorizedException.class, InvalidPasswordException.class, UserNotFoundException.class })
    public ResponseEntity<String> handleUnauthorized(Exception e, HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage() + " 다시 로그인 해주세요.");
    }

}