package com.ProjetTWBAV.GestionSalleDeClasse.dto;

import java.time.LocalDate;

public class OccuperRequest {

    private String codeprof;

    private Integer codesal;

    private LocalDate date;

    public OccuperRequest() {
    }

    public String getCodeprof() {
        return codeprof;
    }

    public void setCodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public Integer getCodesal() {
        return codesal;
    }

    public void setCodesal(Integer codesal) {
        this.codesal = codesal;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}