package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.QueueUserAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.usecase.queues.CreateQueueDatabase;
import com.qflow.server.usecase.queues.GetQueueDatabase;
import com.qflow.server.usecase.queues.JoinQueueDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueueService implements GetQueueDatabase, CreateQueueDatabase, JoinQueueDatabase {


    final private QueueRepository queueRepository;
    final private QueueAdapter queueAdapter;
    final private QueueUserRepository queueUserRepository;
    //final private QueueUserAdapter queueUserAdapter;

    public QueueService(
            @Autowired final QueueRepository queueRepository,
            @Autowired final QueueAdapter queueAdapter,
            @Autowired final QueueUserRepository queueUserRepository) {
        this.queueRepository = queueRepository;
        this.queueAdapter = queueAdapter;
        this.queueUserRepository = queueUserRepository;
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
            //TODO check the id of queue_user
            /*
            QueueUserDB qu = new QueueUserDB(0,queue.getId(), userId);
            queueUserRepository.save(qu);
            */
        }else{
            throw new QueueuAlreadyExistsException("Queue with join Id: " + queue.getJoinId() + " already exists");
        }
    }

    @Override
    public Queue joinQueue(Integer idQueue, Integer idUser) {
        Optional<QueueDB> queueDBOptional = queueRepository.joinQueue(idQueue,idUser);
        if(!queueDBOptional.isPresent()){
            throw new QueueNotFoundException("Queue with id:  is full");
        }
        return queueAdapter.queueDBToQueue(queueDBOptional.get());
    }
}
