package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;

public class CreateQueue {

    private final CreateQueueDatabase createQueueDatabase;

    public CreateQueue(CreateQueueDatabase createQueueDatabase) {
        this.createQueueDatabase = createQueueDatabase;
    }

    public Queue execute(Queue queue, String userToken){
        return createQueueDatabase.createQueue(queue, userToken);
    }

}
