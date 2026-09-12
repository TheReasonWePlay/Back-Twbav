package com.ProjetTWBAV.GestionSalleDeClasse.controller;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.service.ProfService;

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

@WebMvcTest(ProfController.class)
class ProfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfService profService;

    @Test
    void testGetAll() throws Exception {

        Prof prof1 = new Prof();
        prof1.setCodeprof("P001");
        prof1.setNom("Dupont");
        prof1.setPrenom("Jean");
        prof1.setGrade("Professeur");

        Prof prof2 = new Prof();
        prof2.setCodeprof("P002");
        prof2.setNom("Martin");
        prof2.setPrenom("Paul");
        prof2.setGrade("Maître de conférences");

        when(profService.getAll())
                .thenReturn(List.of(prof1, prof2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/profs")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Dupont"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].codeprof").value("P002"));
    }

    @Test
    void testGetById() throws Exception {

        Prof prof = new Prof();
        prof.setCodeprof("P001");
        prof.setNom("Dupont");
        prof.setPrenom("Jean");
        prof.setGrade("Professeur");

        when(profService.getById("P001"))
                .thenReturn(prof);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/profs/P001")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Dupont"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("Jean"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("Professeur"));
    }

    @Test
    void testCreate() throws Exception {

        Prof prof = new Prof();
        prof.setCodeprof("P003");
        prof.setNom("Durand");
        prof.setPrenom("Pierre");
        prof.setGrade("Professeur");

        when(profService.save(any(Prof.class)))
                .thenReturn(prof);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/profs")
                        .contentType("application/json")
                        .content("""
                            {
                                "codeprof": "P003",
                                "nom": "Durand",
                                "prenom": "Pierre",
                                "grade": "Professeur"
                            }
                        """)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codeprof").value("P003"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Durand"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("Pierre"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("Professeur"));
    }

    @Test
    void testUpdate() throws Exception {

        Prof prof = new Prof();
        prof.setCodeprof("P001");
        prof.setNom("Dupont");
        prof.setPrenom("Jean");
        prof.setGrade("Professeur");

        when(profService.save(any(Prof.class)))
                .thenReturn(prof);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/profs/P001")
                        .contentType("application/json")
                        .content("""
                            {
                                "codeprof": "P999",
                                "nom": "Dupont",
                                "prenom": "Jean",
                                "grade": "Professeur"
                            }
                        """)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Dupont"));

        Mockito.verify(profService)
                .save(any(Prof.class));
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/profs/P001")
        )
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(profService)
                .delete("P001");
    }

    @Test
    void testSearch() throws Exception {

        Prof prof = new Prof();
        prof.setCodeprof("P001");
        prof.setNom("Dupont");
        prof.setPrenom("Jean");
        prof.setGrade("Professeur");

        when(profService.searchByNomOrCode("Dupont"))
                .thenReturn(List.of(prof));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/profs/search")
                        .param("keyword", "Dupont")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].codeprof").value("P001"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Dupont"));
    }
}