package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperRequest;
import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperResponse;
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
    public List<OccuperResponse> getAll() {
        return occuperService.getAll()
                .stream()
                .map(o -> new OccuperResponse(
                        o.getId().getCodeprof(),
                        o.getId().getCodesal(),
                        o.getId().getDate()
                ))
                .toList();
    }
    @GetMapping("/{codeprof}/{codesal}/{date}")
    public Occuper getById(
            @PathVariable String codeprof,
            @PathVariable String codesal,
            @PathVariable LocalDate date) {

        OccuperId id = new OccuperId(
                codeprof,
                codesal,
                date
        );

        return occuperService.getById(id);
    }

    @PostMapping
    public OccuperResponse create(
            @RequestBody OccuperRequest request) {

        Occuper occupation =
                occuperService.create(request);

        return new OccuperResponse(
            occupation.getId().getCodeprof(),
            occupation.getId().getCodesal(),
            occupation.getId().getDate()
        );
    }

    @DeleteMapping("/{codeprof}/{codesal}/{date}")
    public void delete(
            @PathVariable String codeprof,
            @PathVariable String codesal,
            @PathVariable LocalDate date) {

        OccuperId id = new OccuperId(
                codeprof,
                codesal,
                date
        );

        occuperService.delete(id);
    }
}