package com.qflow.server.domain.repository.dto;

import javax.persistence.*;

@Entity
@Table(name = "queue_user")
public class QueueUserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_queue_qu_fk", length = 32)
    private Integer idQueue;

    @Column(name = "id_user_qu_fk", length = 32)
    private Integer idUser;

    @Column(name= "is_active")
    private Boolean isActive;

    @Column(name= "is_admin")
    private Boolean isAdmin;

    @Column(length = 32)
    private Integer position;

    public Integer getId() {
        return id;
    }

    public Integer getIdQueue() {
        return idQueue;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Integer getPosition() {
        return position;
    }

    public QueueUserDB(Integer id, Integer idQueue, Integer idUser, Boolean isActive,
                       Boolean isAdmin, Integer position) {
        this.id = id;
        this.idQueue = idQueue;
        this.idUser = idUser;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.position = position;
    }

    public QueueUserDB() {
    }

    //Constructor for CreateQueue
    public QueueUserDB(Integer idQueue, Integer idUser){
        this.id = null;
        this.idQueue = idQueue;
        this.idUser = idUser;
        this.isActive = true;
        this.isAdmin = true;
        this.position = 0;
    }

    //Constructor for JoinQueue
    public QueueUserDB(Integer idQueue, Integer idUser, Integer pos){
        this.id = null;
        this.idQueue = idQueue;
        this.idUser = idUser;
        this.isActive = true;
        this.isAdmin = false;
        this.position = pos;
    }

    public static final class QueueUserDBBuilder {
        private Integer id;
        private Integer idQueue;
        private Integer idUser;
        private Boolean isActive;
        private Boolean isAdmin;
        private Integer position;

        private QueueUserDBBuilder() {
        }

        public static QueueUserDBBuilder aQueueUserDB() {
            return new QueueUserDBBuilder();
        }

        public QueueUserDBBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public QueueUserDBBuilder withIdQueue(Integer idQueue) {
            this.idQueue = idQueue;
            return this;
        }

        public QueueUserDBBuilder withIdUser(Integer idUser) {
            this.idUser = idUser;
            return this;
        }

        public QueueUserDBBuilder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public QueueUserDBBuilder withIsAdmin(Boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public QueueUserDBBuilder withPosition(Integer position) {
            this.position = position;
            return this;
        }

        public QueueUserDB build() {
            return new QueueUserDB(id, idQueue, idUser, isActive, isAdmin, position);
        }
    }


}
