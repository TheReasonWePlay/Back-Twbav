package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class OccuperId implements Serializable {

    private String codeprof;

    private String codesal;

    private LocalDate date;

    public OccuperId() {
    }

    public OccuperId(String codeprof, String codesal, LocalDate date) {
        this.codeprof = codeprof;
        this.codesal = codesal;
        this.date = date;
    }

    public String getCodeprof() {
        return codeprof;
    }

    public void setCodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public String getCodesal() {
        return codesal;
    }

    public void setCodesal(String codesal) {
        this.codesal = codesal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OccuperId)) return false;

        OccuperId that = (OccuperId) o;

        return Objects.equals(codeprof, that.codeprof)
                && Objects.equals(codesal, that.codesal)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeprof, codesal, date);
    }
}