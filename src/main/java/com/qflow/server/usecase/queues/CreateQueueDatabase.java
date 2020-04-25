package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface CreateQueueDatabase {
    Queue createQueue(Queue queue, String userToken);
}
