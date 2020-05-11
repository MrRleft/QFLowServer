package com.qflow.server.config;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.QueueUserAdapter;
import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueueByQueueId;
import com.qflow.server.usecase.queues.GetQueuesByUserId;
import com.qflow.server.usecase.users.CreateUser;
import com.qflow.server.usecase.queues.JoinQueue;
import com.qflow.server.usecase.users.GetUserByToken;
import com.qflow.server.usecase.users.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    //Queue
    @Bean
    public QueueAdapter queueAdapter(){
        return new QueueAdapter();
    }

    @Bean
    public GetQueueByQueueId getQueueByQueueId(@Autowired QueueService queueService){
        return new GetQueueByQueueId(queueService);
    }

    @Bean
    public GetQueuesByUserId getQueuesByUserId(@Autowired QueueService queueService,
                                               @Autowired GetUserByToken getUserByToken){
        return new GetQueuesByUserId(queueService, getUserByToken);
    }

    @Bean
    public CreateQueue createQueue(@Autowired QueueService queueService, @Autowired GetUserByToken getUserByToken){
        return new CreateQueue(queueService, getUserByToken);
    }

    @Bean
    public JoinQueue joinQueue(@Autowired QueueService queueService, @Autowired GetUserByToken userService){
        return new JoinQueue(queueService, userService);
    }

    //User
    @Bean
    public GetUserByToken getUserByToken(@Autowired UserService userService){
        return new GetUserByToken(userService);
    }

    @Bean
    public LoginUser LoginUser(@Autowired UserService userService){
        return new LoginUser(userService);
    }

    @Bean
    public CreateUser CreateUser(@Autowired UserService userService){
        return new CreateUser(userService);
    }

    @Bean
    public UserAdapter userAdapter(){
        return new UserAdapter();
    }

    //Queue User
    //Queue
    @Bean
    public QueueUserAdapter queueUserAdapter(){
        return new QueueUserAdapter();
    }
}
