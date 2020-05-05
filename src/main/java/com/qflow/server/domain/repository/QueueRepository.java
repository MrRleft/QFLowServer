package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<QueueDB, Integer> {

    @Query(value = "SELECT *\n" +
            "FROM queue\n" +
            "WHERE join_id = :joinId ",
            nativeQuery = true)
    Optional<QueueDB> findQueueByJoinId(@Param("joinId") int joinId);


    @Query(value = "SELECT *\n" +
            "FROM queue\n" +
            "WHERE id = :id ",
            nativeQuery = true)
    Optional<QueueDB> insertJoinId(@Param("id") int id);
}
