package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestClass {
    private Animal animal1 = new Animal(1, "Fido", "dog");
    private Animal animal2 = new Animal(2, "Joe, Bambi", "dog");

    @Test
    public void testAnimalConstructor() {
        assertEquals(1, animal1.getAnimalID().intValue());
        assertEquals("Fido", animal1.getAnimalNickname());
        assertEquals("dog", animal1.getSpecies());
        assertFalse(animal1.getIsOrphaned());
    }

    @Test
    public void testOrphanedAnimalTrue() {
        assertTrue(animal2.getIsOrphaned());
        assertNull(animal2.getActiveTime());
        assertNull(animal2.getMeal());
    }

    @Test
    public void testOrphanedAnimalFalse() {
        assertFalse(animal1.getIsOrphaned());
        assertNull(animal1.getActiveTime());
        assertNull(animal1.getMeal());
    }

    @Test
    public void testCageConstructor() {
        Cage cage = new Cage(1, "raccoon");
        assertEquals(1, cage.getCageID().intValue());
        assertEquals("raccoon", cage.getSpecies());
        assertEquals(5, cage.getCleaningTime());
    }

    @Test
    public void testCageCleaningTimeForPorcupine() {
        Cage cage = new Cage(2, "porcupine");
        assertEquals(2, cage.getCageID().intValue());
        assertEquals("porcupine", cage.getSpecies());
        assertEquals(10, cage.getCleaningTime());
    }

    @Test
    public void testFeedingConstructor() {
        Feeding feeding = new Feeding(5, 10);
        assertEquals(10, feeding.getPrepTime());
        assertEquals(5, feeding.getDurationPerAnimal());
        assertEquals(0, feeding.getNumberOfAnimals());
        assertEquals(3, feeding.getMaxWindow());
    }

    @Test
    public void testSetMaxWindow() {
        Feeding feeding = new Feeding(5, 10);
        feeding.setMaxWindow(4);
        assertEquals(4, feeding.getMaxWindow());
    }
}
