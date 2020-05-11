package com.qflow.server.entity;

import org.springframework.hateoas.server.core.Relation;
public class QueueUser {
    private int id;
    private int id_queue_qu_fk;
    private int id_user_qu_fk;
    private Boolean is_active;
    private Boolean is_admin;
    private int position;
    public QueueUser() {
    }
    public int getId() {
        return id;
    }
    public int getId_queue_qu_fk() {
        return id_queue_qu_fk;
    }
    public int getId_user_qu_fk() {
        return id_user_qu_fk;
    }
    public Boolean getIs_active() {
        return is_active;
    }
    public Boolean getIs_admin() {
        return is_admin;
    }
    public int getPosition() {
        return position;
    }

    public static final class QueueUserBuilder {
        private int id;
        private int id_queue_qu_fk;
        private int id_user_qu_fk;
        private Boolean is_active;
        private Boolean is_admin;
        private int position;
        private QueueUserBuilder() {
        }
        public static QueueUserBuilder aQueueUser() {
            return new QueueUserBuilder();
        }
        public QueueUserBuilder withId(int id) {
            this.id = id;
            return this;
        }
        public QueueUserBuilder withId_queue_qu_fk(int id_queue_qu_fk) {
            this.id_queue_qu_fk = id_queue_qu_fk;
            return this;
        }
        public QueueUserBuilder withId_user_qu_fk(int id_user_qu_fk) {
            this.id_user_qu_fk = id_user_qu_fk;
            return this;
        }
        public QueueUserBuilder withIs_active(Boolean is_active) {
            this.is_active = is_active;
            return this;
        }
        public QueueUserBuilder withIs_admin(Boolean is_admin) {
            this.is_admin = is_admin;
            return this;
        }
        public QueueUserBuilder withPosition(int position) {
            this.position = position;
            return this;
        }
        public QueueUser build() {
            QueueUser queueUser = new QueueUser();
            queueUser.id_queue_qu_fk = this.id_queue_qu_fk;
            queueUser.is_active = this.is_active;
            queueUser.id_user_qu_fk = this.id_user_qu_fk;
            queueUser.position = this.position;
            queueUser.is_admin = this.is_admin;
            queueUser.id = this.id;
            return queueUser;
        }
    }
}
