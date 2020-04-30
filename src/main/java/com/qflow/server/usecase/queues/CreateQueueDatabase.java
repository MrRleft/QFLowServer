package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface CreateQueueDatabase {
    void createQueue(Queue queue, int userId);
}
