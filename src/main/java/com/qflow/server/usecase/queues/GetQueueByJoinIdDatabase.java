package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface GetQueueByJoinIdDatabase {
    Queue getQueueByJoinId(int joinId);
}
