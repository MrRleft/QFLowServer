package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.users.GetUserByToken;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateQueue {

    private final CreateQueueDatabase createQueueDatabase;
    private final GetUserByToken getUserByToken;

    public CreateQueue(CreateQueueDatabase createQueueDatabase,
                       @Autowired final GetUserByToken getUserByToken) {
        this.createQueueDatabase = createQueueDatabase;
        this.getUserByToken = getUserByToken;
    }

    public Queue execute(Queue queue, String userToken){
        User u = getUserByToken.execute(userToken);
        return createQueueDatabase.createQueue(queue, u.getId());
    }
}
