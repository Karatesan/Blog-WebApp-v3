package com.karatesan.WebAppApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ActivateAccountException extends ResponseStatusException {


    private static final long serialVersionUID = 7439642984069939024L;
    private static final String DEFAULT_MESSAGE = "Activation failure: user already activated or link expired";

    public ActivateAccountException(String message){
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public ActivateAccountException(){
        this(DEFAULT_MESSAGE);
    }
}
