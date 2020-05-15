package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.QueueUserAdapter;
import com.qflow.server.domain.repository.InfoUserQueueRepository;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.QueueUser;
import com.qflow.server.entity.exceptions.QueueFullException;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.entity.exceptions.UserAlreadyInQueue;
import com.qflow.server.usecase.queues.CreateQueueDatabase;
import com.qflow.server.usecase.queues.GetQueueByQueueIdDatabase;
import com.qflow.server.usecase.queues.GetQueuesByUserIdDatabase;
import com.qflow.server.usecase.queues.JoinQueueDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QueueService implements GetQueuesByUserIdDatabase, GetQueueByQueueIdDatabase, CreateQueueDatabase, JoinQueueDatabase {


    final private QueueRepository queueRepository;
    final private QueueAdapter queueAdapter;
    final private QueueUserRepository queueUserRepository;
    final private InfoUserQueueRepository infoUserQueueRepository;
    //final private QueueUserAdapter queueUserAdapter;

    public QueueService(
            @Autowired final QueueRepository queueRepository,
            @Autowired final QueueAdapter queueAdapter,
            @Autowired final QueueUserRepository queueUserRepository,
            @Autowired final InfoUserQueueRepository infoUserQueueRepository) {
        this.queueRepository = queueRepository;
        this.queueAdapter = queueAdapter;
        this.queueUserRepository = queueUserRepository;
        this.infoUserQueueRepository = infoUserQueueRepository;
    }

    @Override
    public List<Queue> getQueuesByUserId(String expand, int idUser, Boolean locked) {

        Optional<List<QueueDB>> queueDBListOptional = null;
        if(expand != null) {
            if(expand.equals("all")) {
                if (locked == null) {   //Showing all queues with locked as true or false
                    queueDBListOptional = queueRepository.getAllQueues();
                } else{  //Fetching all queues by locked status
                    queueDBListOptional = queueRepository.getQueuesByLocked(locked);
                }
            }else if(expand.equalsIgnoreCase("alluser")){
                queueDBListOptional = queueRepository.getAllQueuesByUserId(idUser);
            }else{
                throw new QueueNotFoundException("expand value not identified");
            }
        }else{  //Fetching queues that the user's in with locked as true or false (expand can be null)
            queueDBListOptional = queueRepository.getQueuesByUserIdLocked(idUser, locked);
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
    public void createQueue(Queue queue, int userId) {
            Random r = new Random();
            Integer rnd = r.nextInt(99999);
            while(queueRepository.findQueueByJoinId(rnd).isPresent()) {
                rnd = r.nextInt();
            }
            queue.setJoinId(rnd);
            Timestamp timestamp = new Timestamp(new Date().getTime());
            queue.setDateCreated(timestamp);
            queue.setIsLocked(true);
            queue.setCurrentPos(1);
            QueueDB aux = queueRepository.save(queueAdapter.queueToQueueDB(queue));

            //TODO check the id of queue_user
            QueueUserDB qu = new QueueUserDB(aux.getId(), userId);
            queueUserRepository.save(qu);
    }


    @Override
    public void joinQueue(Integer joinCode, Integer idUser) {
        Integer idQueue = queueRepository.getIdQueueByJoinId(joinCode);
        Optional<QueueUserDB> queueUser = queueUserRepository.getUserInQueue(idUser, idQueue);
        Optional<QueueUserDB> infoUserQueue = infoUserQueueRepository.getUserInInfoUserQueue(idUser, idQueue);
        Integer capacity = queueRepository.getCapacity(idQueue);
        Integer numQueues = queueUserRepository.numActiveQueues(idQueue);
        if(capacity > numQueues) {
            if (!queueUser.isPresent() && !infoUserQueue.isPresent()) {
                Integer pos = queueUserRepository.getLastPosition(idQueue);
                QueueUserDB queueUserDB = new QueueUserDB(idQueue, idUser, pos + 1);
                QueueUserDB queueUserDBInput = queueUserRepository.save(queueUserDB);
                InfoUserQueueDB infoUserQueueDB = new InfoUserQueueDB(idQueue, idUser);
                infoUserQueueRepository.save(infoUserQueueDB);
            } else {
                throw new UserAlreadyInQueue("User already in queue");
            }
        }
        else {
            throw new QueueFullException("Queue full");
        }
    }

}
