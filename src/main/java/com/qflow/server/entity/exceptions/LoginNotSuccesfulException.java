package com.qflow.server.entity.exceptions;

public class LoginNotSuccesfulException extends RuntimeException {

    public LoginNotSuccesfulException(final String message) {
        super(message);
    }

}