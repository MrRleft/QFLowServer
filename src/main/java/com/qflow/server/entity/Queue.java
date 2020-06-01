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

    public Queue() {
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

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public void setJoinId(Integer rnd) {
        this.joinId = rnd;
    }

    public void setDateCreated(Timestamp timestamp) {
        this.dateCreated = timestamp;
    }

    public void setIsLocked(boolean b) {
        this.isLock = b;
    }

    public void setCurrentPos(int i) {
        this.currentPos = i;
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

        private QueueBuilder() {
        }

        public static QueueBuilder aQueue() {
            return new QueueBuilder();
        }

        public QueueBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public QueueBuilder withBusinessAssociated(String businessAssociated)
        {
            this.businessAssociated = businessAssociated;
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

        public Queue build() {
            Queue queue = new Queue();
            queue.name = this.name;
            queue.capacity = this.capacity;
            queue.id = this.id;
            queue.description = this.description;
            queue.dateCreated = this.dateCreated;
            queue.dateFinished = this.dateFinished;
            queue.isLock = this.isLock;
            queue.joinId = this.joinId;
            queue.currentPos = this.currentPos;
            queue.businessAssociated = this.businessAssociated;
            return queue;
        }
    }
}
