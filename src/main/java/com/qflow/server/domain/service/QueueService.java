package com.qflow.server.domain.service;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.ActivePeriodRepository;
import com.qflow.server.domain.repository.InfoUserQueueRepository;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.ActivePeriodDB;
import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.ActivePeriod;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueFullException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.entity.exceptions.UserAlreadyInQueue;
import com.qflow.server.usecase.queues.*;
import net.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class QueueService implements GetQueuesByUserIdDatabase, GetQueueByQueueIdDatabase, GetQueueByJoinIdDatabase,
        CreateQueueDatabase, JoinQueueDatabase, StopQueueDataBase, ResumeQueueDataBase, CloseQueueDataBase {


    final private QueueRepository queueRepository;
    final private QueueAdapter queueAdapter;
    final private QueueUserRepository queueUserRepository;
    final private InfoUserQueueRepository infoUserQueueRepository;
    final private ActivePeriodRepository activePeriodRepository;

    public QueueService(
            @Autowired final QueueRepository queueRepository,
            @Autowired final QueueAdapter queueAdapter,
            @Autowired final QueueUserRepository queueUserRepository,
            @Autowired final InfoUserQueueRepository infoUserQueueRepository,
            @Autowired final ActivePeriodRepository activePeriodRepository) {
        this.queueRepository = queueRepository;
        this.queueAdapter = queueAdapter;
        this.queueUserRepository = queueUserRepository;
        this.infoUserQueueRepository = infoUserQueueRepository;
        this.activePeriodRepository = activePeriodRepository;
    }

    @Override
    public List<Queue> getQueuesByUserId(String expand, int idUser, Boolean finished) {
        Optional<List<QueueDB>> queueDBListOptional = null;

        if(finished == null){    //Me da igual que hayan acabado o no -> AllFromUser, allQueues
            if(expand.equalsIgnoreCase("alluser"))   //All queues from the user
                queueDBListOptional = queueRepository.getAllQueuesByUserId(idUser);
            else if(expand.equalsIgnoreCase("all"))
                queueDBListOptional = queueRepository.getAllQueues();
            else
                throw new QueueNotFoundException("expand value not identified");
        }else if(finished)
            queueDBListOptional = queueRepository.getQueuesByUserIdFinished(idUser);
        else
            queueDBListOptional = queueRepository.getQueuesByUserIdNotFinished(idUser);

        if(!queueDBListOptional.isPresent()){
            throw new QueueNotFoundException("Queues not found");
        }

        List<Queue> queueList = new ArrayList<>();
        for(QueueDB queueDB : queueDBListOptional.get()){
            Queue ret = queueAdapter.queueDBToQueue(queueDB);

            Optional<QueueUserDB> quDB = queueUserRepository.getUserInQueue(idUser, ret.getId());
            if(quDB.isPresent()) {
                int posUser = quDB.get().getPosition();
                int numPersons  = ret.getCurrentPos();
                ret.setInFrontOfUser(quDB.get().getPosition() - ret.getCurrentPos());
            }
            else
                ret.setInFrontOfUser(-1);

            ret.setNumPersons(queueDB.getCurrentPos());
            queueList.add(ret);
        }

        return queueList;
    }

    @Override
    public Queue getQueueByQueueId(int idQueue) {
        Optional<QueueDB> queueDBOptional = queueRepository.findById(idQueue);
        if(!queueDBOptional.isPresent()){
            throw new QueueNotFoundException("Queue with id: " + idQueue + " not found");
        }
        Queue ret = queueAdapter.queueDBToQueue(queueDBOptional.get());

        ret.setNumPersons(queueUserRepository.numPersonsInQueue(idQueue));
        return ret;
    }

    @Override
    public Queue getQueueByJoinId(int joinId) {
        Optional<QueueDB> queueDBOptional = null;
        Integer idQueue =  queueRepository.getIdQueueByJoinId(joinId);

        if(idQueue == null)
            throw new QueueNotFoundException("Queue with id: " + idQueue + " not found");
        else
            queueDBOptional = queueRepository.findById(idQueue);

        Queue ret = queueAdapter.queueDBToQueue(queueDBOptional.get());

        ret.setNumPersons(queueUserRepository.numPersonsInQueue(idQueue));
        return ret;
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
            queue.setNumPersons(0);
            QueueDB aux = queueRepository.save(queueAdapter.queueToQueueDB(queue));

            //TODO check the id of queue_user
            QueueUserDB qu = new QueueUserDB(aux.getId(), userId);
            queueUserRepository.save(qu);
    }


    @Override
    public Integer joinQueue(Integer joinCode, Integer idUser) {
        Integer idQueue = queueRepository.getIdQueueByJoinId(joinCode);
        Optional<QueueUserDB> queueUser = queueUserRepository.getUserInQueue(idUser, idQueue);
        Optional<InfoUserQueueDB> infoUserQueue = infoUserQueueRepository.getUserInInfoUserQueue(idUser, idQueue);
        Integer capacity = queueRepository.getCapacity(idQueue);
        Integer numQueues = queueUserRepository.numPersonsInQueue(idQueue);
        if(capacity > numQueues) {
            if (!queueUser.isPresent() && !infoUserQueue.isPresent()) {
                Integer pos = queueUserRepository.getLastPosition(idQueue);
                QueueUserDB queueUserDB = new QueueUserDB(idQueue, idUser, pos + 1);
                QueueUserDB queueUserDBInput = queueUserRepository.save(queueUserDB);
                InfoUserQueueDB infoUserQueueDB = new InfoUserQueueDB(idQueue, idUser);
                infoUserQueueRepository.save(infoUserQueueDB);
                return idQueue;
            } else {
                throw new UserAlreadyInQueue("User already in queue");
            }
        }
        else {
            throw new QueueFullException("Queue full");
        }
    }

    @Override
    public Queue stopQueue(Queue queue) {
        QueueDB queueDB = queueAdapter.queueToQueueDB(queue);
        Optional<ActivePeriodDB> activePeriodDB = activePeriodRepository.getLastTuple(queue.getId());

        if(activePeriodDB.isPresent()) {
            if(activePeriodDB.get().getDateDeactivation() == null) {
                Timestamp timestamp = new Timestamp(new Date().getTime());
                ActivePeriodDB activePeriodDB1 = activePeriodDB.get();
                activePeriodDB1.setDateDeactivation(timestamp);
                activePeriodRepository.save(activePeriodDB1);

                queueDB.setLocked(true);
                queueRepository.save(queueDB);
            }
        }
        return queueAdapter.queueDBToQueue(queueDB);
    }

    @Override
    public Queue resumeQueue(Queue queue) {
        QueueDB queueDB = queueAdapter.queueToQueueDB(queue);
        Optional<ActivePeriodDB> activePeriodDB = activePeriodRepository.getLastTuple(queue.getId());

        if(!activePeriodDB.isPresent() || activePeriodDB.get().getDateDeactivation() != null) {
            Timestamp timestamp = new Timestamp(new Date().getTime());
            ActivePeriodDB activePeriodDB1 = activePeriodDB.get();
            activePeriodDB1.setDateActivation(timestamp);
            activePeriodRepository.save(activePeriodDB1);

            queueDB.setLocked(false);
            queueRepository.save(queueDB);
        }

        return queueAdapter.queueDBToQueue(queueDB);
    }

    @Override
    public Queue closeQueue(Queue queue) {
        QueueDB queueDB;
        Optional<ActivePeriodDB> activePeriodDB = activePeriodRepository.getLastTuple(queue.getId());
        Timestamp timestamp = new Timestamp(new Date().getTime());

        if(activePeriodDB.isPresent()) {
            if(activePeriodDB.get().getDateDeactivation() == null) {
                //Se cierra tal cual se ha abierto, sin haberse pausado
                ActivePeriodDB activePeriodDB1 = activePeriodDB.get();
                activePeriodDB1.setDateDeactivation(timestamp);
                activePeriodRepository.save(activePeriodDB1);
            }

        }

        queueDB = queueAdapter.queueToQueueDB(queue);
        queueDB.setLocked(true);
        queueDB.setDateFinished(timestamp);
        queueRepository.save(queueDB);

        return queueAdapter.queueDBToQueue(queueDB);
    }

}


/*if(expand.equals("all")) {
                if (locked == null) {   //All queues from DB
                    queueDBListOptional = queueRepository.getAllQueues();
                } else{     //All queues from DB by locked status
                    queueDBListOptional = queueRepository.getQueuesByLocked(locked);
                }
            }else */