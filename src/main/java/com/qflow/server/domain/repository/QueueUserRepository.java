package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueUserRepository extends JpaRepository<QueueUserDB, Integer> {

}
