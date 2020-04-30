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
                .withBusinessAssociated(queueDB.getBusinessAssociated())
                .build();

    }

    public Queue queuePostToQueue(QueuePost queuePost) {
        //TODO do this adapter
        return new Queue();
    }

    public QueueDB queueToQueueDB(Queue queue)
    {
        return QueueDB.QueueDBBuilder.aQueueDB()
                .withBusinessAssociated(queue.getBusinessAssociated())
                .withCapacity(queue.getCapacity())
                .withCurrentPos(queue.getCurrentPos())
                .withDateCreated(queue.getDateCreated())
                .withDateFinished(queue.getDateFinished())
                .withDescription(queue.getDescription())
                .withId(queue.getId())
                .withIsLocked(queue.getLock())
                .withJoinId(queue.getJoinId())
                .withName(queue.getName())
                .build();
    }
}
