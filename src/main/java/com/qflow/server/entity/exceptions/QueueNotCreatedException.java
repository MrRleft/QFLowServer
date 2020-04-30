package com.qflow.server.entity.exceptions;

public class QueueNotCreatedException extends RuntimeException {

    public QueueNotCreatedException(final String message) {
        super(message);
    }

}