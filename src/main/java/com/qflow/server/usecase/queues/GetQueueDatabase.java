package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface GetQueueDatabase {
    Queue getQueue(int idQueue);
}
