package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperRequest;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import com.ProjetTWBAV.GestionSalleDeClasse.service.OccuperService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/occupations")
@CrossOrigin("*")
public class OccuperController {

    private final OccuperService occuperService;

    public OccuperController(OccuperService occuperService) {
        this.occuperService = occuperService;
    }

    @GetMapping
    public List<Occuper> getAll() {
        return occuperService.getAll();
    }

    @GetMapping("/{codeprof}/{codesal}/{date}")
    public Occuper getById(
            @PathVariable String codeprof,
            @PathVariable Integer codesal,
            @PathVariable LocalDate date) {

        OccuperId id = new OccuperId(
                codeprof,
                codesal,
                date
        );

        return occuperService.getById(id);
    }

    @PostMapping
    public Occuper create(@RequestBody OccuperRequest request) {

        return occuperService.create(request);
    }

    @DeleteMapping("/{codeprof}/{codesal}/{date}")
    public void delete(
            @PathVariable String codeprof,
            @PathVariable Integer codesal,
            @PathVariable LocalDate date) {

        OccuperId id = new OccuperId(
                codeprof,
                codesal,
                date
        );

        occuperService.delete(id);
    }
}