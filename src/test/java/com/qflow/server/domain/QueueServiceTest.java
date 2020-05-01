package com.qflow.server.domain;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.exceptions.QueueNotFoundException;
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

    @BeforeEach
    void setUp() {
        this.queueService = new QueueService(queueRepository, queueAdapter);
        initializeMocks();
    }

    private void initializeMocks() {
        Instant instant = Instant.now();
        queueDBMock = new QueueDB(1, "Example", "desc",
                "buss", "idJ", 2,1,false,
                Timestamp.from(instant), Timestamp.from(instant));

        queueDBListMock = new ArrayList<>();
        queueDBListMock.add(queueDBMock);
    }


    @Test
    void getQueueById_userId_queue_ALL(){
        Mockito.when(queueRepository.getQueuesByUserId(1, false)).thenReturn(Optional.of(queueDBListMock));

        List<Queue> res = queueService.getQueuesByUserId("all", 1, false);

        assertEquals(res.get(0).getName(), "Example");
    }

    @Test
    void getQueueById_userId_queue_NOTALL(){
        Mockito.when(queueRepository.getAllQueues(false)).thenReturn(Optional.of(queueDBListMock));

        List<Queue> res = queueService.getQueuesByUserId(null, 1, false);

        assertEquals(res.get(0).getName(), "Example");
    }

    @Test
    void getQueueById_userQueuesNotFound_Exception(){
        Mockito.when(queueRepository.getQueuesByUserId(1, false)).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueuesByUserId("all", 1, false));
    }


    @Test
    void getQueueById_queueId_queue(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.of(queueDBMock));
        Queue res = queueService.getQueueByQueueId(1);
        assertEquals(res.getName(), "Example");
    }

    @Test
    void getQueueById_queueIdNotExists_Exception(){
        Mockito.when(queueRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(QueueNotFoundException.class, () -> this.queueService.getQueueByQueueId(1));
    }


}
