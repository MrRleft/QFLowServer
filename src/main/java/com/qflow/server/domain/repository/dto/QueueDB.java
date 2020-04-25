package com.qflow.server.domain.repository.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Queues")
public class QueueDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String name;

    @Column(length = 256)
    private String description;

    @Column(name = "business_associated",
            length = 128)
    private String businessAssocitated;

    @Column(length = 32)
    private String joinId;

    @Column(length = 32)
    private Integer capacity;

    @Column(name = "current_position",
            length = 32)
    private Integer currentPos;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_finished")
    private Timestamp dateFinished;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBusinessAssocitated() {
        return businessAssocitated;
    }

    public String getJoinId() {
        return joinId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getCurrentPos() {
        return currentPos;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public Timestamp getDateFinished() {
        return dateFinished;
    }

    public QueueDB(Integer id,
                   String name,
                   String description,
                   String businessAssocitated,
                   String joinId,
                   Integer capacity,
                   Integer currentPos,
                   Boolean isLocked,
                   Timestamp dateCreated,
                   Timestamp dateFinished) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.businessAssocitated = businessAssocitated;
        this.joinId = joinId;
        this.capacity = capacity;
        this.currentPos = currentPos;
        this.isLocked = isLocked;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
    }
}
