package com.qflow.server.controller.dto;

import java.sql.Timestamp;

public class QueuePost {
    //TODO this class represents what the app sends the service when creating a Queue
    //TODO add builder (download plugin to create builders (remember to create innerbuild))

    private String name;
    private String description;
    private int capacity;
    private String businessAssociated;
    private int avgServiceTime;

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

    public int getAvgServiceTime() { return avgServiceTime; }


    public static final class QueuePostBuilder {
        private String name;
        private String description;
        private int capacity;
        private String businessAssociated;
        private int avgServiceTime;

        private QueuePostBuilder() {
        }

        public static QueuePostBuilder aQueuePost() {
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

        public QueuePostBuilder avgServiceTime(int avgServiceTime){
            this.avgServiceTime = avgServiceTime;
            return this;
        }

        public QueuePost build() {
            QueuePost queuePost = new QueuePost();
            queuePost.description = this.description;
            queuePost.capacity = this.capacity;
            queuePost.name = this.name;
            queuePost.businessAssociated = this.businessAssociated;
            queuePost.avgServiceTime = this.avgServiceTime;
            return queuePost;
        }
    }
}
