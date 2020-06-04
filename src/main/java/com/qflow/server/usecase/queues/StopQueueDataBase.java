package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface StopQueueDataBase {
    Queue stopQueue(Queue queue);
}
