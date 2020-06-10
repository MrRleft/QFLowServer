package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.ActivePeriodDB;
import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.entity.ActivePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ActivePeriodRepository extends JpaRepository<ActivePeriodDB, Integer> {

    @Query(value = "SELECT * " +
            "FROM active_period" +
            " WHERE id_queue_ap_fk = :queueId " +
            "ORDER BY id DESC" +
            " LIMIT 1",
            nativeQuery = true)
    Optional<ActivePeriodDB> getLastTuple(Integer queueId);
}
