package com.qflow.server.usecase.users;

import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;

public class LoginUser {

    private LoginUserDatabase loginUserDatabase;

    public LoginUser(LoginUserDatabase loginUserDatabase) {
        this.loginUserDatabase = loginUserDatabase;
    }

    public User execute(boolean isAdmin, String mail, String password) throws LoginNotSuccesfulException {

        return loginUserDatabase.loginUser(isAdmin, mail, password);

    }
}
