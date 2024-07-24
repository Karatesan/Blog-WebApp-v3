package com.karatesan.WebAppApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE = "User not found";


    public UserNotFoundException(){
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }
}
