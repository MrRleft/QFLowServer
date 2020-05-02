package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.usecase.queues.CreateQueueDatabase;
import com.qflow.server.usecase.queues.GetQueueByQueueIdDatabase;
import com.qflow.server.usecase.queues.GetQueuesByUserIdDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueService implements GetQueuesByUserIdDatabase, GetQueueByQueueIdDatabase, CreateQueueDatabase {


    final private QueueRepository queueRepository;
    final private QueueAdapter queueAdapter;

    public QueueService(
            @Autowired final QueueRepository queueRepository,
            @Autowired final QueueAdapter queueAdapter) {
        this.queueRepository = queueRepository;
        this.queueAdapter = queueAdapter;
    }

    @Override
    public List<Queue> getQueuesByUserId(String expand, int idUser, boolean locked) {

        Optional<List<QueueDB>> queueDBListOptional = null;
        if(expand != null)
        {
            if(expand.equalsIgnoreCase("all")){
                queueDBListOptional = queueRepository.getQueuesByUserId(idUser, locked);
            }
        }
        else{
            queueDBListOptional = queueRepository.getAllQueues(locked);
        }
        if(!queueDBListOptional.isPresent()){
            throw new QueueNotFoundException("Queues not found");
        }
        return queueAdapter.queueDBListToQueueList(queueDBListOptional.get());

    }

    @Override
    public Queue getQueueByQueueId(int idQueue) {
        Optional<QueueDB> queueDBOptional = queueRepository.findById(idQueue);
        if(!queueDBOptional.isPresent()){
            throw new QueueNotFoundException("Queue with id: " + idQueue + " not found");
        }
        return queueAdapter.queueDBToQueue(queueDBOptional.get());
    }

    @Override
    public Queue createQueue(Queue queue, String userToken) {
        //TODO add to queueToAdd queue using QueueAdapter
        QueueDB queueToAdd = null;
        queueRepository.save(queueToAdd);
        //TODO add corresponding data to Users-Queue
        return null;
    }

}
