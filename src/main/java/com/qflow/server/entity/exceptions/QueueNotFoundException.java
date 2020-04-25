package com.qflow.server.entity.exceptions;

import com.qflow.server.entity.Queue;

public class QueueNotFoundException extends RuntimeException {

    public QueueNotFoundException(final String message) {
        super(message);
    }

}
