package com.ProjetTWBAV.GestionSalleDeClasse.repository;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfRepository extends JpaRepository<Prof, String> {

    @Query("""
        SELECT p
        FROM Prof p
        WHERE LOWER(p.codeprof) LIKE LOWER(CONCAT('%', :recherche, '%'))
           OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :recherche, '%'))
           OR LOWER(p.prenom) LIKE LOWER(CONCAT('%', :recherche, '%'))
    """)
    List<Prof> search(@Param("recherche") String recherche);
}