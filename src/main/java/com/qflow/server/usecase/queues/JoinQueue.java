package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class JoinQueue {

    private final JoinQueueDatabase joinQueueDatabase;

    public JoinQueue(CreateQueueDatabase createQueueDatabase) {
        this.joinQueueDatabase = joinQueueDatabase;
    }

    public Queue execute(Queue queue, String userToken){
        return joinQueueDatabase.joinQueue(queue, userToken);
    }
}
