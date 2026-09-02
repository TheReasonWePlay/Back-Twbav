package com.ProjetTWBAV.GestionSalleDeClasse.dto;

import java.time.LocalDate;

public class OccuperResponse {

    private String codeprof;
    private String codesal;
    private LocalDate date;

    public OccuperResponse() {
    }

    public OccuperResponse(
            String codeprof,
            String codesal,
            LocalDate date) {

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
}