package com.example.examplescheduleapp.exception;

import com.example.examplescheduleapp.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Validation 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        logger.warn("[VALIDATION WARN] {}", e.getMessage());

        List<String> errorDetails = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getStatusCode().toString(), "입력 값이 잘못되었습니다.", errorDetails));
    }

    // 일반적인 400번대 예외
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleResponseStatusException(ResponseStatusException e) {
        logger.warn("[WARN] {}", e.getMessage());

        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getStatusCode().toString(), e.getReason()));
    }

    // 서버 내부 오류 (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception e, HttpServletRequest request) {
        logger.error("[ERROR] {} {} - {}", request.getMethod(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(e.getMessage(), "서버 내부 오류가 발생했습니다."));
    }

}