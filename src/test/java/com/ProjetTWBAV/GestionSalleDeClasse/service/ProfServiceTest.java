package com.ProjetTWBAV.GestionSalleDeClasse.service;

import com.ProjetTWBAV.GestionSalleDeClasse.entity.Prof;
import com.ProjetTWBAV.GestionSalleDeClasse.repository.ProfRepository;

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
class ProfServiceTest {

    @Mock
    private ProfRepository profRepository;

    @InjectMocks
    private ProfService profService;

    private Prof prof1;
    private Prof prof2;

    @BeforeEach
    void setUp() {

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
    }

    @Test
    void testGetAll() {

        List<Prof> professeurs = Arrays.asList(prof1, prof2);

        when(profRepository.findAll()).thenReturn(professeurs);

        List<Prof> resultat = profService.getAll();

        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals("P001", resultat.get(0).getCodeprof());
        assertEquals("P002", resultat.get(1).getCodeprof());

        verify(profRepository, times(1)).findAll();
    }

    @Test
    void testGetById_ProfExiste() {

        when(profRepository.findById("P001"))
                .thenReturn(Optional.of(prof1));

        Prof resultat = profService.getById("P001");

        assertNotNull(resultat);
        assertEquals("P001", resultat.getCodeprof());
        assertEquals("Dupont", resultat.getNom());

        verify(profRepository, times(1)).findById("P001");
    }

    @Test
    void testGetById_ProfInexistant() {

        when(profRepository.findById("P999"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> profService.getById("P999")
        );

        assertEquals("Professeur introuvable", exception.getMessage());

        verify(profRepository, times(1)).findById("P999");
    }

    @Test
    void testSave() {

        when(profRepository.save(prof1))
                .thenReturn(prof1);

        Prof resultat = profService.save(prof1);

        assertNotNull(resultat);
        assertEquals("P001", resultat.getCodeprof());
        assertEquals("Dupont", resultat.getNom());

        verify(profRepository, times(1)).save(prof1);
    }

    @Test
    void testDelete() {

        doNothing().when(profRepository).deleteById("P001");

        profService.delete("P001");

        verify(profRepository, times(1)).deleteById("P001");
    }

    @Test
    void testSearchByNomOrCode() {

        List<Prof> professeurs = Arrays.asList(prof1);

        when(profRepository.search("Dupont"))
                .thenReturn(professeurs);

        List<Prof> resultat =
                profService.searchByNomOrCode("Dupont");

        assertNotNull(resultat);
        assertEquals(1, resultat.size());
        assertEquals("Dupont", resultat.get(0).getNom());

        verify(profRepository, times(1))
                .search("Dupont");
    }
}