package com.qflow.server.controller.dto;

import java.sql.Timestamp;

public class QueuePost {
    //TODO this class represents what the app sends the service when creating a Queue
    //TODO add builder (download plugin to create builders (remember to create innerbuild))

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

    public static final class QueuePostBuilder {
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

        private QueuePostBuilder() {
        }

        public static QueuePostBuilder aQueuePost() {
            return new QueuePostBuilder();
        }

        public QueuePostBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public QueuePostBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public QueuePostBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public QueuePostBuilder withJoinId(int joinId) {
            this.joinId = joinId;
            return this;
        }

        public QueuePostBuilder withDateCreated(Timestamp dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public QueuePostBuilder withDateFinished(Timestamp dateFinished) {
            this.dateFinished = dateFinished;
            return this;
        }

        public QueuePostBuilder withCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueuePostBuilder withCurrentPos(int currentPos) {
            this.currentPos = currentPos;
            return this;
        }

        public QueuePostBuilder withIsLock(Boolean isLock) {
            this.isLock = isLock;
            return this;
        }

        public QueuePostBuilder withBusinessAssociated(String businessAssociated) {
            this.businessAssociated = businessAssociated;
            return this;
        }

        public QueuePost build() {
            QueuePost queuePost = new QueuePost();
            queuePost.dateFinished = this.dateFinished;
            queuePost.id = this.id;
            queuePost.businessAssociated = this.businessAssociated;
            queuePost.name = this.name;
            queuePost.capacity = this.capacity;
            queuePost.currentPos = this.currentPos;
            queuePost.isLock = this.isLock;
            queuePost.description = this.description;
            queuePost.dateCreated = this.dateCreated;
            queuePost.joinId = this.joinId;
            return queuePost;
        }
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

}
