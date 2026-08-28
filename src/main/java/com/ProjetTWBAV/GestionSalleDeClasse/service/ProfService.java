package com.ProjetTWBAV.GestionSalleDeClasse.service;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.ProfRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfService {

    private final ProfRepository profRepository;

    public ProfService(ProfRepository profRepository) {
        this.profRepository = profRepository;
    }

    public List<Prof> getAll() {
        return profRepository.findAll();
    }

    public Prof getById(String id) {
        return profRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
    }

    public Prof save(Prof prof) {
        return profRepository.save(prof);
    }

    public void delete(String id) {
        profRepository.deleteById(id);
    }
    
    public List<Prof> searchByNomOrCode(String recherche) {
        return profRepository.search(recherche);
    }
}