package com.karatesan.WebAppApi.exception;

public class RoleDoesNotExistException extends RuntimeException{

    public RoleDoesNotExistException(String message){
        super(message);
    }
}
