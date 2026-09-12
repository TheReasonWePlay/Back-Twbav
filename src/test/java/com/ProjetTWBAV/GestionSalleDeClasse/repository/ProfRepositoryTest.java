package com.ProjetTWBAV.GestionSalleDeClasse.repository;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProfRepositoryTest {

    @Autowired
    private ProfRepository profRepository;

    @BeforeEach
    void setUp() {

        profRepository.deleteAll();

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

        Prof prof3 = new Prof();
        prof3.setCodeprof("P003");
        prof3.setNom("Durand");
        prof3.setPrenom("Pierre");
        prof3.setGrade("Professeur");

        profRepository.save(prof1);
        profRepository.save(prof2);
        profRepository.save(prof3);
    }

    @Test
    void testFindAll() {

        List<Prof> resultats = profRepository.findAll();

        assertEquals(3, resultats.size());
    }

    @Test
    void testSearchParCode() {

        List<Prof> resultats =
                profRepository.search("P001");

        assertEquals(1, resultats.size());
        assertEquals(
                "Dupont",
                resultats.get(0).getNom()
        );
    }

    @Test
    void testSearchParNom() {

        List<Prof> resultats =
                profRepository.search("Dupont");

        assertEquals(1, resultats.size());
        assertEquals(
                "P001",
                resultats.get(0).getCodeprof()
        );
    }

    @Test
    void testSearchParPrenom() {

        List<Prof> resultats =
                profRepository.search("Jean");

        assertEquals(1, resultats.size());
        assertEquals(
                "Dupont",
                resultats.get(0).getNom()
        );
    }

    @Test
    void testSearchSansResultat() {

        List<Prof> resultats =
                profRepository.search("Inexistant");

        assertNotNull(resultats);
        assertTrue(resultats.isEmpty());
    }

    @Test
    void testSearchSansRespecterLaCasse() {

        List<Prof> resultats =
                profRepository.search("DUPONT");

        assertEquals(1, resultats.size());
        assertEquals(
                "Dupont",
                resultats.get(0).getNom()
        );
    }
}