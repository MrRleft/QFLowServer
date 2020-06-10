package com.qflow.server.entity.exceptions;

public class UserNotInQueueException  extends RuntimeException{

    public UserNotInQueueException(final String message) {
        super(message);
    }

}