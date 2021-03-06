package com.qflow.server.controller;

import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.controller.dto.UserPost;
import com.qflow.server.domain.repository.QueueRepository;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.entity.User;
import com.qflow.server.entity.exceptions.LoginNotSuccesfulException;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueuesByUserId;

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
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    @MockBean
    private GetUserByToken getUserByToken;

    /*
    @MockBean
    private CreateUser createUser;
    */

    @MockBean
    private UserService userService;

    @MockBean
    private GetQueuesByUserId getQueuesByUserId;

    @MockBean
    private LoginUser loginUser;

    @MockBean
    private CreateUser createUser;

    @MockBean
    private CreateQueue createQueue;

    @MockBean
    private QueueService queueService;

    @MockBean
    private QueueRepository queueRepository;

    private final UserAdapter userAdapter = new UserAdapter();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private User mockUser;

    @BeforeEach
    void setUp(){
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("isAdmin", "true");
                    return execution.execute(request, body);
                }));

        this.mockUser = User.UserBuilder.anUser().withEmail("prueba@Pororeo")
                .withIsAdmin(false)
                .withNameLastname("left")
                .withPassword("123")
                .withToken("token")
                .withUsername("auiofhjasduio")
                .withId(1)
                .withProfilePicture("ime").build();
    }

    @Test
    void getUserByToken_token_user(){

        User userMock = User.UserBuilder.anUser().withToken("kilo").build();

        Mockito.when(this.getUserByToken.execute("kilo")).thenReturn(userMock);


        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/user/token/kilo", this.port),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("kilo"));
    }

    @Test
    void loginUser_mailPassIsAdmin_token(){

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("mail", "Example@com");
                    request.getHeaders().add("password", "123");
                    request.getHeaders().add("isAdmin", "true");

                    return execution.execute(request, body);
                }));

        Mockito.when(this.loginUser.execute(true, "Example@com", "123"))
                .thenReturn(this.mockUser);


        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/user/?isAdmin=true", this.port),
                        HttpMethod.PUT,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((String) response.getBody()).contains("token"));

    }

    @Test
    void loginUser_parameterMissing_serverError(){

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("mail", "Example@com");
                    request.getHeaders().add("password", "123");

                    return execution.execute(request, body);
                }));

        Mockito.when(this.loginUser.execute(true, "Example@com", "123"))
                .thenReturn(this.mockUser);


        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/user/?isAdmin=true", this.port),
                        HttpMethod.PUT,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void loginUser_parameterNotValid_serverException(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("mail", "Example@com");
                    request.getHeaders().add("password", "123");
                    request.getHeaders().add("isAdmin", "10");

                    return execution.execute(request, body);
                }));

        Mockito.when(this.loginUser.execute(true, "Example@com", "123"))
                .thenReturn(this.mockUser);


        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/user/?isAdmin=true", this.port),
                        HttpMethod.PUT,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void loginUser_userNotExists_LoginNotSuccessfulException(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("mail", "Example@com");
                    request.getHeaders().add("password", "123");
                    request.getHeaders().add("isAdmin", "true");

                    return execution.execute(request, body);
                }));

        Mockito.when(this.loginUser.execute(true, "Example@com", "123"))
                .thenThrow(LoginNotSuccesfulException.class);


        final ResponseEntity response =
                restTemplate.exchange(String.format("http://localhost:%d/qflow/user/?isAdmin=true", this.port),
                        HttpMethod.PUT,
                        new HttpEntity<>(new HttpHeaders()),
                        String.class,
                        new Object());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createUser_userPost_na(){

        UserPost userPost = UserPost.UserPostBuilder.anUserPost()
                .withUserName("name")
                .build();

        Mockito.when(this.createUser.execute(Mockito.any()))
                .thenReturn(this.mockUser);

        final ResponseEntity response =
                this.restTemplate.exchange(String.format("http://localhost:%d/qflow/user/", this.port),
                        HttpMethod.POST,
                        new HttpEntity<>(userPost, new HttpHeaders()),
                        String.class,
                        new Object());

        Mockito.verify(createUser).execute(Mockito.any());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
