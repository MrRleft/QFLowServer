package com.qflow.server.domain.repository.dto;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "queue")
public class QueueDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 256)
    private String name;

    @Column(length = 256)
    private String description;

    @Column(name = "business_associated",
            length = 128)
    private String businessAssociated;

    @Column(length = 32, name = "join_id")
    @SequenceGenerator(name="queue_join_id_seq",sequenceName="queue_join_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="queue_join_id_seq")
    private int joinId;

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

    @Column(name = "avg_service_time")
    private Integer avgServiceTime;

    public QueueDB(Integer id, String name, String description, String businessAssociated, int joinId, Integer capacity, Integer currentPos, Boolean isLocked, Timestamp dateCreated, Timestamp dateFinished, Integer avgServiceTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.businessAssociated = businessAssociated;
        this.joinId = joinId;
        this.capacity = capacity;
        this.currentPos = currentPos;
        this.isLocked = isLocked;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
        this.avgServiceTime = avgServiceTime;
    }

    public QueueDB() { }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBusinessAssociated() {
        return businessAssociated;
    }

    public int getJoinId() {
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

    public Integer getAvgServiceTime() {
        return avgServiceTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBusinessAssociated(String businessAssociated) {
        this.businessAssociated = businessAssociated;
    }

    public void setJoinId(int joinId) {
        this.joinId = joinId;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setCurrentPos(Integer currentPos) {
        this.currentPos = currentPos;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateFinished(Timestamp dateFinished) {
        this.dateFinished = dateFinished;
    }

    public void setAvgServiceTime(Integer avgServiceTime) {
        this.avgServiceTime = avgServiceTime;
    }


    public static final class QueueDBBuilder {
        private Integer id;
        private String name;
        private String description;
        private String businessAssociated;
        private int joinId;
        private Integer capacity;
        private Integer currentPos;
        private Boolean isLocked;
        private Timestamp dateCreated;
        private Timestamp dateFinished;
        private Integer avgServiceTime;

        private QueueDBBuilder() {
        }

        public static QueueDBBuilder aQueueDB() {
            return new QueueDBBuilder();
        }

        public QueueDBBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public QueueDBBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public QueueDBBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public QueueDBBuilder withBusinessAssociated(String businessAssociated) {
            this.businessAssociated = businessAssociated;
            return this;
        }

        public QueueDBBuilder withJoinId(int joinId) {
            this.joinId = joinId;
            return this;
        }

        public QueueDBBuilder withCapacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueueDBBuilder withCurrentPos(Integer currentPos) {
            this.currentPos = currentPos;
            return this;
        }

        public QueueDBBuilder withIsLocked(Boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public QueueDBBuilder withDateCreated(Timestamp dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public QueueDBBuilder withDateFinished(Timestamp dateFinished) {
            this.dateFinished = dateFinished;
            return this;
        }

        public QueueDBBuilder withAvgServiceTime(Integer avgServiceTime) {
            this.avgServiceTime = avgServiceTime;
            return this;
        }

        public QueueDB build() {
            return new QueueDB(id, name, description, businessAssociated, joinId, capacity, currentPos, isLocked, dateCreated, dateFinished, avgServiceTime);
        }
    }
}
