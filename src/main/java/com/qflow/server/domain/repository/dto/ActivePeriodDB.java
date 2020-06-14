package com.qflow.server.domain.repository.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "active_period")
public class ActivePeriodDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_queue_ap_fk",length = 32)
    private Integer idQueue;

    @Column(name = "date_activation")
    private Timestamp dateActivation;

    @Column(name = "date_deactivation")
    private Timestamp dateDeactivation;

    public ActivePeriodDB() {
    }

    public ActivePeriodDB(Integer idQueue, Timestamp dateActivation, Timestamp dateDeactivation) {
        this.idQueue = idQueue;
        this.dateActivation = dateActivation;
        this.dateDeactivation = dateDeactivation;
    }

    public ActivePeriodDB(Integer idQueue, Timestamp dateDeactivation) {
        this.idQueue = idQueue;
        this.dateDeactivation = dateDeactivation;
    }

    public ActivePeriodDB(Integer id, Integer idQueue, Timestamp dateActivation, Timestamp dateDeactivation) {
        this.id = id;
        this.idQueue = idQueue;
        this.dateActivation = dateActivation;
        this.dateDeactivation = dateDeactivation;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdQueue() {
        return idQueue;
    }

    public Timestamp getDateActivation() {
        return dateActivation;
    }

    public Timestamp getDateDeactivation() {
        return dateDeactivation;
    }

    public void setDateActivation(Timestamp dateActivation) {
        this.dateActivation = dateActivation;
    }

    public void setDateDeactivation(Timestamp dateDeactivation) {
        this.dateDeactivation = dateDeactivation;
    }

    public static final class ActivePeriodDBBuilder {
        private Integer id;
        private Integer idQueue;
        private Timestamp dateActivation;
        private Timestamp dateDeactivation;

        private ActivePeriodDBBuilder() {
        }

        public static ActivePeriodDBBuilder anActivePeriodDB() {
            return new ActivePeriodDBBuilder();
        }

        public ActivePeriodDBBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ActivePeriodDBBuilder withIdQueue(Integer idQueue) {
            this.idQueue = idQueue;
            return this;
        }

        public ActivePeriodDBBuilder withDateActivation(Timestamp dateActivation) {
            this.dateActivation = dateActivation;
            return this;
        }

        public ActivePeriodDBBuilder withDateDeactivation(Timestamp dateDeactivation) {
            this.dateDeactivation = dateDeactivation;
            return this;
        }

        public ActivePeriodDB build() {
            return new ActivePeriodDB(id, idQueue, dateActivation, dateDeactivation);
        }
    }
}
