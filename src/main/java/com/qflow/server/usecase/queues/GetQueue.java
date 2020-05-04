package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class GetQueue {

    private GetQueueDatabase getQueueDatabase;

    public GetQueue(GetQueueDatabase getQueueDatabase) {
        this.getQueueDatabase = getQueueDatabase;
    }

    public Queue execute(int idQueue){
        return getQueueDatabase.getQueue(idQueue);
    }
}
