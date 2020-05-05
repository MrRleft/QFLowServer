package com.qflow.server.entity.exceptions;

public class QueueuAlreadyExistsException extends RuntimeException {

    public QueueuAlreadyExistsException(final String message) {
        super(message);
    }

}