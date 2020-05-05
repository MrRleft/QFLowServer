package com.qflow.server.entity.exceptions;

public class UserAlreadyInQueue extends RuntimeException {
    public UserAlreadyInQueue(String s) {
        super(s);
    }
}