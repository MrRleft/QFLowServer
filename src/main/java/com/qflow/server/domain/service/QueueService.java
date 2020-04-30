package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
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
    public void createQueue(Queue queue, int userId) {

        if(!queueRepository.findQueueByJoinId(queue.getJoinId()).isPresent()){
            queueRepository.save(queueAdapter.queueToQueueDB(queue));
        }else{
            throw new QueueuAlreadyExistsException("Queue with join Id: " + queue.getJoinId() + " already exists");
        }
    }


}
