package com.ProjetTWBAV.GestionSalleDeClasse.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OccuperIdTest {

    @Test
    void equalsSameObject() {
        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertEquals(id, id);
    }

    @Test
    void equalsNull() {
        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertNotEquals(null, id);
    }

    @Test
    void equalsDifferentType() {
        OccuperId id = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertNotEquals(id, "P001");
    }

    @Test
    void equalsSameValues() {
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertEquals(id1, id2);
        assertEquals(id2, id1);
    }

    @Test
    void equalsDifferentCodeprof() {
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P002",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertNotEquals(id1, id2);
    }

    @Test
    void equalsDifferentCodesal() {
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P001",
                "S002",
                LocalDate.of(2026, 9, 10)
        );

        assertNotEquals(id1, id2);
    }

    @Test
    void equalsDifferentDate() {
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

        assertNotEquals(id1, id2);
    }

    @Test
    void hashCodeSameValues() {
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void hashCodeDifferentValues() {
        OccuperId id1 = new OccuperId(
                "P001",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        OccuperId id2 = new OccuperId(
                "P002",
                "S001",
                LocalDate.of(2026, 9, 10)
        );

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }
}