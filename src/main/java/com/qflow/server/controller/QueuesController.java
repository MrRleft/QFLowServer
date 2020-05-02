package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueuesByUserId;
import com.qflow.server.usecase.queues.GetQueueByQueueId;
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
    private final CreateQueue createQueue;
    private final QueueAdapter queueAdapter;



    public QueuesController(@Autowired final GetQueuesByUserId getQueuesByUserId,
                            @Autowired final GetQueueByQueueId getQueueByQueueId,
                            @Autowired final CreateQueue createQueue,
                            @Autowired final QueueAdapter queueAdapter) {
        this.getQueuesByUserId = getQueuesByUserId;
        this.getQueueByQueueId = getQueueByQueueId;
        this.createQueue = createQueue;
        this.queueAdapter = queueAdapter;
    }

    @GetMapping("/byIdUser/{token}")
    public ResponseEntity<List<Queue>> getQueuesByUserId(
            @PathVariable("token") final String token,
            @RequestParam(required = false) String expand,
            @RequestParam(required = false) boolean locked) {
        return new ResponseEntity<>(
                this.getQueuesByUserId.execute(expand, token, locked), HttpStatus.OK);
    }

    @GetMapping("/byIdQueue/{idQueue}")
    public ResponseEntity<Queue> getQueueByQueueId(@PathVariable("idQueue") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueueByQueueId.execute(idQueue), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Queue> postQueue(
            @RequestBody @Valid final QueuePost queuePost,
            @RequestHeader @Valid final String token
    ) {
        return new ResponseEntity<Queue>(
                this.createQueue.execute(
                        this.queueAdapter.queuePostToQueue(queuePost), token
                ), HttpStatus.CREATED);
    }
}
