package com.qflow.server.usecase.queues;

import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.GetUserByTokenDatabase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetQueuesByUserId {

    private GetQueuesByUserIdDatabase getQueuesByUserIdDatabase;
    private GetUserByToken getUserByToken;

    public GetQueuesByUserId(GetQueuesByUserIdDatabase getQueuesByUserIdDatabase, @Autowired GetUserByToken getUserByToken) {
        this.getQueuesByUserIdDatabase = getQueuesByUserIdDatabase;
        this.getUserByToken = getUserByToken;
    }

    public List<Queue> execute(String expand, String token, Boolean finished){

        User userReturned = getUserByToken.execute(token);

        return getQueuesByUserIdDatabase.getQueuesByUserId(expand , userReturned.getId(), finished);
    }
}