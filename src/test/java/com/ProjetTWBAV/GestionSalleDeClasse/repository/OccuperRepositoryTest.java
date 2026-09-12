package com.ProjetTWBAV.GestionSalleDeClasse.repository;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OccuperRepositoryTest {

    @Autowired
    private OccuperRepository occuperRepository;

    @Autowired
    private ProfRepository profRepository;

    @Autowired
    private SalleRepository salleRepository;

    private Prof prof1;
    private Prof prof2;

    private Salle salle1;
    private Salle salle2;

    private Occuper occupation1;
    private Occuper occupation2;

    @BeforeEach
    void setUp() {

        // Nettoyage
        occuperRepository.deleteAll();
        profRepository.deleteAll();
        salleRepository.deleteAll();

        // Création des professeurs
        prof1 = new Prof();
        prof1.setCodeprof("P001");
        prof1.setNom("Dupont");
        prof1.setPrenom("Jean");
        prof1.setGrade("Professeur");

        prof2 = new Prof();
        prof2.setCodeprof("P002");
        prof2.setNom("Martin");
        prof2.setPrenom("Paul");
        prof2.setGrade("Maître de conférences");

        profRepository.save(prof1);
        profRepository.save(prof2);

        // Création des salles
        salle1 = new Salle();
        salle1.setCodesal("S001");
        salle1.setDesignation("Salle Informatique");

        salle2 = new Salle();
        salle2.setCodesal("S002");
        salle2.setDesignation("Salle de Mathématiques");

        salleRepository.save(salle1);
        salleRepository.save(salle2);

        // Première occupation
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        occupation1 = new Occuper();
        occupation1.setId(id1);
        occupation1.setProf(prof1);
        occupation1.setSalle(salle1);

        // Deuxième occupation
        OccuperId id2 = new OccuperId(
                "P002",
                "S002",
                LocalDate.of(2026, 9, 11)
        );

        occupation2 = new Occuper();
        occupation2.setId(id2);
        occupation2.setProf(prof2);
        occupation2.setSalle(salle2);

        occuperRepository.save(occupation1);
        occuperRepository.save(occupation2);
    }

    @Test
    void testFindAll() {

        List<Occuper> resultats = occuperRepository.findAll();

        assertEquals(2, resultats.size());
    }

    @Test
    void testFindById() {

        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        Optional<Occuper> resultat = occuperRepository.findById(id);

        assertTrue(resultat.isPresent());

        Occuper occupation = resultat.get();

        assertEquals("P001", occupation.getId().getCodeprof());
        assertEquals("S001", occupation.getId().getCodesal());
        assertEquals(
                LocalDate.of(2026, 9, 10),
                occupation.getId().getDate()
        );
    }

    @Test
    void testFindByIdInexistant() {

        OccuperId id = new OccuperId(
                "P999",
                "S999",
                LocalDate.of(2026, 9, 20)
        );

        Optional<Occuper> resultat = occuperRepository.findById(id);

        assertFalse(resultat.isPresent());
    }

    @Test
    void testRelationsProfEtSalle() {

        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        Occuper occupation = occuperRepository.findById(id).orElseThrow();

        assertNotNull(occupation.getProf());
        assertNotNull(occupation.getSalle());

        assertEquals("P001", occupation.getProf().getCodeprof());
        assertEquals("Dupont", occupation.getProf().getNom());

        assertEquals("S001", occupation.getSalle().getCodesal());
        assertEquals("Salle Informatique",
                occupation.getSalle().getDesignation());
    }

    @Test
    void testSave() {

        OccuperId id = new OccuperId(
                "P001",
                "S002",
                LocalDate.of(2026, 9, 12)
        );

        Occuper occupation = new Occuper();
        occupation.setId(id);
        occupation.setProf(prof1);
        occupation.setSalle(salle2);

        Occuper resultat = occuperRepository.save(occupation);

        assertNotNull(resultat);
        assertEquals(id, resultat.getId());

        assertEquals(3, occuperRepository.count());
    }

    @Test
    void testDeuxDatesDifferentes() {

        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 11)
        );

        Occuper occupation = new Occuper();
        occupation.setId(id2);
        occupation.setProf(prof1);
        occupation.setSalle(salle1);

        occuperRepository.save(occupation);

        assertTrue(occuperRepository.findById(id1).isPresent());
        assertTrue(occuperRepository.findById(id2).isPresent());

        assertEquals(3, occuperRepository.count());
    }

    @Test
    void testDeleteById() {

        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        occuperRepository.deleteById(id);

        Optional<Occuper> resultat = occuperRepository.findById(id);

        assertFalse(resultat.isPresent());
        assertEquals(1, occuperRepository.count());
    }
}
