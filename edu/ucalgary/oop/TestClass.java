package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TestClass {
    private Animal animal1 = new Animal(1, "Fido", "dog");
    private Animal animal2 = new Animal(2, "Joe, Bambi", "dog");
    private Animal animal3 = new Animal(3, "ALEX", "porcupine");
    private Animal animal4 = new Animal(4, "ALEX", "coyote");


    @Test
    public void testAnimalConstructor() {
        assertEquals(1, animal1.getAnimalID().intValue());
        assertEquals("Fido", animal1.getAnimalNickname());
        assertEquals("dog", animal1.getSpecies());
        assertFalse(animal1.getIsOrphaned());
    }


    @Test
    public void testCageCleaningTimeForCoyote() {
        assertEquals(5, animal4.getCageCleaningDuration(animal4.getSpecies()));
    }

    @Test
    public void testCageCleaningTimeForPorcupine() {
        assertEquals(10, animal3.getCageCleaningDuration(animal3.getSpecies()));
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
