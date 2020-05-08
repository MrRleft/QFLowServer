package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.QueueUserAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.QueueUser;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.entity.exceptions.UserAlreadyInQueue;
import com.qflow.server.usecase.queues.CreateQueueDatabase;
import com.qflow.server.usecase.queues.GetQueueDatabase;
import com.qflow.server.usecase.queues.JoinQueueDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
            Random r = new Random();
            int rnd = r.nextInt(99999);
            while(queueRepository.findQueueByJoinId(rnd).isPresent()) {
                rnd = r.nextInt(99999);
            }
            queue.setJoinId(rnd);
            QueueDB aux = queueRepository.save(queueAdapter.queueToQueueDB(queue));
            //Consulta para obtener id de la cola recien creada

            //TODO check the id of queue_user
            QueueUserDB qu = new QueueUserDB(aux.getId(), userId);
            queueUserRepository.save(qu);
    }

    @Override
    public void joinQueue(Integer idQueue, Integer idUser) {
        Optional<QueueUserDB> queueUser = queueUserRepository.getUserInQueue(idUser, idQueue);
        if(!queueUser.isPresent()) {
            Integer pos = queueUserRepository.getLastPosition(idQueue);
            QueueUserDB queueUserDB = new QueueUserDB(idQueue, idUser, pos + 1);
            queueUserRepository.save(queueUserDB);
        }
        else{
            throw new UserAlreadyInQueue("User already in queue");
        }
    }
}
