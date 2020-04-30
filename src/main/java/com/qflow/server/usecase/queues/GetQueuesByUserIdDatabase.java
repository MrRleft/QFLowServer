package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface GetQueuesByUserIdDatabase {
    Queue getQueuesByUserId(String expand, int idUser, boolean locked);
}
