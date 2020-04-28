package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDB, Integer> {
    @Query(value = "SELECT *\n" +
            "FROM users\n" +
            "WHERE token = :token",
         nativeQuery = true)
    Optional<UserDB> findUserByToken(@Param("token") String token);
}
