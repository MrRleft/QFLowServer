package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import org.springframework.beans.factory.annotation.Autowired;

public class ResumeQueue {
    private final ResumeQueueDataBase resumeQueueDataBase;
    private final GetQueueByQueueId getQueueByQueueId;

    public ResumeQueue(ResumeQueueDataBase resumeQueueDataBase,
                     @Autowired GetQueueByQueueId getQueueByQueueId) {
        this.getQueueByQueueId = getQueueByQueueId;
        this.resumeQueueDataBase = resumeQueueDataBase;
    }

    public Queue execute(int idQueue){
        Queue queue = getQueueByQueueId.execute(idQueue);
        return resumeQueueDataBase.resumeQueue(queue);
    }
}
