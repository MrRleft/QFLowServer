package com.qflow.server.domain;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.ActivePeriodRepository;
import com.qflow.server.domain.repository.InfoUserQueueRepository;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.QueueUserRepository;
import com.qflow.server.domain.repository.dto.*;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.*;
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

    @Mock
    private QueueDB queueDBMock;

    private List<QueueDB> queueDBListMock;
    private List<QueueDB> queueDBFinishedListMock;

    private UserDB userDBMock;

    private QueueUserDB queueUserDBMock;
    private QueueUserDB queueUserDBMock2;

    @Mock
    private QueueUserRepository queueUserRepository;

    @Mock
    private ActivePeriodRepository activePeriodRepo;

    private ActivePeriodDB activePeriodDB;

    @Mock
    private InfoUserQueueRepository infoUserQueueRepository;

    private InfoUserQueueDB infoUserQueueDB;
    private InfoUserQueueDB infoUserQueueDB2;

    @BeforeEach
    void setUp() {
        this.queueService = new QueueService(queueRepository, queueAdapter, queueUserRepository, infoUserQueueRepository, activePeriodRepo);
        initializeMocks();
    }

    private void initializeMocks() {
        Instant instant = Instant.now();
        queueDBMock = new QueueDB(1, "ExampleNotFinished", "desc",
                "buss", 1, 20,1,false,
                Timestamp.from(instant) , null, 5);

        queueDBListMock = new ArrayList<>();
        queueDBListMock.add(queueDBMock);

        QueueDB queueDBMockFinished1 = new QueueDB(2, "ExampleFinished1", "desc",
                "buss", 2, 20,1,false,
                Timestamp.from(instant), Timestamp.from(instant), 5);

        queueDBFinishedListMock = new ArrayList<>();
        queueDBFinishedListMock.add(queueDBMockFinished1);

        queueUserDBMock = new QueueUserDB(1, 1, 1, true, true, 10);
        queueUserDBMock2 = new QueueUserDB(1, 1, 1, false, false, 10);
        infoUserQueueDB = new InfoUserQueueDB(1, 1);
        infoUserQueueDB2 = new InfoUserQueueDB(1,1, Timestamp.from(instant), Timestamp.from(instant.plusMillis(100)));
        activePeriodDB = new ActivePeriodDB(1, Timestamp.from(instant), null);
    }

    //----------------------------- GetQueuesByIdUser----------------------------------------------------------------

    @Test
    void getQueueById_userId_queue_AllQueues() {
        Mockito.when(queueRepository.getAllQueues()).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(queueUserRepository.getNextPerson(1)).thenReturn("NamePrueba");


        List<Queue> res = queueService.getQueuesByUserId("all", 1, null);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals( -1, res.get(0).getInFrontOfUser());
        assertEquals(0, res.get(0).getNumPersons());
    }

    @Test
    void getQueueById_userId_queue_QueuesFinished() {
        Mockito.when(queueRepository.getQueuesByUserIdFinished(1)).thenReturn(Optional.of(queueDBFinishedListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 2)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(queueUserRepository.getNextPerson(2)).thenReturn("NamePrueba");


        List<Queue> res = queueService.getQueuesByUserId(null, 1, true);
        assertEquals(res.get(0).getName(), "ExampleFinished1");
        assertEquals(9, res.get(0).getInFrontOfUser());
        assertEquals(45, res.get(0).getWaitingTimeForUser());
        assertEquals(0, res.get(0).getNumPersons());
    }

    @Test
    void getQueueById_userId_queue_QueuesNotFinished() {
        Mockito.when(queueRepository.getQueuesByUserIdNotFinished(1)).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(queueUserRepository.getNextPerson(1)).thenReturn("NamePrueba");


        List<Queue> res = queueService.getQueuesByUserId(null, 1, false);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals(9, res.get(0).getInFrontOfUser());
        assertEquals(45, res.get(0).getWaitingTimeForUser());
        assertEquals(0, res.get(0).getNumPersons());
    }

    @Test
    void getQueueById_userId_queue_AllFromUser() {
        Mockito.when(queueRepository.getAllQueuesByUserId(1)).thenReturn(Optional.of(queueDBListMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(queueUserRepository.getNextPerson(1)).thenReturn("NamePrueba");


        List<Queue> res = queueService.getQueuesByUserId("alluser", 1, null);
        assertEquals(res.get(0).getName(), "ExampleNotFinished");
        assertEquals(9, res.get(0).getInFrontOfUser());
        assertEquals(45, res.get(0).getWaitingTimeForUser());
        assertEquals(0, res.get(0).getNumPersons());
    }

    @Test
    void getQueueById_userQueuesNotFound_Exception() {
        Mockito.when(queueRepository.getAllQueues()).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueuesByUserId("all", 1, null));
    }

    //------------------------------- GetQueueByJoinId----------------------------------------------------------------------

    @Test
    void getQueueById_joinId_queue() {
        Mockito.when(queueRepository.getIdQueueByJoinId(1)).thenReturn(1);
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(30);

        Queue res = queueService.getQueueByJoinId(1);

        assertEquals(res.getName(), "ExampleNotFinished");
    }

    @Test
    void getQueueById_joinId_exception() {
        Mockito.when(queueRepository.getIdQueueByJoinId(1)).thenReturn(null);

        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueueByJoinId(1));
    }


    //-------------------------------- GetQueueByQueueId---------------------------------------------------------------------------

    @Test
    void getQueueById_queueId_queue() {
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(30);
        Mockito.when(queueUserRepository.getNextPerson(1)).thenReturn("NamePrueba");

        Queue res = queueService.getQueueByQueueId(1);
        assertEquals(res.getName(), "ExampleNotFinished");
    }

    @Test
    void getQueueById_queueIdNotExists_Exception() {
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueueByQueueId(1));
    }

    @Test
    void createQueue_queue(){
        Queue queueToCreate = Queue.QueueBuilder.aQueue()
                .withJoinId(1133).build();
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
    void joinQueue_queueFull() {
        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.empty());
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(500);
        assertThrows(QueueFullException.class, () -> this.queueService.joinQueue(123, 1));
    }

    @Test
    void joinQueue_userInQueue() {
        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.of(infoUserQueueDB));
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(50);
        assertThrows(UserAlreadyInQueue.class, () -> this.queueService.joinQueue(123, 1));
    }
    @Test
    void joinQueue_reJoin() {
        Mockito.when(queueRepository.getIdQueueByJoinId(123)).thenReturn(1);
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock2));
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.of(infoUserQueueDB2));
        Mockito.when(queueRepository.getCapacity(1)).thenReturn(100);
        Mockito.when(queueUserRepository.numPersonsInQueue(1)).thenReturn(50);

        int idCola = queueService.joinQueue(123, 1);
        assertEquals(1, idCola);
        Mockito.verify(queueUserRepository).save(Mockito.any());
        Mockito.verify(infoUserQueueRepository).save(Mockito.any());
    }

    //-------------------------------- AdvanceQueueByQueueIdAndUserId---------------------------------------------------------------------------
    @Test
    void advanceQueue_UserIdQueueId_allOk() {
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Mockito.when(queueUserRepository.getUserInQueue(1, 1)).thenReturn(Optional.of(queueUserDBMock));
        Mockito.when(infoUserQueueRepository.getUserInInfoUserQueue(1, 1)).thenReturn(Optional.of(infoUserQueueDB));
        Mockito.when(queueUserRepository.getNextPersonId(1)).thenReturn(1);


        queueService.advanceQueue(1, 1);

        Mockito.verify(queueUserRepository).save(queueUserDBMock);
        Mockito.verify(infoUserQueueRepository).save(infoUserQueueDB);
    }

    @Test
    void advanceQueue_QueueIdNotExists_ThrowsExc() {
        assertThrows(QueueNotFoundException.class, () -> queueService.advanceQueue(1, 1));
    }

    @Test
    void advanceQueue_QueueIdLocked_ThrowsExc() {
        QueueDB queue = new QueueDB(1, "ExampleNotFinished", "desc",
                "buss", 1, 20, 1, true,
                null, null, 10);
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queue));

        assertThrows(QueueLockedException.class, () -> queueService.advanceQueue(1, 1));
    }

    @Test
    void advanceQueue_UserNotInQueue_ThrowsExc() {
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));

        assertThrows(UserIsNotAdminException.class, () -> queueService.advanceQueue(1, 1));
    }

}