package com.qflow.server.domain;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.ActivePeriodRepository;
import com.qflow.server.domain.repository.InfoUserQueueRepository;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.*;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.entity.InfoUserQueue;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.QueueUser;
import com.qflow.server.entity.exceptions.QueueFullException;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import com.qflow.server.entity.exceptions.UserAlreadyInQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QueueServiceTest {

    @Mock
    private QueueRepository queueRepository;

    private QueueAdapter queueAdapter = new QueueAdapter();

    private QueueService queueService;

    private QueueDB queueDBMock;

    private List<QueueDB> queueDBListMock;
    private List<QueueDB> queueDBFinishedListMock;

    private UserDB userDBMock;

    private QueueUserDB queueUserDBMock;

    @Mock
    private QueueUserRepository queueUserRepository;

    @Mock
    private ActivePeriodRepository activePeriodRepo;

    private ActivePeriodDB activePeriodDB;

    @Mock
    private InfoUserQueueRepository infoUserQueueRepository;

    private InfoUserQueueDB infoUserQueueDB;

    @BeforeEach
    void setUp() {
        this.queueService = new QueueService(queueRepository, queueAdapter, queueUserRepository, infoUserQueueRepository, activePeriodRepo);
        initializeMocks();
    }

    private void initializeMocks() {
        Instant instant = Instant.now();
        queueDBMock = new QueueDB(1, "ExampleNotFinished", "desc",
                "buss", 1, 20,1,false,
                Timestamp.from(instant), null, 10);

        queueDBListMock = new ArrayList<>();
        queueDBListMock.add(queueDBMock);

        QueueDB queueDBMockFinished1 = new QueueDB(2, "ExampleFinished1", "desc",
                "buss", 2, 20,1,false,
                Timestamp.from(instant), Timestamp.from(instant), 10);

        queueDBFinishedListMock = new ArrayList<>();
        queueDBFinishedListMock.add(queueDBMockFinished1);

        queueUserDBMock = new QueueUserDB(1 ,1, 1, true, false, 10);
        infoUserQueueDB = new InfoUserQueueDB(1, 1);
        activePeriodDB = new ActivePeriodDB(1, Timestamp.from(instant), null);
    }

    //----------------------------- GetQueuesByIdUser----------------------------------------------------------------

    @Test
    void getQueueById_userId_queue_AllQueues(){
        Mockito.when(queueRepository.getAllQueues()).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.empty());

        List<Queue> res = queueService.getQueuesByUserId("all", 1, null);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals(res.get(0).getInFrontOfUser(), -1);
        assertEquals(res.get(0).getNumPersons(), 1);
    }

    @Test
    void getQueueById_userId_queue_QueuesFinished(){
        Mockito.when(queueRepository.getQueuesByUserIdFinished(1)).thenReturn(Optional.of(queueDBFinishedListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 2)).thenReturn(Optional.of(queueUserDBMock));

        List<Queue> res = queueService.getQueuesByUserId(null , 1, true);
        assertEquals(res.get(0).getName(), "ExampleFinished1");
        assertEquals(4, res.get(0).getInFrontOfUser());
        assertEquals(res.get(0).getNumPersons(), 1);
    }

    @Test
    void getQueueById_userId_queue_QueuesNotFinished(){
        Mockito.when(queueRepository.getQueuesByUserIdNotFinished(1)).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));

        List<Queue> res = queueService.getQueuesByUserId(null , 1, false);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals(4, res.get(0).getInFrontOfUser());
        assertEquals(res.get(0).getNumPersons(), 1);
    }

    @Test
    void getQueueById_userId_queue_AllFromUser(){
        Mockito.when(queueRepository.getAllQueuesByUserId(1)).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));

        List<Queue> res = queueService.getQueuesByUserId("alluser", 1, null);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals(4, res.get(0).getInFrontOfUser());
        assertEquals(res.get(0).getNumPersons(), 1);
    }

    @Test
    void getQueueById_userQueuesNotFound_Exception(){
        Mockito.when(queueRepository.getAllQueues()).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueuesByUserId("all", 1, null));
    }

    //------------------------------- GetQueueByJoinId----------------------------------------------------------------------

    @Test
    void getQueueById_joinId_queue(){
        Mockito.when(queueRepository.getIdQueueByJoinId(1)).thenReturn(1);
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(30);

        Queue res = queueService.getQueueByJoinId(1);

        assertEquals(res.getName(), "ExampleNotFinished");
    }

    @Test
    void getQueueById_joinId_exception(){
        Mockito.when(queueRepository.getIdQueueByJoinId(1)).thenReturn(null);

        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueueByJoinId(1));
    }



    //-------------------------------- GetQueueByQueueId---------------------------------------------------------------------------

    @Test
    void getQueueById_queueId_queue(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(30);

        Queue res = queueService.getQueueByQueueId(1);
        assertEquals(res.getName(), "ExampleNotFinished");
    }

    @Test
    void getQueueById_queueIdNotExists_Exception(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueueByQueueId(1));
    }

    @Test
    void createQueue_queueAlreadyExists_queue() {
        Queue queueToCreate = Queue.QueueBuilder.aQueue()
                                .withJoinId(1133).build();
        QueueDB queueDB = new QueueDB();
        //QueueUserDB queueUserDB = new QueueUserDB();
        Mockito.when(queueRepository.findQueueByJoinId(1133)).thenReturn(Optional.of(queueDB));
        //Mockito.when(queueUserRepository.save(queueUserDBMock)).thenReturn(Optional.of());
        assertThrows(QueueuAlreadyExistsException.class, () -> this.queueService.createQueue(queueToCreate, 1));
    }

    @Test
    void createQueue_queue(){
        Queue queueToCreate = Queue.QueueBuilder.aQueue()
                .withJoinId(1133).build();
        /*
        this.queueService.createQueue(queueToCreate, 1);
        Mockito.verify(queueRepository).save(Mockito.any());
        */
    }

    @Test
    void stopQueue_queue() {
        Instant instant = Instant.now();
        Timestamp.from(instant);
        Queue queueToStop = Queue.QueueBuilder.aQueue()
                .withId(10)
                .withJoinId(222)
                .withBusinessAssociated("sony")
                .withCapacity(100)
                .withDescription("mala")
                .withName("pepe")
                .withCurrentPos(1)
                .withDateCreated(Timestamp.from(instant))
                .withDateFinished(Timestamp.from(instant))
                .withIsLock(false)
                .build();

        Mockito.when(activePeriodRepo.getLastTuple(queueToStop.getId())).thenReturn(Optional.of(activePeriodDB));
        queueService.stopQueue(queueToStop);
        Mockito.verify(activePeriodRepo).save(Mockito.any());
        Mockito.verify(queueRepository).save(Mockito.any());
    }

    @Test
    void resumeQueue_queue() {
        Instant instant = Instant.now();
        Timestamp.from(instant);
        Queue queueToResume = Queue.QueueBuilder.aQueue()
                .withId(10)
                .withJoinId(222)
                .withBusinessAssociated("sony")
                .withCapacity(100)
                .withDescription("mala")
                .withName("pepe")
                .withCurrentPos(1)
                .withDateCreated(Timestamp.from(instant))
                .withDateFinished(Timestamp.from(instant))
                .withIsLock(true)
                .build();

        Mockito.when(activePeriodRepo.getLastTuple(queueToResume.getId())).thenReturn(Optional.empty());
        queueService.resumeQueue(queueToResume);
        Mockito.verify(activePeriodRepo).save(Mockito.any());
        Mockito.verify(queueRepository).save(Mockito.any());
    }

    @Test
    void closeQueue_queue() {
        Instant instant = Instant.now();
        Queue queueToClose = Queue.QueueBuilder.aQueue()
                .withId(10)
                .withJoinId(222)
                .withBusinessAssociated("sony")
                .withCapacity(100)
                .withDescription("mala")
                .withName("pepe")
                .withCurrentPos(1)
                .withDateCreated(Timestamp.from(instant))
                .withDateFinished(Timestamp.from(instant))
                .withIsLock(false)
                .withInFrontOfUser(10)
                .withNumPersons(3)
                .withAvgServiceTime(100)
                .build();
        Mockito.when(activePeriodRepo.getLastTuple(queueToClose.getId())).thenReturn(Optional.of(activePeriodDB));
        queueAdapter.queueToQueueDB(queueToClose);
        queueService.closeQueue(queueToClose);
        Mockito.verify(activePeriodRepo).save(Mockito.any());
        Mockito.verify(queueRepository).save(Mockito.any());
    }

    @Test
    void joinQueue_queue() {

        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(50);
        Mockito.when(queueUserRepository.getLastPosition(1)).thenReturn(10);

        int idCola = queueService.joinQueue(123, 1);
        assertEquals(1, idCola);
        Mockito.verify(queueUserRepository).save(Mockito.any());
        Mockito.verify(infoUserQueueRepository).save(Mockito.any());
    }

    @Test
    void joinQueue_queueFull(){
        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(500);
        assertThrows(QueueFullException.class, () -> this.queueService.joinQueue(123, 1));
    }

    @Test
    void joinQueue_userInQueue(){
        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.of(infoUserQueueDB));
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(50);
        assertThrows(UserAlreadyInQueue.class, () -> this.queueService.joinQueue(123, 1));
    }

}


/*
@Test
void createUser_userExists_UserNotCreatedException(){
    User userToCreate = User.UserBuilder.anUser()
            .withEmail("example")
            .withIsAdmin(true)
            .build();
    UserDB userDB = new UserDB();
    Mockito.when(userRepository.findUserByEmailAndisAdmin("example", true))
            .thenReturn(Optional.of(userDB));
    assertThrows(UserAlreadyExistsException.class, () -> this.userService.createUser(userToCreate));
}

@Test
void createUser_userNotExists_na(){

    User userToCreate = User.UserBuilder.anUser()
            .withEmail("example")
            .withIsAdmin(true)
            .build();

    this.userService.createUser(userToCreate);
    Mockito.verify(userRepository).save(Mockito.any());
}
* */
