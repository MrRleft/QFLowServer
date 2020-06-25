package com.qflow.server.entity.exceptions;

public class EmptyQueueListException extends RuntimeException {

    public EmptyQueueListException(final String message) {
        super(message);
    }

}
