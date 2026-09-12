package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.service.SalleService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(SalleController.class)
class SalleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalleService salleService;

    @Test
    void testGetAll() throws Exception {

        Salle salle1 = new Salle();
        salle1.setCodesal("S001");
        salle1.setDesignation("Salle Informatique");

        Salle salle2 = new Salle();
        salle2.setCodesal("S002");
        salle2.setDesignation("Salle de Mathématiques");

        when(salleService.getAll())
                .thenReturn(List.of(salle1, salle2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/salles")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].designation")
                .value("Salle Informatique"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].codesal").value("S002"));
    }

    @Test
    void testGetById() throws Exception {

        Salle salle = new Salle();
        salle.setCodesal("S001");
        salle.setDesignation("Salle Informatique");

        when(salleService.getById("S001"))
                .thenReturn(salle);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/salles/S001")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.designation")
                .value("Salle Informatique"));
    }

    @Test
    void testCreate() throws Exception {

        Salle salle = new Salle();
        salle.setCodesal("S003");
        salle.setDesignation("Salle de Physique");

        when(salleService.save(any(Salle.class)))
                .thenReturn(salle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/salles")
                        .contentType("application/json")
                        .content("""
                            {
                                "codesal": "S003",
                                "designation": "Salle de Physique"
                            }
                        """)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codesal").value("S003"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.designation")
                .value("Salle de Physique"));
    }

    @Test
    void testUpdate() throws Exception {

        Salle salle = new Salle();
        salle.setCodesal("S001");
        salle.setDesignation("Salle Informatique Modifiée");

        when(salleService.save(any(Salle.class)))
                .thenReturn(salle);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/salles/S001")
                        .contentType("application/json")
                        .content("""
                            {
                                "codesal": "S999",
                                "designation": "Salle Informatique Modifiée"
                            }
                        """)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codesal").value("S001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.designation")
                .value("Salle Informatique Modifiée"));

        Mockito.verify(salleService)
                .save(any(Salle.class));
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/salles/S001")
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(salleService)
                .delete("S001");
    }
}