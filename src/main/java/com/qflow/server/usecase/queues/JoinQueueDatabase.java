package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface JoinQueueDatabase {

    void joinQueue(Integer queue, Integer idUser);
}
