package com.ProjetTWBAV.GestionSalleDeClasse.service;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Salle;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.SalleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalleServiceTest {

    @Mock
    private SalleRepository salleRepository;

    @InjectMocks
    private SalleService salleService;

    private Salle salle1;
    private Salle salle2;

    @BeforeEach
    void setUp() {

        salle1 = new Salle();
        salle1.setCodesal("S001");
        salle1.setDesignation("Salle Informatique");

        salle2 = new Salle();
        salle2.setCodesal("S002");
        salle2.setDesignation("Salle de cours");
    }

    @Test
    void testGetAll() {

        List<Salle> salles = Arrays.asList(salle1, salle2);

        when(salleRepository.findAll()).thenReturn(salles);

        List<Salle> resultat = salleService.getAll();

        assertNotNull(resultat);
        assertEquals(2, resultat.size());

        assertEquals("S001", resultat.get(0).getCodesal());
        assertEquals("Salle Informatique",
                resultat.get(0).getDesignation());

        assertEquals("S002", resultat.get(1).getCodesal());
        assertEquals("Salle de cours",
                resultat.get(1).getDesignation());

        verify(salleRepository, times(1)).findAll();
    }

    @Test
    void testGetById_SalleExiste() {

        when(salleRepository.findById("S001"))
                .thenReturn(Optional.of(salle1));

        Salle resultat = salleService.getById("S001");

        assertNotNull(resultat);
        assertEquals("S001", resultat.getCodesal());
        assertEquals("Salle Informatique",
                resultat.getDesignation());

        verify(salleRepository, times(1))
                .findById("S001");
    }

    @Test
    void testGetById_SalleInexistante() {

        when(salleRepository.findById("S999"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> salleService.getById("S999")
        );

        assertEquals(
                "Salle introuvable",
                exception.getMessage()
        );

        verify(salleRepository, times(1))
                .findById("S999");
    }

    @Test
    void testSave() {

        when(salleRepository.save(salle1))
                .thenReturn(salle1);

        Salle resultat = salleService.save(salle1);

        assertNotNull(resultat);
        assertEquals("S001", resultat.getCodesal());
        assertEquals(
                "Salle Informatique",
                resultat.getDesignation()
        );

        verify(salleRepository, times(1))
                .save(salle1);
    }

    @Test
    void testDelete() {

        doNothing()
                .when(salleRepository)
                .deleteById("S001");

        salleService.delete("S001");

        verify(salleRepository, times(1))
                .deleteById("S001");
    }
}