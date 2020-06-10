package com.qflow.server.entity.exceptions;

public class QueueLockedException extends RuntimeException {

    public QueueLockedException(final String message) {
        super(message);
    }

}
