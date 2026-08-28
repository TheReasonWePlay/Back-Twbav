package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.service.SalleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salles")
@CrossOrigin("*")
public class SalleController {

    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping
    public List<Salle> getAll() {
        return salleService.getAll();
    }

    @GetMapping("/{id}")
    public Salle getById(@PathVariable Integer id) {
        return salleService.getById(id);
    }

    @PostMapping
    public Salle create(@RequestBody Salle salle) {
        return salleService.save(salle);
    }

    @PutMapping("/{id}")
    public Salle update(
            @PathVariable Integer id,
            @RequestBody Salle salle) {

        salle.setCodesal(id);

        return salleService.save(salle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        salleService.delete(id);
    }
}