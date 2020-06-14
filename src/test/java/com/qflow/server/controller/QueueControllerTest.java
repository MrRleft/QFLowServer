package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.*;
import com.qflow.server.usecase.users.CreateUser;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QueueControllerTest {

    @MockBean
    private GetQueueByQueueId getQueueByQueueId;

    @MockBean
    private GetQueueByJoinId getQueueByJoinId;

    @MockBean
    private CreateQueue createQueue;

    @MockBean
    private QueueService queueService;

    @MockBean
    private GetUserByToken getUserByToken;

    @MockBean
    private GetQueuesByUserId getQueuesByUserId;

    @MockBean
    private CreateUser createUser;

    @MockBean
    private StopQueue stopQueue;

    @MockBean
    private ResumeQueue resumeQueue;

    @MockBean
    private AdvanceQueue advanceQueue;

    @MockBean
    private CloseQueue closeQueue;

    @MockBean
    private UserService userService;
    private final QueueAdapter queueAdapter = new QueueAdapter();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void setUp(){
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Token", "ExampleToken");
                    return execution.execute(request, body);
                }));
    }

    @Test
    void getQueue_queueId_queue(){

        Queue queueMock = Queue.QueueBuilder.aQueue().withId(1).build();

        Mockito.when(this.getQueueByQueueId.execute(1)).thenReturn(queueMock);


        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/byIdQueue/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));

    }

    @Test
    void getQueue_userId_FinishedQueues(){

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("token", "1");
                    return execution.execute(request, body);
                }));

        List<Queue>  queueListMock = new ArrayList<>();
        Queue queueMock = Queue.QueueBuilder.aQueue().withId(1).build();
        queueListMock.add(queueMock);

        Mockito.when(this.getQueuesByUserId.execute(null,"1",  true)).thenReturn(queueListMock);

        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/byIdUser?finished=true", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));
    }

    @Test
    void getQueue_userId_AllQueuesFromUser(){

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("token", "1");
                    return execution.execute(request, body);
                }));

        List<Queue>  queueListMock = new ArrayList<>();
        Queue queueMock = Queue.QueueBuilder.aQueue().withId(1).build();
        queueListMock.add(queueMock);

        Mockito.when(this.getQueuesByUserId.execute("alluser","1",  null)).thenReturn(queueListMock);

        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/byIdUser?expand=alluser", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));
    }

    @Test
    void getQueue_joinId_correct(){

        Queue queueMock = Queue.QueueBuilder.aQueue().withId(1).build();

        Mockito.when(this.getQueueByJoinId.execute(1)).thenReturn(queueMock);


        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/byIdJoin/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));

    }

    @Test
    void getQueue_joinId_noQueue(){

        Queue queueMock = Queue.QueueBuilder.aQueue().withId(1).build();

        Mockito.when(this.getQueueByJoinId.execute(1)).thenReturn(queueMock);


        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/byIdJoin/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));

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

        Mockito.when(this.stopQueue.execute(1)).thenReturn(queueToStop);

        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/stopQueue/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
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

        Mockito.when(this.resumeQueue.execute(1)).thenReturn(queueToResume);

        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/resumeQueue/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
    }

    @Test
    void advanceQueue_queue() {
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

        Mockito.when(this.advanceQueue.execute(1, "1")).thenReturn(queueToResume);

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("token", "1");
                    return execution.execute(request, body);
                }));

        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/advanceQueue/1", this.port),
                        HttpMethod.POST,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));
    }

    @Test
    void closeQueue_queue() {
        Instant instant = Instant.now();
        Timestamp.from(instant);
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
                .withIsLock(true)
                .build();

        Mockito.when(this.closeQueue.execute(1)).thenReturn(queueToClose);

        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/closeQueue/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
    }

}
