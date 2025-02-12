package com.example.examplescheduleapp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponseDto {

    private String status;
    private String reason;
    private List<String> details;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponseDto(String status, String reason, List<String> details) {
        this.status = status;
        this.reason = reason;
        this.details = details;
    }

    public ErrorResponseDto(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }
}
