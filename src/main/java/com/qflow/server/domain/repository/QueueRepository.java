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
            "      WHERE  q.is_locked = :locked",
                     nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByUserIdLocked(Integer userId, Boolean locked );

    @Query(value = "SELECT * " +
            "FROM queue" +
            " WHERE join_id = :joinId ",
            nativeQuery = true)
    Optional<QueueDB> findQueueByJoinId(@Param("joinId") int joinId);

    @Query(value = "SELECT *  FROM queue" +
                    " WHERE queue.is_locked = :locked",
            nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByLocked(Boolean locked);

    @Query(value = "SELECT *  FROM queue",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueues();

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON\n" +
            "      q.id = qu.id_queue_qu_fk\n" +
            "    AND qu.id_user_qu_fk = :userId\n",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueuesByUserId(Integer userId); //expand as userall

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

    @Query(value = "SELECT id " +
            "FROM queue" +
            " WHERE join_id = :join_id ",
            nativeQuery = true)
    Integer getIdQueueByJoinId(@Param("join_id") Integer join_id);

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON\n" +
            "      q.id = qu.id_queue_qu_fk\n" +
            "    AND qu.id_user_qu_fk = :userId\n" +
            "    AND q.date_finished IS NOT NULL",
            nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByUserIdFinished(Integer userId);

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON q.id = qu.id_queue_qu_fk\n\n" +
            "      AND qu.id_user_qu_fk = :userId\n" +
            "    AND q.date_finished IS NULL\n" +
            "    ORDER BY q.is_locked ASC",
            nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByUserIdNotFinished(Integer userId);

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON " +
            "q.id = qu.id_queue_qu_fk JOIN info_user_queue iuq ON q.id = iuq.id_queue_iuq_fk " +
            "AND qu.id_user_qu_fk = :userId AND iuq.id_user_iuq_fk = :userId " +
            "AND (q.date_finished IS NOT NULL OR iuq.date_success IS NOT NULL) ",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueuesByUserIdPastDate(Integer userId);

    @Query(value = "SELECT *  FROM queue q JOIN queue_user qu ON " +
            "q.id = qu.id_queue_qu_fk JOIN info_user_queue iuq ON q.id = iuq.id_queue_iuq_fk " +
            "AND qu.id_user_qu_fk = :userId AND iuq.id_user_iuq_fk = :userId " +
            "AND q.date_finished IS NULL AND iuq.date_success IS NULL",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueuesByUserIdCurrentDate(Integer userId);
}