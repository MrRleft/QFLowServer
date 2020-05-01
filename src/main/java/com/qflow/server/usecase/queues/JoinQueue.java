package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.GetUserByTokenDatabase;
import org.springframework.beans.factory.annotation.Autowired;

public class JoinQueue {

    private final JoinQueueDatabase joinQueueDatabase;
    private final GetUserByToken getUserByToken;
    public JoinQueue(JoinQueueDatabase joinQueueDatabase,
                     @Autowired GetUserByToken getUserByTokenDatabase) {
        this.joinQueueDatabase = joinQueueDatabase;
        this.getUserByToken = getUserByTokenDatabase;
    }

    public Queue execute(Integer idQueue, String userToken){
        User userByToken = getUserByToken.execute(userToken);
        return joinQueueDatabase.joinQueue(idQueue, userByToken.getId());
    }
}
