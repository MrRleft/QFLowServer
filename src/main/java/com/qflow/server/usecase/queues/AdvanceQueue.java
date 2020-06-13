package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.users.GetUserByToken;

public class AdvanceQueue {

    private final AdvanceQueueDatabase advanceQueueDatabase;
    private final GetQueueByQueueId getQueueByQueueId;
    private final GetUserByToken getUserByToken;

    public AdvanceQueue(AdvanceQueueDatabase advanceQueueDatabase,
                        GetQueueByQueueId getQueuesByUserId,
                        GetUserByToken getUserByToken) {
        this.advanceQueueDatabase = advanceQueueDatabase;
        this.getQueueByQueueId = getQueuesByUserId;
        this.getUserByToken = getUserByToken;
    }

    public Queue execute(int id, String token){
        User user = this.getUserByToken.execute(token);
        advanceQueueDatabase.advanceQueue(id, user.getId());
        return getQueueByQueueId.execute(id);
    }
}
