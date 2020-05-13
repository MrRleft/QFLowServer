package com.qflow.server.controller;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.controller.dto.UserDTO;
import com.qflow.server.controller.dto.UserPost;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.users.CreateUser;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("qflow/user")
public class UserController {

    private final GetUserByToken getUserByToken;
    private final LoginUser loginUser;
    private final CreateUser createUser;
    private final UserAdapter userAdapter;

    public UserController(@Autowired GetUserByToken getUserByToken,
                          @Autowired CreateUser createUser,
                          @Autowired UserAdapter userAdapter,
                          @Autowired LoginUser loginUser) {
        this.getUserByToken = getUserByToken;
        this.userAdapter = userAdapter;
        this.loginUser = loginUser;
        this.createUser = createUser;
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<User> getUserByToken(@PathVariable("token") final String token) {
        return new ResponseEntity<>(
                this.getUserByToken.execute(token), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> loginUser(
            @RequestHeader(value = "isAdmin") final boolean isAdmin,
            @RequestHeader(value = "mail") String mail,
            @RequestHeader(value = "password") String password) {

        mail = org.owasp.encoder.Encode.forJava(mail);
        password = org.owasp.encoder.Encode.forJava(password);

        return new ResponseEntity<>(
                userAdapter.userToUserDTO(this.loginUser.execute(isAdmin, mail, password)), HttpStatus.OK);

    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(
            @RequestHeader(value = "isAdmin") final boolean isAdmin,
            @Valid @RequestBody UserPost userPost) {

        User userToCreate = userAdapter.userPostToUser(userPost, isAdmin);

        return new ResponseEntity<>(userAdapter.userToUserDTO(createUser.execute(userToCreate)), HttpStatus.OK);

    }


}
