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
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.*;
import com.qflow.server.usecase.queues.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class QueueService implements GetQueuesByUserIdDatabase, GetQueueByQueueIdDatabase, GetQueueByJoinIdDatabase,
        CreateQueueDatabase, JoinQueueDatabase, StopQueueDataBase, ResumeQueueDataBase, AdvanceQueueDatabase, CloseQueueDataBase {


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
        }else if(finished) {
            if (expand.equalsIgnoreCase("creator"))
                queueDBListOptional = queueRepository.getQueuesByUserIdFinished(idUser);
            else if (expand.equalsIgnoreCase("user"))
                queueDBListOptional = queueRepository.getAllQueuesByUserIdPastDate(idUser);
        }
        else{
            if(expand.equalsIgnoreCase("creator"))
                queueDBListOptional = queueRepository.getQueuesByUserIdNotFinished(idUser);
            else if(expand.equalsIgnoreCase("user"))
                queueDBListOptional = queueRepository.getAllQueuesByUserIdCurrentDate(idUser);
        }

        if(!queueDBListOptional.isPresent()){
            throw new EmptyQueueListException("Queues not found");
        }

        List<Queue> queueList = new ArrayList<>();
        for(QueueDB queueDB : queueDBListOptional.get()){
            Queue ret = queueAdapter.queueDBToQueue(queueDB);

            Optional<QueueUserDB> quDB = queueUserRepository.getUserInQueue(idUser, ret.getId());
            if(quDB.isPresent()) {

                int inFrontOfUser = quDB.get().getPosition() - ret.getCurrentPos();
                int avgServiceTime = queueDB.getAvgServiceTime();

                ret.setNumPersons(queueUserRepository.numPersonsInQueue(ret.getId()));
                ret.setNextPerson(queueUserRepository.getNextPerson(ret.getId()));

                ret.setInFrontOfUser(inFrontOfUser);
                ret.setWaitingTimeForUser(avgServiceTime * inFrontOfUser);

            }
            else{
                ret.setInFrontOfUser(-1);
            }
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
        QueueDB qDB = queueDBOptional.get();
        Queue ret = queueAdapter.queueDBToQueue(qDB);

        ret.setNumPersons(queueUserRepository.numPersonsInQueue(idQueue));
        ret.setNextPerson(queueUserRepository.getNextPerson(idQueue));
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
            queue.setLock(true);
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

        if(!activePeriodDB.isPresent()) {
            Timestamp timestamp = new Timestamp(new Date().getTime());
            ActivePeriodDB activePeriodDB1 = new ActivePeriodDB(queue.getId(), timestamp,null);
            activePeriodRepository.save(activePeriodDB1);

            queueDB.setLocked(false);
            queueRepository.save(queueDB);
        }
        else if(activePeriodDB.get().getDateDeactivation() != null) {
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

    @Override
    public void advanceQueue(int idUser, int idQueue) {

        Optional<QueueDB> queueDB = queueRepository.findById(idQueue);
        if(!queueDB.isPresent())
            throw new QueueNotFoundException("Queue with id: " + idQueue + " not found");
        if(queueDB.get().getLocked())
            throw new QueueLockedException("Queue with id: " + idQueue + " is locked");

        Optional<QueueUserDB> queueAdminDB = queueUserRepository.getUserInQueue(idUser, idQueue);
        if(!queueAdminDB.isPresent() || !queueAdminDB.get().getAdmin())
            throw new UserIsNotAdminException("User with id " + idUser + " not admin in queue " + idQueue );

        Integer idUserToAdvance = queueUserRepository.getNextPersonId(idQueue);

        Optional<InfoUserQueueDB> infoUserQueueDB = infoUserQueueRepository.getUserInInfoUserQueue(idUserToAdvance, idQueue);
        Optional<QueueUserDB> queueUserDB = queueUserRepository.getUserInQueue(idUser, idQueue);
        if(!infoUserQueueDB.isPresent() || !queueUserDB.isPresent() || !queueUserDB.get().getActive())
            throw new UserIsNotInQueue("User with id " + idUserToAdvance + " not in queue " + idQueue );

        updateDataAdvanceQueue(queueDB.get(), infoUserQueueDB.get(), queueUserDB.get());

    }

    private void updateDataAdvanceQueue(QueueDB queueDB, InfoUserQueueDB infoUserQueueDB, QueueUserDB queueUserDB) {

        queueDB.setCurrentPos(queueUserDB.getPosition()+1);
        infoUserQueueDB.setUnattended(false);
        infoUserQueueDB.setDateSuccess( new Timestamp(new Date().getTime()));
        queueUserDB.setActive(false);
        infoUserQueueRepository.save(infoUserQueueDB);
        queueUserRepository.save(queueUserDB);

    }
}

/*if(!finished && expand.equalsIgnoreCase("user")){    //Looking for not finished User Queues
                    if(inFrontOfUser >= 0){
                        ret.setInFrontOfUser(inFrontOfUser);
                        ret.setWaitingTimeForUser(avgServiceTime * inFrontOfUser);
                        queueList.add(ret);
                    }
                }else if(finished && expand.equalsIgnoreCase("user")){  //looking for finished User Queues
                    if(inFrontOfUser < 0){
                        ret.setInFrontOfUser(inFrontOfUser);
                        ret.setWaitingTimeForUser(avgServiceTime * inFrontOfUser);
                        queueList.add(ret);
                    }
                }else if(expand.equalsIgnoreCase("creator")){      //rest of the cases
                    ret.setWaitingTimeForUser(-1);
                    ret.setInFrontOfUser(-1);
                    queueList.add(ret);
                }else{*/