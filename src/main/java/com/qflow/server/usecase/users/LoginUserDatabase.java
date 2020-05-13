package com.qflow.server.usecase.users;

import com.qflow.server.entity.User;

public interface LoginUserDatabase {
    User loginUser(boolean isAdmin, String mail, String password);
}
