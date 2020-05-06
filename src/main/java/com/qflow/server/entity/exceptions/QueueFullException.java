package com.qflow.server.entity.exceptions;

public class QueueFullException extends RuntimeException {

    public QueueFullException(final String message) {
        super(message);
    }

}
