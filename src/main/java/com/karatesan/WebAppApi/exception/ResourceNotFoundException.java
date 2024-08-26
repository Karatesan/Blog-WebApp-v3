package com.karatesan.WebAppApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


//TODO maybe change all exceptions handling those type of things to this one - to considertation
public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
