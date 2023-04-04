package edu.ucalgary.oop;

public class Cage {
    private final Integer CAGEID;
    private final String ANIMALSPECIES;
    private int cleaningTime = 5;

    /**
     * Constructor of Cage, sets all initial field values for the cage.
     * @param cageID  An (Integer) ID of the cage.
     * @param animalSpecies Species of the animal in the cage.
     */
    public Cage(Integer cageID, String animalSpecies) {
        this.CAGEID = cageID;
        this.ANIMALSPECIES = animalSpecies;

        if (animalSpecies.equalsIgnoreCase("porcupine")) {
            this.cleaningTime = 10;
        }
    }

    /**
     * Gets the cleaning time of the cage.
     * @return the cleaning time of the cage.
     */
    public int getCleaningTime() {
        return cleaningTime;
    }

    /**
     * Gets the species of the animal in the cage.
     * @return the species of the animal in the cage.
     */
    public String getSpecies() {
        return this.ANIMALSPECIES;
    }

    /**
     * Gets the ID of the cage.
     * @return the ID of the cage.
     */
    public Integer getCageID() {
        return this.CAGEID;
    }
}