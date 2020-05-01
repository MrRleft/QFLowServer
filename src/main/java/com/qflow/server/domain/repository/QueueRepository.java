package com.qflow.server.domain.repository;

import com.qflow.server.domain.repository.dto.QueueDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueRepository extends JpaRepository<QueueDB, Integer> {

    @Query(value = "SELECT *  FROM queue" +
                        "JOIN queue_user ON queue_user.id_queue_qu_fk = queue.id_queue" +
                        "JOIN users ON queue_user.id_user_qu_fk = :userId" +
                        " AND CASE WHEN :active IS NOT NULL THEN queue.is_locked = :locked ELSE true END",
                     nativeQuery = true)
    Optional<List<QueueDB>> getQueuesByUserId(Integer userId, Boolean locked );

    @Query(value = "SELECT *  FROM queue" +
                    " AND CASE WHEN :locked IS NOT NULL THEN queue.is_locked = :locked ELSE true END",
            nativeQuery = true)
    Optional<List<QueueDB>> getAllQueues(Boolean locked);
}


/*
    -------------------------------------------------------------------------------------

    SELECT DISTINCT Products.*,
                Products.last_modified    AS lastModified,
                Products.created_date     AS createdDate,
                Gateways.title            AS gatewayTitle,
                Gateways.uri              AS gatewayUri,
                Gateways.type             AS gatewayType,
                Gateways.exposure         AS gatewayExposure,
                abstractionlayers.id      as aLId,
                abstractionlayers.name    as aLName,
                abstractionlayers.title   as aLTitle,
                abstractionlayers.country as aLCountry
FROM Products
         INNER JOIN Plans ON Plans.id_product = Products.id
         INNER JOIN ApisProducts ON Products.id = ApisProducts.id_product
         INNER JOIN Gateways ON Gateways.id = Products.id_gateway
         LEFT JOIN PlansOrgs ON Plans.id = PlansOrgs.id_plan
         LEFT JOIN orgsabstractionlayer on orgsabstractionlayer.id_org = plansorgs.id_org
         JOIN abstractionlayers on orgsabstractionlayer.id_abstraction_layer = abstractionlayers.id
WHERE Products.deprecated = false
  AND (
    ((PlansOrgs.id_org = :userOrg AND Plans.visibility = 'INTERNAL')
        OR Plans.visibility = 'PUBLIC'
        OR (Plans.visibility = 'PRIVATE' AND :userOrg <> -1)))
  AND CASE WHEN :alId <> -1 THEN abstractionlayers.id = :alId ELSE true END
 */