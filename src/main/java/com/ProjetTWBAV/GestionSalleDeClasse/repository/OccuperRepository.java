package com.ProjetTWBAV.GestionSalleDeClasse.repository;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccuperRepository
        extends JpaRepository<Occuper, OccuperId> {

}