package com.qflow.server.adapter;

import com.qflow.server.domain.repository.dto.UserDB;
import com.qflow.server.entity.User;

public class UserAdapter {

    public UserAdapter() {
    }

    public User userDBToUser(UserDB userDB){
        return User.UserBuilder.anUser()
                .withId(userDB.getId())
                .withEmail(userDB.getEmail())
                .withIsAdmin(userDB.getAdmin())
                .withNameLastname(userDB.getNameLastname())
                .withPassword(userDB.getPassword())
                .withProfilePicture(userDB.getProfilePicture())
                .withToken(userDB.getToken())
                .withUsername(userDB.getUsername())
                .build();
    }
}
