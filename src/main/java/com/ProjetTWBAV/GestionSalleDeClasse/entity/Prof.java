package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prof")
public class Prof {

    @Id
    private String codeprof;

    private String nom;
    private String prenom;
    private String grade;

    public Prof() {
    }

    public String getCodeprof() {
        return codeprof;
    }

    public void setCodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}