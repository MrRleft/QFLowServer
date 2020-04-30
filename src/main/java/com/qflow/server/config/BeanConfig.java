package com.qflow.server.config;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.adapter.UserAdapter;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.domain.service.UserService;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueueByQueueId;
import com.qflow.server.usecase.queues.GetQueuesByUserId;
import com.qflow.server.usecase.users.GetUserByToken;
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
    public GetQueuesByUserId getQueuesByUserId(@Autowired QueueService queueService){
        return new GetQueuesByUserId(queueService);
    }

    @Bean
    public CreateQueue createQueue(@Autowired QueueService queueService){
        return new CreateQueue(queueService);
    }

    //User
    @Bean
    public GetUserByToken getUserByToken(@Autowired UserService userService){
        return new GetUserByToken(userService);
    }

    @Bean
    public UserAdapter userAdapter(){
        return new UserAdapter();
    }
}
