package com.example.examplescheduleapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommonMethods {

    default void compareEqualsAndThrow(boolean comparator, HttpStatus badRequest, String reason) {
        if (comparator) { throw new ResponseStatusException(badRequest, reason); }
    }

}
