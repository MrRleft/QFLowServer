package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("qflow/queues")
public class QueuesController {

    private final GetQueuesByUserId getQueuesByUserId;
    private final GetQueueByQueueId getQueueByQueueId;
    private final GetQueueByJoinId getQueueByJoinId;
    private final CreateQueue createQueue;
    private final JoinQueue joinQueue;
    private final QueueAdapter queueAdapter;



    public QueuesController(@Autowired final GetQueuesByUserId getQueuesByUserId,
                            @Autowired final GetQueueByQueueId getQueueByQueueId,
                            @Autowired final GetQueueByJoinId getQueueByJoinId,
                            @Autowired final CreateQueue createQueue,
                            @Autowired final JoinQueue joinQueue,
                            @Autowired final QueueAdapter queueAdapter) {
        this.getQueuesByUserId = getQueuesByUserId;
        this.getQueueByQueueId = getQueueByQueueId;
        this.getQueueByJoinId = getQueueByJoinId;
        this.createQueue = createQueue;
        this.joinQueue = joinQueue;
        this.queueAdapter = queueAdapter;
    }

    @GetMapping("/byIdUser")
    public ResponseEntity<List<Queue>> getQueuesByUserId(
            @RequestHeader(value = "token") final String token,
            @RequestParam(required = false) String expand,
            @RequestParam(required = false) Boolean finished) {
        return new ResponseEntity<>(
                this.getQueuesByUserId.execute(expand, token, finished), HttpStatus.OK);
    }

    @GetMapping("/byIdQueue/{idQueue}")
    public ResponseEntity<Queue> getQueueByQueueId(@PathVariable("idQueue") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueueByQueueId.execute(idQueue), HttpStatus.OK);
    }

    @GetMapping("/byIdJoin/{joinId}")
    public ResponseEntity<Queue> getQueueByJoinId(@PathVariable("joinId") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueueByJoinId.execute(idQueue), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> postQueue(
            @RequestBody @Valid final QueuePost queuePost,
            @RequestHeader (value = "token") final String token
    ) {
        createQueue.execute(queueAdapter.queuePostToQueue(queuePost), token);
        return new ResponseEntity<>("Queue created", HttpStatus.OK);
    }

    @PostMapping("/joinQueue/{join_id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Integer> joinQueue(
            @PathVariable("join_id") final int joinCode,
            @RequestHeader @Valid final String token
    ) {
        //TODO ver como hacer el id bien
        //QueueUser qu = new QueueUser();
        Integer idQueue = this.joinQueue.execute(joinCode, token);
        return new ResponseEntity<>(idQueue,
                HttpStatus.OK);
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