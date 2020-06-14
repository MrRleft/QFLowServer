package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface CloseQueueDataBase {
    Queue closeQueue(Queue queue);
}
