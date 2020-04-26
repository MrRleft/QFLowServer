package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueRepository extends JpaRepository<QueueDB, Integer> {
//
//    @Query(value = "SELECT x " +
//            "FROM y " +
//            "WHERE :queueName",
//            nativeQuery = true)
//    Queue findApplicationByName(@Param("queueName") String queueName);

}
