package com.qflow.server.usecase.users;

import com.qflow.server.entity.User;

public class CreateUser {

    private CreateUserDatabase createUserDatabase;

    public CreateUser(CreateUserDatabase createUserDatabase) {
        this.createUserDatabase = createUserDatabase;
    }

    public User execute(User user){
        return this.createUserDatabase.createUser(user);
    }

}
