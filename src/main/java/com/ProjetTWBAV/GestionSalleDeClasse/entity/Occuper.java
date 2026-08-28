package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "occuper")
public class Occuper {

    @EmbeddedId
    private OccuperId id;

    @ManyToOne
    @MapsId("codeprof")
    @JoinColumn(name = "codeprof")
    private Prof prof;

    @ManyToOne
    @MapsId("codesal")
    @JoinColumn(name = "codesal")
    private Salle salle;

    public Occuper() {
    }

    public OccuperId getId() {
        return id;
    }

    public void setId(OccuperId id) {
        this.id = id;
    }

    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}