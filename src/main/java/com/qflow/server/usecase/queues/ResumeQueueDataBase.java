package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface ResumeQueueDataBase {
    Queue resumeQueue(Queue queue);
}
