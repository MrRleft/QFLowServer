package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class GetQueueByJoinId {

    private GetQueueByJoinIdDatabase getQueueByJoinIdDatabase;

    public GetQueueByJoinId(GetQueueByJoinIdDatabase getQueueByJoinIdDatabase) {
        this.getQueueByJoinIdDatabase = getQueueByJoinIdDatabase;
    }

    public Queue execute(int joinId){
        return getQueueByJoinIdDatabase.getQueueByJoinId(joinId); }
}