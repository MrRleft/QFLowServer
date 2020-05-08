package com.qflow.server.controller.dto;

import java.sql.Timestamp;

public class QueuePost {
    //TODO this class represents what the app sends the service when creating a Queue
    //TODO add builder (download plugin to create builders (remember to create innerbuild))

    private String name;
    private String description;
    private int capacity;
    private String businessAssociated;

    public static final class QueuePostBuilder {
        private String name;
        private String description;
        private int capacity;
        private String businessAssociated;

        private QueuePostBuilder() {
        }

        public static QueuePostBuilder aQueuePost() {
            return new QueuePostBuilder();
        }

        public QueuePostBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public QueuePostBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public QueuePostBuilder withCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueuePostBuilder withBusinessAssociated(String businessAssociated) {
            this.businessAssociated = businessAssociated;
            return this;
        }

        public QueuePost build() {
            QueuePost queuePost = new QueuePost();
            queuePost.name = this.name;
            queuePost.businessAssociated = this.businessAssociated;
            queuePost.capacity = this.capacity;
            queuePost.description = this.description;
            return queuePost;
        }
    }

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
}
