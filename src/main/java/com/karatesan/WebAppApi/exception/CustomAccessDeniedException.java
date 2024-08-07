package com.karatesan.WebAppApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomAccessDeniedException extends ResponseStatusException {

    public CustomAccessDeniedException(){
        super(HttpStatus.FORBIDDEN, "Access Denied: You do not have the necessary permissions to access this resource.");
    }
}
