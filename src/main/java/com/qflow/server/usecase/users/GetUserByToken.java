package com.qflow.server.usecase.users;

import com.qflow.server.entity.User;

public class GetUserByToken {

    private GetUserByTokenDatabase getUserByTokenDatabase;

    public GetUserByToken(GetUserByTokenDatabase getUserByTokenDatabase){
        this.getUserByTokenDatabase = getUserByTokenDatabase;
    }

    public User execute(String token){
        return getUserByTokenDatabase.getUserByToken(token);
    }
}
