package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

import java.util.List;

public interface GetQueuesByUserIdDatabase {
    List<Queue> getQueuesByUserId(String expand, int idUser, Boolean locked);
}
