package com.qflow.server.entity;

import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Relation(collectionRelation = "active_period")
public class ActivePeriod {
    private int id;
    private Timestamp dateActivation;
    private Timestamp dateDeactivation;
    private int id_queue_ap_fk;

    public ActivePeriod() {
    }

    public int getId() {
        return id;
    }

    public Timestamp getDateActivation() {
        return dateActivation;
    }

    public Timestamp getDateDeactivation() {
        return dateDeactivation;
    }

    public int getId_queue_ap_fk() {
        return id_queue_ap_fk;
    }

    public void setDateActivation(Timestamp dateActivation) {
        this.dateActivation = dateActivation;
    }

    public void setDateDeactivation(Timestamp dateDeactivation) {
        this.dateDeactivation = dateDeactivation;
    }

    public void setId_queue_ap_fk(int id_queue_ap_fk) {
        this.id_queue_ap_fk = id_queue_ap_fk;
    }

    public static final class ActivePeriodBuilder {
        private int id;
        private Timestamp dateActivation;
        private Timestamp dateDeactivation;
        private int id_queue_qu_fk;

        private ActivePeriodBuilder() {
        }

        public static ActivePeriodBuilder anActivePeriod() {
            return new ActivePeriodBuilder();
        }

        public ActivePeriodBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public ActivePeriodBuilder withDateActivation(Timestamp dateActivation) {
            this.dateActivation = dateActivation;
            return this;
        }

        public ActivePeriodBuilder withDateDeactivation(Timestamp dateDeactivation) {
            this.dateDeactivation = dateDeactivation;
            return this;
        }

        public ActivePeriodBuilder withId_queue_qu_fk(int id_queue_qu_fk) {
            this.id_queue_qu_fk = id_queue_qu_fk;
            return this;
        }

        public ActivePeriod build() {
            ActivePeriod activePeriod = new ActivePeriod();
            activePeriod.dateActivation = this.dateActivation;
            activePeriod.id_queue_ap_fk = this.id_queue_qu_fk;
            activePeriod.id = this.id;
            activePeriod.dateDeactivation = this.dateDeactivation;
            return activePeriod;
        }
    }
}
