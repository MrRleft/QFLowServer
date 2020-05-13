package com.qflow.server.adapter;

import com.qflow.server.controller.dto.UserDTO;
import com.qflow.server.controller.dto.UserPost;
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

    public User userPostToUser(UserPost userPost, boolean isAdmin) {
        return User.UserBuilder.anUser()
                .withEmail(userPost.getEmail())
                .withIsAdmin(isAdmin)
                .withNameLastname(userPost.getNameLastName())
                .withPassword(userPost.getPassword())
                .withUsername(userPost.getUsername())
                .build();
    }

    public UserDTO userToUserDTO(User execute) {

        return UserDTO.UserDTOBuilder.anUserDTO()
                .email(execute.getEmail())
                .token(execute.getToken())
                .username(execute.getUsername())
                .build();

    }
}
