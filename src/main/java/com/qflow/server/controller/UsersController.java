package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.User;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueue;
import com.qflow.server.usecase.users.GetUserByToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("qflow/users")
public class UsersController {

    private final GetUserByToken getUserByToken;
    private final UserAdapter userAdapter;

    public UsersController(@Autowired final GetUserByToken getUserByToken,
                            @Autowired final UserAdapter userAdapter) {
        this.getUserByToken = getUserByToken;
        this.userAdapter = userAdapter;
    }

    @GetMapping("/{token}")
    public ResponseEntity<User> getUserByToken(@PathVariable("token") final String token) {
        return new ResponseEntity<>(
                this.getUserByToken.execute(token), HttpStatus.OK);
    }
}
