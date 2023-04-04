package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestClass {
    @Test
    public void testAnimalConstructor() {
        Animal animal = new Animal(1, "Fido", "dog");
        assertEquals(1, animal.getAnimalID().intValue());
        assertEquals("Fido", animal.getAnimalNickname());
        assertEquals("dog", animal.getSpecies());
        assertFalse(animal.getIsOrphaned());
    }

    @Test
    public void testOrphanedAnimal() {
        Animal animal = new Animal(2, "Bambi", "deer");
        assertTrue(animal.getIsOrphaned());
        assertNull(animal.getActiveTime());
        assertNull(animal.getMeal());
    }

    @Test
    public void testNonOrphanedCoyote() {
        Animal animal = new Animal(3, "Wile E.", "coyote");
        assertFalse(animal.getIsOrphaned());
        assertEquals(ActiveTimes.CREPUSCULAR, animal.getActiveTime());
        assertEquals(new Feeding(5, 10), animal.getMeal());
    }

    @Test
    public void testNonOrphanedPorcupine() {
        Animal animal = new Animal(4, "Prickly Pete", "porcupine");
        assertFalse(animal.getIsOrphaned());
        assertEquals(ActiveTimes.CREPUSCULAR, animal.getActiveTime());
        assertEquals(new Feeding(5, 0), animal.getMeal());
    }

    @Test
    public void testNonOrphanedFox() {
        Animal animal = new Animal(5, "Red", "fox");
        assertFalse(animal.getIsOrphaned());
        assertEquals(ActiveTimes.NOCTURNAL, animal.getActiveTime());
        assertEquals(new Feeding(5, 5), animal.getMeal());
    }

    @Test
    public void testNonOrphanedRaccoon() {
        Animal animal = new Animal(6, "Rocky", "raccoon");
        assertFalse(animal.getIsOrphaned());
        assertEquals(ActiveTimes.NOCTURNAL, animal.getActiveTime());
        assertEquals(new Feeding(5, 0), animal.getMeal());
    }

    @Test
    public void testNonOrphanedBeaver() {
        Animal animal = new Animal(7, "Busy", "beaver");
        assertFalse(animal.getIsOrphaned());
        assertEquals(ActiveTimes.DIURNAL, animal.getActiveTime());
        assertEquals(new Feeding(5, 0), animal.getMeal());
    }
}
