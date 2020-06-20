package com.qflow.server.entity.exceptions;

public class UserIsNotAdminException extends RuntimeException{

    public UserIsNotAdminException(final String message) {
        super(message);
    }

}