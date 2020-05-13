package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface GetQueueByQueueIdDatabase {
    Queue getQueueByQueueId(int idQueue);
}
