package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueue;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class QueueControllerTest {

    @MockBean
    private GetQueue getQueue;

    @MockBean
    private CreateQueue createQueue;

    @MockBean
    private QueueService queueService;

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

        Mockito.when(this.getQueue.execute(1)).thenReturn(queueMock);


        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/queues/1", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("1"));

    }

}
