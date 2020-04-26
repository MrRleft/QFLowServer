package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.usecase.queues.CreateQueueDatabase;
import com.qflow.server.usecase.queues.GetQueueDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService implements GetQueueDatabase, CreateQueueDatabase {


    final private QueueRepository queueRepository;
    final private QueueAdapter queueAdapter;

    public QueueService(
            @Autowired final QueueRepository queueRepository,
            @Autowired final QueueAdapter queueAdapter) {
        this.queueRepository = queueRepository;
        this.queueAdapter = queueAdapter;
    }

    @Override
    public Queue getQueue(int idQueue) {

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
