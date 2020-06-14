package com.qflow.server.domain.repository.dto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "info_user_queue")
public class InfoUserQueueDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_queue_iuq_fk",length = 32)
    private Integer idQueue;

    @Column(name = "id_user_iuq_fk",length = 32)
    private Integer idUser;

    @Column(name = "is_rate_iuq_fk", length = 32)
    private Integer isRate;

    @Column(name = "date_access")
    private Timestamp dateAccess;

    @Column(name = "date_success")
    private Timestamp dateSuccess;

    @Column(name = "unattended")
    private Boolean unattended;

    public InfoUserQueueDB(Integer id, Integer idQueue, Integer idUser,
                           Integer isRate, Timestamp dateAccess,
                           Timestamp dateSuccess, Boolean unattended) {
        this.id = id;
        this.idQueue = idQueue;
        this.idUser = idUser;
        this.isRate = isRate;
        this.dateAccess = dateAccess;
        this.dateSuccess = dateSuccess;
        this.unattended = unattended;
    }

    public InfoUserQueueDB() {
    }

    public InfoUserQueueDB(Integer idQueue, Integer idUser) {
        this.id = id;
        this.idQueue = idQueue;
        this.idUser = idUser;
        this.dateAccess = new Timestamp(new Date().getTime());
        this.unattended = false;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdQueue() {
        return idQueue;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getIsRate() {
        return isRate;
    }

    public Timestamp getDateAccess() {
        return dateAccess;
    }

    public Timestamp getDateSuccess() {
        return dateSuccess;
    }

    public Boolean getUnattended() {
        return unattended;
    }

    public void setDateSuccess(Timestamp dateSuccess) {
        this.dateSuccess = dateSuccess;
    }

    public void setUnattended(Boolean unattended) {
        this.unattended = unattended;
    }

    public static final class InfoUserQueueDBBuilder {
        private Integer id;
        private Integer idQueue;
        private Integer idUser;
        private Integer isRate;
        private Timestamp dateAccess;
        private Timestamp dateSuccess;
        private Boolean unattended;

        private InfoUserQueueDBBuilder() {
        }

        public static InfoUserQueueDBBuilder anInfoUserQueueDB() {
            return new InfoUserQueueDBBuilder();
        }

        public InfoUserQueueDBBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public InfoUserQueueDBBuilder withIdQueue(Integer idQueue) {
            this.idQueue = idQueue;
            return this;
        }

        public InfoUserQueueDBBuilder withIdUser(Integer idUser) {
            this.idUser = idUser;
            return this;
        }

        public InfoUserQueueDBBuilder withIsRate(Integer isRate) {
            this.isRate = isRate;
            return this;
        }

        public InfoUserQueueDBBuilder withDateAccess(Timestamp dateAccess) {
            this.dateAccess = dateAccess;
            return this;
        }

        public InfoUserQueueDBBuilder withDateSuccess(Timestamp dateSuccess) {
            this.dateSuccess = dateSuccess;
            return this;
        }

        public InfoUserQueueDBBuilder withUnattended(Boolean unattended) {
            this.unattended = unattended;
            return this;
        }

        public InfoUserQueueDB build() {
            return new InfoUserQueueDB(id, idQueue, idUser,
                    isRate, dateAccess, dateSuccess, unattended);
        }
    }
}
