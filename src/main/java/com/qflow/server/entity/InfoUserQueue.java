package com.qflow.server.entity;

import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Relation(collectionRelation = "info_user_queue")
public class InfoUserQueue {
    private int id;
    private int id_queue;
    private int id_user;
    private int is_rate;
    private Timestamp date_access;
    private Timestamp date_success;
    private boolean unattended;

    public InfoUserQueue() {
    }


    public int getId() {
        return id;
    }

    public int getId_queue() {
        return id_queue;
    }

    public int getId_user() {
        return id_user;
    }

    public int getIs_rate() {
        return is_rate;
    }

    public Timestamp getDate_access() {
        return date_access;
    }

    public Timestamp getDate_success() {
        return date_success;
    }

    public boolean isUnattended() {
        return unattended;
    }

    public static final class InfoUserQueueBuilder {
        private int id;
        private int id_queue;
        private int id_user;
        private int is_rate;
        private Timestamp date_access;
        private Timestamp date_success;
        private boolean unattended;

        private InfoUserQueueBuilder() {
        }

        public static InfoUserQueueBuilder anInfoUserQueue() {
            return new InfoUserQueueBuilder();
        }

        public InfoUserQueueBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public InfoUserQueueBuilder withId_queue(int id_queue) {
            this.id_queue = id_queue;
            return this;
        }

        public InfoUserQueueBuilder withId_user(int id_user) {
            this.id_user = id_user;
            return this;
        }

        public InfoUserQueueBuilder withIs_rate(int is_rate) {
            this.is_rate = is_rate;
            return this;
        }

        public InfoUserQueueBuilder withDate_access(Timestamp date_access) {
            this.date_access = date_access;
            return this;
        }

        public InfoUserQueueBuilder withDate_success(Timestamp date_success) {
            this.date_success = date_success;
            return this;
        }

        public InfoUserQueueBuilder withUnattended(boolean unattended) {
            this.unattended = unattended;
            return this;
        }

        public InfoUserQueue build() {
            InfoUserQueue infoUserQueue = new InfoUserQueue();
            infoUserQueue.id_user = this.id_user;
            infoUserQueue.date_access = this.date_access;
            infoUserQueue.unattended = this.unattended;
            infoUserQueue.id_queue = this.id_queue;
            infoUserQueue.date_success = this.date_success;
            infoUserQueue.id = this.id;
            infoUserQueue.is_rate = this.is_rate;
            return infoUserQueue;
        }
    }
}
