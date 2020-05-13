package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface QueueRepository extends JpaRepository<QueueDB, Integer> {

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON\n" +
            "      q.id = qu.id_queue_qu_fk\n" +
            "    AND qu.id_user_qu_fk = :userId\n" +
            "      WHERE CASE WHEN :locked IS NOT NULL THEN q.is_locked = :locked ELSE true END",
                     nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByUserId(Integer userId, Boolean locked );
    @Query(value = "SELECT * " +
            "FROM queue" +
            " WHERE join_id = :joinId ",
            nativeQuery = true)
    Optional<QueueDB> findQueueByJoinId(@Param("joinId") int joinId);

    @Query(value = "SELECT *  FROM queue" +
                    " WHERE CASE WHEN :locked IS NOT NULL THEN queue.is_locked = :locked ELSE true END",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueues(Boolean locked);

    @Query(value = "SELECT *" +
            " FROM queue " +
            "WHERE id = :id ",
            nativeQuery = true)
    Optional<QueueDB> insertJoinId(@Param("id") int id);

    @Query(value = "SELECT capacity " +
            "FROM queue" +
            " WHERE id = :id ",
            nativeQuery = true)
    Integer getCapacity(@Param("id") int id);
}
