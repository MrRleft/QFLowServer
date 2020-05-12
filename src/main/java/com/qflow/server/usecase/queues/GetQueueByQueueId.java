package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class GetQueueByQueueId {

    private GetQueueByQueueIdDatabase getQueueByQueueIdDatabase;

    public GetQueueByQueueId(GetQueueByQueueIdDatabase getQueueByQueueIdDatabase) {
        this.getQueueByQueueIdDatabase = getQueueByQueueIdDatabase;
    }

    public Queue execute(int idQueue){
        return getQueueByQueueIdDatabase.getQueueByQueueId(idQueue);
    }
}