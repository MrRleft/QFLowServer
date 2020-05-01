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
                .build();

    }

    public Queue queuePostToQueue(QueuePost queuePost) {
        //TODO do this adapter
        return new Queue();
    }

    public List<Queue> queueDBListToQueueList(List<QueueDB> queueDBList) {
        List<Queue> queueList = new ArrayList<>();

        for(QueueDB queueDB : queueDBList){
            queueList.add(this.queueDBToQueue(queueDB));
        }

        return queueList;
    }
}
