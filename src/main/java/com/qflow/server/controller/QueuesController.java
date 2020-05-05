package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.QueueUser;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueue;
import com.qflow.server.usecase.queues.JoinQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("qflow/queues")
public class QueuesController {

    private final GetQueue getQueue;
    private final CreateQueue createQueue;
    private final JoinQueue joinQueue;
    private final QueueAdapter queueAdapter;


    public QueuesController(@Autowired final GetQueue getQueue,
                            @Autowired final CreateQueue createQueue,
                            @Autowired final JoinQueue joinQueue,
                            @Autowired final QueueAdapter queueAdapter) {
        this.getQueue = getQueue;
        this.createQueue = createQueue;
        this.joinQueue = joinQueue;
        this.queueAdapter = queueAdapter;
    }

    @GetMapping("/{idQueue}")
    public ResponseEntity<Queue> getQueue(@PathVariable("idQueue") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueue.execute(idQueue), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> postQueue(
            @RequestBody @Valid final QueuePost queuePost,
            @RequestHeader (value = "token") final String token
    ) {
        createQueue.execute(queueAdapter.queuePostToQueue(queuePost), token);
        return new ResponseEntity<>("Queue created", HttpStatus.OK);
    }

    @PostMapping("/joinQueue/{idQueue}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> joinQueue(
            @PathVariable("idQueue") final int idQueue,
            @RequestHeader @Valid final String token
    ) {
        //TODO ver como hacer el id bien
        //QueueUser qu = new QueueUser();
        this.joinQueue.execute(idQueue, token);
        return new ResponseEntity<>("Queue joined", HttpStatus.OK);
    }
}

/*
@PostMapping("/")
public ResponseEntity<String> loginUser(
        @RequestHeader(value = "isAdmin") final boolean isAdmin,
        @Valid @RequestBody UserPost userPost) {
    User userToCreate = userAdapter.userPostToUser(userPost, isAdmin);
    createUser.execute(userToCreate);
    return new ResponseEntity<>("User created", HttpStatus.OK);
}
* */