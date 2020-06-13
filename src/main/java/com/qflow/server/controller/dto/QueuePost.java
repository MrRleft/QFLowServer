package com.qflow.server.controller.dto;

import java.sql.Timestamp;

public class QueuePost {
    //TODO this class represents what the app sends the service when creating a Queue

    private String name;
    private String description;
    private int capacity;
    private String businessAssociated;
    private int estimatedTime;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBusinessAssociated() {
        return businessAssociated;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }


    public static final class QueuePostBuilder {
        private String name;
        private String description;
        private int capacity;
        private String businessAssociated;
        private int estimatedTime;

        private QueuePostBuilder() {
        }

        public static QueuePostBuilder QueuePost() {
            return new QueuePostBuilder();
        }

        public QueuePostBuilder name(String name) {
            this.name = name;
            return this;
        }

        public QueuePostBuilder description(String description) {
            this.description = description;
            return this;
        }

        public QueuePostBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueuePostBuilder businessAssociated(String businessAssociated) {
            this.businessAssociated = businessAssociated;
            return this;
        }

        public QueuePostBuilder estimatedTime(int estimatedTime) {
            this.estimatedTime = estimatedTime;
            return this;
        }

        public QueuePost build() {
            QueuePost queuePost = new QueuePost();
            queuePost.capacity = this.capacity;
            queuePost.name = this.name;
            queuePost.estimatedTime = this.estimatedTime;
            queuePost.description = this.description;
            queuePost.businessAssociated = this.businessAssociated;
            return queuePost;
        }
    }
}
