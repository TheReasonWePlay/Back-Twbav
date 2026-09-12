package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperRequest;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.service.OccuperService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OccuperController.class)
class OccuperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OccuperService occuperService;


    @Test
    void testGetAll() throws Exception {

        Prof prof = new Prof();
        prof.setCodeprof("P001");
        prof.setNom("Dupont");
        prof.setPrenom("Jean");
        prof.setGrade("Professeur");

        Salle salle = new Salle();
        salle.setCodesal("S001");
        salle.setDesignation("Salle Informatique");

        Occuper occupation1 = new Occuper();
        occupation1.setId(
                new OccuperId(
                        "P001",
                        "S001",
                        LocalDate.of(2026, 9, 10)
                )
        );
        occupation1.setProf(prof);
        occupation1.setSalle(salle);

        Occuper occupation2 = new Occuper();
        occupation2.setId(
                new OccuperId(
                        "P001",
                        "S001",
                        LocalDate.of(2026, 9, 11)
                )
        );
        occupation2.setProf(prof);
        occupation2.setSalle(salle);

        when(occuperService.getAll())
                .thenReturn(List.of(occupation1, occupation2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/occupations")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].date")
                .value("2026-09-10"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].date")
                .value("2026-09-11"));
    }


    @Test
    void testGetById() throws Exception {

        LocalDate date = LocalDate.of(2026, 9, 10);

        OccuperId id = new OccuperId(
                "P001",
                "S001",
                date
        );

        Occuper occupation = new Occuper();
        occupation.setId(id);

        when(occuperService.getById(any(OccuperId.class)))
                .thenReturn(occupation);

        mockMvc.perform(
                MockMvcRequestBuilders.get(
                        "/api/occupations/P001/S001/2026-09-10"
                )
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.id.codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.id.codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.id.date").value("2026-09-10"));
    }


    @Test
    void testCreate() throws Exception {

        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        Occuper occupation = new Occuper();
        occupation.setId(id);

        when(occuperService.create(any(OccuperRequest.class)))
                .thenReturn(occupation);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/occupations")
                        .contentType("application/json")
                        .content("""
                            {
                                "codeprof": "P001",
                                "codesal": "S001",
                                "date": "2026-09-10"
                            }
                        """)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath(
                "$.date").value("2026-09-10"));
    }


    @Test
    void testDelete() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete(
                        "/api/occupations/P001/S001/2026-09-10"
                )
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(occuperService)
                .delete(any(OccuperId.class));
    }
}