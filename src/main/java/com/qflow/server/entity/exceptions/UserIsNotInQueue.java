package com.qflow.server.entity.exceptions;

public class UserIsNotInQueue extends RuntimeException{

    public UserIsNotInQueue(final String message) {
        super(message);
    }

}