package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueue;
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
    private final QueueAdapter queueAdapter;


    public QueuesController(@Autowired final GetQueue getQueue,
                            @Autowired final CreateQueue createQueue,
                            @Autowired final QueueAdapter queueAdapter) {
        this.getQueue = getQueue;
        this.createQueue = createQueue;
        this.queueAdapter = queueAdapter;
    }

    @GetMapping("/{idQueue}")
    public ResponseEntity<Queue> getQueue(@PathVariable("idQueue") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueue.execute(idQueue), HttpStatus.OK);
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
