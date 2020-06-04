package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueUserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueUserRepository extends JpaRepository<QueueUserDB, Integer> {

    @Query(value = "SELECT MAX(position )  " +
            "FROM queue_user " +
            "WHERE id_queue_qu_fk = :id_queue_qu_fk ",
            nativeQuery = true)
    Integer getLastPosition(@Param("id_queue_qu_fk") int id_queue_qu_fk);

    @Query(value = "SELECT * " +
            "FROM queue_user " +
            "WHERE id_user_qu_fk = :id_user_qu_fk" +
            " AND id_queue_qu_fk = :id_queue_qu_fk ",
            nativeQuery = true)
    Optional<QueueUserDB> getUserInQueue(@Param("id_user_qu_fk") int id_user_qu_fk,
                              @Param("id_queue_qu_fk") int id_queue_qu_fk);

    @Query(value = "SELECT COUNT(id_queue_qu_fk) " +
            "FROM queue_user " +
            "WHERE is_active = true" +
            " AND id_queue_qu_fk = :id_queue_qu_fk ",
            nativeQuery = true)
    Integer numPersonsInQueue(@Param("id_queue_qu_fk") int id_queue_qu_fk);
}
