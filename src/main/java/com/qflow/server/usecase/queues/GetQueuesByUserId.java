package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class GetQueuesByUserId {

    private GetQueuesByUserIdDatabase getQueuesByUserIdDatabase;

    public GetQueuesByUserId(GetQueuesByUserIdDatabase getQueuesByUserIdDatabase) {
        this.getQueuesByUserIdDatabase = getQueuesByUserIdDatabase;
    }

    public Queue execute(String expand, int idUser, boolean locked){
        return getQueuesByUserIdDatabase.getQueuesByUserId(expand , idUser, locked);
    }
}