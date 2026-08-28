package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.service.ProfService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profs")
@CrossOrigin("*")
public class ProfController {

    private final ProfService profService;

    public ProfController(ProfService profService) {
        this.profService = profService;
    }

    @GetMapping
    public List<Prof> getAll() {
        return profService.getAll();
    }

    @GetMapping("/{id}")
    public Prof getById(@PathVariable String id) {
        return profService.getById(id);
    }

    @PostMapping
    public Prof create(@RequestBody Prof prof) {
        return profService.save(prof);
    }

    @PutMapping("/{id}")
    public Prof update(
            @PathVariable String id,
            @RequestBody Prof prof) {

        prof.setCodeprof(id);

        return profService.save(prof);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        profService.delete(id);
    }
    
    @GetMapping("/search")
    public List<Prof> search(@RequestParam String recherche) {
        return profService.searchByNomOrCode(recherche);
    }
}