package com.qflow.server.entity;

import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Relation(collectionRelation = "queues")
public class Queue {

    private int id;
    private String name;
    private String description;
    private int joinId;
    private Timestamp dateCreated;
    private Timestamp dateFinished;
    private int capacity;
    private int currentPos;
    private Boolean isLock;
    private String businessAssociated;
    private int numPersons;
    private int inFrontOfUser;
    private int avgServiceTime;
    private int waitingTimeForUser;

    public Queue() {
    }

    public Queue(int id, String name, String description, int joinId, Timestamp dateCreated, Timestamp dateFinished, int capacity, int currentPos, Boolean isLock, String businessAssociated, int numPersons, int inFrontOfUser, int avgServiceTime, int waitingTimeForUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.joinId = joinId;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
        this.capacity = capacity;
        this.currentPos = currentPos;
        this.isLock = isLock;
        this.businessAssociated = businessAssociated;
        this.numPersons = numPersons;
        this.inFrontOfUser = inFrontOfUser;
        this.avgServiceTime = avgServiceTime;
        this.waitingTimeForUser = waitingTimeForUser;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getJoinId() {
        return joinId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public Timestamp getDateFinished() {
        return dateFinished;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public Boolean getLock() {
        return isLock;
    }

    public String getBusinessAssociated() {
        return businessAssociated;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public int getInFrontOfUser() {
        return inFrontOfUser;
    }

    public int getAvgServiceTime() {
        return avgServiceTime;
    }

    public int getWaitingTimeForUser() {
        return waitingTimeForUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJoinId(int joinId) {
        this.joinId = joinId;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateFinished(Timestamp dateFinished) {
        this.dateFinished = dateFinished;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    public void setBusinessAssociated(String businessAssociated) {
        this.businessAssociated = businessAssociated;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public void setInFrontOfUser(int inFrontOfUser) {
        this.inFrontOfUser = inFrontOfUser;
    }

    public void setAvgServiceTime(int avgServiceTime) {
        this.avgServiceTime = avgServiceTime;
    }

    public void setWaitingTimeForUser(int waitingTimeForUser) {
        this.waitingTimeForUser = waitingTimeForUser;
    }


    public static final class QueueBuilder {
        private int id;
        private String name;
        private String description;
        private int joinId;
        private Timestamp dateCreated;
        private Timestamp dateFinished;
        private int capacity;
        private int currentPos;
        private Boolean isLock;
        private String businessAssociated;
        private int numPersons;
        private int inFrontOfUser;
        private int avgServiceTime;
        private int waitingTimeForUser;



        public static QueueBuilder aQueue() {
            return new QueueBuilder();
        }

        public QueueBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public QueueBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public QueueBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public QueueBuilder withJoinId(int joinId) {
            this.joinId = joinId;
            return this;
        }

        public QueueBuilder withDateCreated(Timestamp dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public QueueBuilder withDateFinished(Timestamp dateFinished) {
            this.dateFinished = dateFinished;
            return this;
        }

        public QueueBuilder withCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueueBuilder withCurrentPos(int currentPos) {
            this.currentPos = currentPos;
            return this;
        }

        public QueueBuilder withIsLock(Boolean isLock) {
            this.isLock = isLock;
            return this;
        }

        public QueueBuilder withBusinessAssociated(String businessAssociated) {
            this.businessAssociated = businessAssociated;
            return this;
        }

        public QueueBuilder withNumPersons(int numPersons) {
            this.numPersons = numPersons;
            return this;
        }

        public QueueBuilder withInFrontOfUser(int inFrontOfUser) {
            this.inFrontOfUser = inFrontOfUser;
            return this;
        }

        public QueueBuilder withAvgServiceTime(int avgServiceTime) {
            this.avgServiceTime = avgServiceTime;
            return this;
        }

        public QueueBuilder withWaitingTimeForUser(int waitingTimeForUser) {
            this.waitingTimeForUser = waitingTimeForUser;
            return this;
        }

        public Queue build() {
            return new Queue(id, name, description, joinId, dateCreated, dateFinished, capacity, currentPos, isLock, businessAssociated, numPersons, inFrontOfUser, avgServiceTime, waitingTimeForUser);
        }
    }
}
