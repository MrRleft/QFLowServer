package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public interface JoinQueueDatabase {

    Integer joinQueue(Integer joinCode, Integer idUser);
}
