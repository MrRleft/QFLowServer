package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.ActivePeriodDB;
import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.entity.ActivePeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivePeriodRepository extends JpaRepository<ActivePeriodDB, Integer> {
}
