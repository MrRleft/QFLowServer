package com.qflow.server.usecase.users;

import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;

public interface LoginUserDatabase {
    String loginUser(boolean isAdmin, String mail, String password) throws LoginNotSuccesfulException;
}
