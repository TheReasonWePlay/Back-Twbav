package com.ProjetTWBAV.GestionSalleDeClasse.service;

import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperRequest;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.OccuperRepository;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.ProfRepository;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.SalleRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccuperService {

    private final OccuperRepository occuperRepository;
    private final ProfRepository profRepository;
    private final SalleRepository salleRepository;

    public OccuperService(
            OccuperRepository occuperRepository,
            ProfRepository profRepository,
            SalleRepository salleRepository) {

        this.occuperRepository = occuperRepository;
        this.profRepository = profRepository;
        this.salleRepository = salleRepository;
    }

    public List<Occuper> getAll() {
        return occuperRepository.findAll();
    }

    public Occuper getById(OccuperId id) {

        return occuperRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Occupation introuvable"));
    }

    public Occuper create(OccuperRequest request) {

        Prof prof = profRepository.findById(request.getCodeprof())
                .orElseThrow(() ->
                        new RuntimeException("Professeur introuvable"));

        Salle salle = salleRepository.findById(request.getCodesal())
                .orElseThrow(() ->
                        new RuntimeException("Salle introuvable"));

        OccuperId id = new OccuperId(
                request.getCodeprof(),
                request.getCodesal(),
                request.getDate()
        );

        Occuper occuper = new Occuper();

        occuper.setId(id);
        occuper.setProf(prof);
        occuper.setSalle(salle);

        return occuperRepository.save(occuper);
    }

    public void delete(OccuperId id) {
        occuperRepository.deleteById(id);
    }
}