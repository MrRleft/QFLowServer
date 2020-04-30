package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDB, Integer> {
    @Query(value = "SELECT *\n" +
            "FROM users\n" +
            "WHERE token = :token",
         nativeQuery = true)
    Optional<UserDB> findUserByToken(@Param("token") String token);

    @Query(value = "SELECT *\n" +
            "FROM users\n" +
            "WHERE email = :email " +
            "AND password = :password " +
            "AND is_admin = :isAdmin",
            nativeQuery = true)
    Optional<UserDB> findUserByEmailAndPassword(@Param("email") String email,
                                    @Param("password") String password,
                                    @Param("isAdmin") boolean isAdmin);
}
