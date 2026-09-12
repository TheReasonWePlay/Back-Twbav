package com.ProjetTWBAV.GestionSalleDeClasse.repository;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SalleRepositoryTest {

    @Autowired
    private SalleRepository salleRepository;

    @BeforeEach
    void setUp() {
        salleRepository.deleteAll();

        Salle salle1 = new Salle();
        salle1.setCodesal("S001");
        salle1.setDesignation("Salle Informatique");

        Salle salle2 = new Salle();
        salle2.setCodesal("S002");
        salle2.setDesignation("Salle de Mathématiques");

        Salle salle3 = new Salle();
        salle3.setCodesal("S003");
        salle3.setDesignation("Salle de Physique");

        salleRepository.save(salle1);
        salleRepository.save(salle2);
        salleRepository.save(salle3);
    }

    @Test
    void testFindAll() {
        List<Salle> resultats = salleRepository.findAll();

        assertEquals(3, resultats.size());
    }

    @Test
    void testFindById() {
        Optional<Salle> resultat = salleRepository.findById("S001");

        assertTrue(resultat.isPresent());
        assertEquals("Salle Informatique", resultat.get().getDesignation());
    }

    @Test
    void testFindByIdInexistant() {
        Optional<Salle> resultat = salleRepository.findById("S999");

        assertFalse(resultat.isPresent());
    }

    @Test
    void testSave() {
        Salle salle = new Salle();
        salle.setCodesal("S004");
        salle.setDesignation("Salle de Réunion");

        Salle resultat = salleRepository.save(salle);

        assertNotNull(resultat);
        assertEquals("S004", resultat.getCodesal());
        assertEquals("Salle de Réunion", resultat.getDesignation());

        assertEquals(4, salleRepository.count());
    }

    @Test
    void testDeleteById() {
        salleRepository.deleteById("S001");

        Optional<Salle> resultat = salleRepository.findById("S001");

        assertFalse(resultat.isPresent());
        assertEquals(2, salleRepository.count());
    }
}

