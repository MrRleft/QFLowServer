package com.qflow.server.domain;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.UserDB;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueuAlreadyExistsException;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QueueServiceTest {

    @Mock
    private QueueRepository queueRepository;

    private QueueAdapter queueAdapter = new QueueAdapter();

    private QueueService queueService;

    private QueueDB queueDBMock;

    private UserDB userDBMock;

    @BeforeEach
    void setUp() {
        this.queueService = new QueueService(queueRepository, queueAdapter);
        initializeMocks();
    }

    private void initializeMocks() {
        Instant instant = Instant.now();
        queueDBMock = new QueueDB(3, "Example", "desc",
                "buss", "idJ", 2,1,false,
                Timestamp.from(instant), Timestamp.from(instant));
        userDBMock = new UserDB(1, "123", "vic@vic.es", true,
                "pepe", "123",
                "kilo.jpg", "splitter");
    }

    @Test
    void getQueueById_queueId_queue(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));

        Queue res = queueService.getQueue(1);

        assertEquals(res.getName(), "Example");
    }

    @Test
    void getQueueById_queueIdNotExists_Exception(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueue(1));
    }

    @Test
    void createQueue_queueAlreadyExists_queue() {
        Queue queueToCreate = Queue.QueueBuilder.aQueue()
                                .withJoinId("1133").build();
        QueueDB queueDB = new QueueDB();
        Mockito.when(queueRepository.findQueueByJoinId("1133")).thenReturn(Optional.of(queueDB));

        assertThrows(QueueuAlreadyExistsException.class, () -> this.queueService.createQueue(queueToCreate, 1));
    }

    @Test
    void createQueue_queue(){
        Queue queueToCreate = Queue.QueueBuilder.aQueue()
                .withJoinId("1133").build();

        this.queueService.createQueue(queueToCreate, 1);
        Mockito.verify(queueRepository).save(Mockito.any());
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
