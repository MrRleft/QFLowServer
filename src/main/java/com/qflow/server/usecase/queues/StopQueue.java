package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import org.springframework.beans.factory.annotation.Autowired;

public class StopQueue {
    private final StopQueueDataBase stopQueueDatabase;
    private final GetQueueByQueueId getQueueByQueueId;

    public StopQueue(StopQueueDataBase stopQueueDatabase,
                     @Autowired GetQueueByQueueId getQueueByQueueId) {
        this.getQueueByQueueId = getQueueByQueueId;
        this.stopQueueDatabase = stopQueueDatabase;
    }

    public Queue execute(int idQueue){
        Queue queue = getQueueByQueueId.execute(idQueue);
        return stopQueueDatabase.stopQueue(queue);
    }
}
