package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<QueueDB, Integer> {

         @Query(value = "INSERT INTO queue\n" +
                 "(id, join_id, name, description, business_associated,\n" +
                 "date_finished, date_created, capacity, current_position, is_locked)\n" +
                 "VALUES (:queueDB.getId(), :queueDB.getJoinId(), :queueDB.getName()," +
                 " :queueDB.getDescription(), :queueDB.getBusinessAssociated()," +
                 ":queueDB.getDatefinished(),\n" +
                 ":queueDB.getDateCreated()," +
                 " :queueDB.getCapacity(), :queueDB.getCurrentPos()," +
                 " :queueDB.getLocked())",
               nativeQuery = true)
         Optional<QueueDB> createQueue(@Param("queueDB") QueueDB queue, int userId);

}
