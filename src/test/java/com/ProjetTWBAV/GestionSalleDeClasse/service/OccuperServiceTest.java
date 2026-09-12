package com.ProjetTWBAV.GestionSalleDeClasse.service;

import com.ProjetTWBAV.GestionSalleDeClasse.dto.OccuperRequest;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Occuper;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.OccuperId;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.OccuperRepository;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.ProfRepository;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.SalleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OccuperServiceTest {

    @Mock
    private OccuperRepository occuperRepository;

    @Mock
    private ProfRepository profRepository;

    @Mock
    private SalleRepository salleRepository;

    @InjectMocks
    private OccuperService occuperService;

    private Prof prof;
    private Salle salle;
    private OccuperId occupationId;
    private Occuper occupation;

    @BeforeEach
    void setUp() {

        prof = new Prof();
        prof.setCodeprof("P001");
        prof.setNom("Dupont");
        prof.setPrenom("Jean");
        prof.setGrade("Professeur");

        salle = new Salle();
        salle.setCodesal("S001");
        salle.setDesignation("Salle Informatique");

        occupationId = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 8)
        );

        occupation = new Occuper();
        occupation.setId(occupationId);
        occupation.setProf(prof);
        occupation.setSalle(salle);
    }

    @Test
    void testGetAll() {

        List<Occuper> occupations =
                Arrays.asList(occupation);

        when(occuperRepository.findAll())
                .thenReturn(occupations);

        List<Occuper> resultat =
                occuperService.getAll();

        assertNotNull(resultat);
        assertEquals(1, resultat.size());

        assertEquals(
                "P001",
                resultat.get(0).getId().getCodeprof()
        );

        assertEquals(
                "S001",
                resultat.get(0).getId().getCodesal()
        );

        verify(occuperRepository, times(1))
                .findAll();
    }

    @Test
    void testGetById_OccupationExiste() {

        when(occuperRepository.findById(occupationId))
                .thenReturn(Optional.of(occupation));

        Occuper resultat =
                occuperService.getById(occupationId);

        assertNotNull(resultat);

        assertEquals(
                occupationId,
                resultat.getId()
        );

        assertEquals(
                "P001",
                resultat.getProf().getCodeprof()
        );

        assertEquals(
                "S001",
                resultat.getSalle().getCodesal()
        );

        verify(occuperRepository, times(1))
                .findById(occupationId);
    }

    @Test
    void testGetById_OccupationInexistante() {

        when(occuperRepository.findById(occupationId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> occuperService.getById(occupationId)
        );

        assertEquals(
                "Occupation introuvable",
                exception.getMessage()
        );

        verify(occuperRepository, times(1))
                .findById(occupationId);
    }

    @Test
    void testCreate_Succes() {

        OccuperRequest request = new OccuperRequest();

        request.setCodeprof("P001");
        request.setCodesal("S001");
        request.setDate(
                LocalDate.of(2026, 9, 8)
        );

        when(profRepository.findById("P001"))
                .thenReturn(Optional.of(prof));

        when(salleRepository.findById("S001"))
                .thenReturn(Optional.of(salle));

        when(occuperRepository.save(any(Occuper.class)))
                .thenReturn(occupation);

        Occuper resultat =
                occuperService.create(request);

        assertNotNull(resultat);

        assertEquals(
                "P001",
                resultat.getId().getCodeprof()
        );

        assertEquals(
                "S001",
                resultat.getId().getCodesal()
        );

        assertEquals(
                LocalDate.of(2026, 9, 8),
                resultat.getId().getDate()
        );

        assertEquals(
                prof,
                resultat.getProf()
        );

        assertEquals(
                salle,
                resultat.getSalle()
        );

        verify(profRepository, times(1))
                .findById("P001");

        verify(salleRepository, times(1))
                .findById("S001");

        verify(occuperRepository, times(1))
                .save(any(Occuper.class));
    }

    @Test
    void testCreate_ProfesseurInexistant() {

        OccuperRequest request = new OccuperRequest();

        request.setCodeprof("P999");
        request.setCodesal("S001");
        request.setDate(
                LocalDate.of(2026, 9, 8)
        );

        when(profRepository.findById("P999"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> occuperService.create(request)
        );

        assertEquals(
                "Professeur introuvable",
                exception.getMessage()
        );

        verify(profRepository, times(1))
                .findById("P999");

        verify(salleRepository, never())
                .findById(anyString());

        verify(occuperRepository, never())
                .save(any(Occuper.class));
    }

    @Test
    void testCreate_SalleInexistante() {

        OccuperRequest request = new OccuperRequest();

        request.setCodeprof("P001");
        request.setCodesal("S999");
        request.setDate(
                LocalDate.of(2026, 9, 8)
        );

        when(profRepository.findById("P001"))
                .thenReturn(Optional.of(prof));

        when(salleRepository.findById("S999"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> occuperService.create(request)
        );

        assertEquals(
                "Salle introuvable",
                exception.getMessage()
        );

        verify(profRepository, times(1))
                .findById("P001");

        verify(salleRepository, times(1))
                .findById("S999");

        verify(occuperRepository, never())
                .save(any(Occuper.class));
    }

    @Test
    void testDelete() {

        doNothing()
                .when(occuperRepository)
                .deleteById(occupationId);

        occuperService.delete(occupationId);

        verify(occuperRepository, times(1))
                .deleteById(occupationId);
    }
}