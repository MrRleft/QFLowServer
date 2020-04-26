package com.qflow.server.adapter;

import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;

import java.time.Instant;

public class QueueAdapter {

    public QueueAdapter() {
    }

    public Queue queueDBToQueue(QueueDB queueDB){
        return Queue.QueueBuilder.aQueue()
                .withCapacity(queueDB.getCapacity())
                .withCurrentPos(queueDB.getCurrentPos())
                .withDateCreated(queueDB.getDateCreated())
                .withDateFinished(queueDB.getDateFinished())
                .withDescription(queueDB.getDescription())
                .withIsLock(queueDB.getLocked())
                .withJoinId(queueDB.getJoinId())
                .withName(queueDB.getName())
                .withId(queueDB.getId())
                .build();

    }

    public Queue queuePostToQueue(QueuePost queuePost) {
        //TODO do this adapter
        return new Queue();
    }
}
