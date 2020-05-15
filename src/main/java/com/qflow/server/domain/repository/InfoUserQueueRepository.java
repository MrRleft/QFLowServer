package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InfoUserQueueRepository extends JpaRepository<InfoUserQueueDB, Integer> {

    @Query(value = "SELECT * " +
            "FROM info_user_queue" +
            " WHERE id_user_iuq_fk = :id_user_iuq_fk" +
            " AND id_queue_iuq_fk = :id_queue_iuq_fk ",
            nativeQuery = true)
    Optional<InfoUserQueueDB> getUserInInfoUserQueue(@Param("id_user_iuq_fk") int id_user_iuq_fk,
                                         @Param("id_queue_iuq_fk") int id_queue_iuq_fk);

}
