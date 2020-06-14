package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import org.springframework.beans.factory.annotation.Autowired;

public class CloseQueue {
    private final CloseQueueDataBase closeQueueDatabase;
    private final GetQueueByQueueId getQueueByQueueId;

    public CloseQueue(CloseQueueDataBase closeQueueDatabase,
                     @Autowired GetQueueByQueueId getQueueByQueueId) {
        this.getQueueByQueueId = getQueueByQueueId;
        this.closeQueueDatabase = closeQueueDatabase;
    }

    public Queue execute(int idQueue) {
        Queue queue = getQueueByQueueId.execute(idQueue);
        return closeQueueDatabase.closeQueue(queue);
    }
}
