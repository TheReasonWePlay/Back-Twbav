package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salle")
public class Salle {

    @Id
    private String codesal;

    private String designation;

    public Salle() {
    }

    public String getCodesal() {
        return codesal;
    }

    public void setCodesal(String codesal) {
        this.codesal = codesal;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}