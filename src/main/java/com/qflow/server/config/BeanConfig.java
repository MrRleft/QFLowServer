package com.qflow.server.config;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.domain.service.QueueService;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public QueueAdapter queueAdapter(){
        return new QueueAdapter();
    }

    @Bean
    public GetQueue getQueue(@Autowired QueueService queueService){
        return new GetQueue(queueService);
    }

    @Bean
    public CreateQueue createQueue(@Autowired QueueService queueService){
        return new CreateQueue(queueService);
    }


}
