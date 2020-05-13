package com.qflow.server.adapter;

import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
        return Queue.QueueBuilder.aQueue()
                .withBusinessAssociated(queuePost.getBusinessAssociated())
                .withCapacity(queuePost.getCapacity())
                .withDescription(queuePost.getDescription())
                .withName(queuePost.getName())
                .build();
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

    public List<Queue> queueDBListToQueueList(List<QueueDB> queueDBList) {
        List<Queue> queueList = new ArrayList<>();

        for(QueueDB queueDB : queueDBList){
            queueList.add(this.queueDBToQueue(queueDB));
        }

        return queueList;
    }
}
