package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.users.GetUserByTokenDatabase;
import org.springframework.beans.factory.annotation.Autowired;

public class JoinQueue {

    private final JoinQueueDatabase joinQueueDatabase;
    private final GetUserByTokenDatabase getUserByTokenDatabase;
    public JoinQueue(JoinQueueDatabase joinQueueDatabase,
                     @Autowired GetUserByTokenDatabase getUserByTokenDatabase) {
        this.joinQueueDatabase = joinQueueDatabase;
        this.getUserByTokenDatabase = getUserByTokenDatabase;
    }

    //TODO revisar
    public Queue execute(Integer idQueue, String userToken){
        User userByToken = getUserByTokenDatabase.getUserByToken(userToken);
        userByToken.getId();
        return joinQueueDatabase.joinQueue(idQueue, userByToken.getId());
    }
}
