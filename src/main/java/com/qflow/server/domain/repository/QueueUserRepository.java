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
    Integer getLastPosition(@Param("id_queue_qu_fk") int idQueue);

    @Query(value = "SELECT * " +
            "FROM queue_user " +
            "WHERE id_user_qu_fk = :id_user_qu_fk" +
            " AND id_queue_qu_fk = :id_queue_qu_fk ",
            nativeQuery = true)
    Optional<QueueUserDB> getUserInQueue(@Param("id_user_qu_fk") int idUser,
                              @Param("id_queue_qu_fk") int idQueue);

    @Query(value = "SELECT COUNT(id_queue_qu_fk) " +
            "FROM queue_user " +
            "WHERE is_active = true" +
            " AND id_queue_qu_fk = :id_queue_qu_fk ",
            nativeQuery = true)
    Integer numPersonsInQueue(@Param("id_queue_qu_fk") int idQueue);

    @Query(value= "SELECT u.name_lastname " +
            "FROM users u JOIN info_user_queue iuq ON u.id = iuq.id_user_iuq_fk " +
            "WHERE iuq.id_queue_iuq_fk = :id_queue_iuq_fk AND iuq.date_success IS NULL " +
            "ORDER BY date_access DESC LIMIT 1",
            nativeQuery = true)
    String getNextPerson(@Param("id_queue_iuq_fk") int id_queue_iuq_fk);

    @Query(value= "SELECT u.id " +
            "FROM users u JOIN info_user_queue iuq ON u.id = iuq.id_user_iuq_fk " +
            "WHERE iuq.id_queue_iuq_fk = :id_queue_iuq_fk AND iuq.date_success IS NULL " +
            "ORDER BY date_access DESC LIMIT 1",
            nativeQuery = true)
    Integer getNextPersonId(@Param("id_queue_iuq_fk") int id_queue_iuq_fk);
}

//JOIN queue_user qu ON u.id = qu.id_user_qu_fk