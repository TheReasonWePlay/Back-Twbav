package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salle")
public class Salle {

    @Id
    private Integer codesal;

    private String designation;

    public Salle() {
    }

    public Integer getCodesal() {
        return codesal;
    }

    public void setCodesal(Integer codesal) {
        this.codesal = codesal;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}