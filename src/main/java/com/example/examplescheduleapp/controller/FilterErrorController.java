package com.example.examplescheduleapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/error")
public class FilterErrorController {

    @RequestMapping("/auth")
    public void handleUnauthorizedException(HttpServletRequest request) {
        throw (ResponseStatusException) request.getAttribute("exception");
    }

    @RequestMapping("/internal")
    public void handleInternalException(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute("exception");
    }

}
