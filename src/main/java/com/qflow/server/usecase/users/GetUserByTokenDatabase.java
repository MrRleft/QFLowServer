package com.qflow.server.usecase.users;

import com.qflow.server.entity.User;

public interface GetUserByTokenDatabase {
    User getUserByToken(String token);
}
