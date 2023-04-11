/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 *  @version 1.2
 */
package edu.ucalgary.oop;

import java.util.regex.*;

public class Animal {
    private final Integer ANIMALID;
    private final String ANIMALSPECIES;
    private boolean isOrphaned = false;
    private String activeTime;
    private String animalNickname;
    private Feeding meal;
    private final Pattern PATTERNISORPHANED = Pattern.compile("^[a-zA-Z]+$");

    /**
     * Constructor of Animal, sets all initial field values for the animal.
     * 
     * @param animalID       An (Integer) ID of the animal.
     * @param animalNickname Name of the animal.
     * @param animalSpecies  Species of the animal.
     *                       Note:
     *                       You can convert an int to an Integer by using the
     *                       valueOf() method of the Integer class.
     */
    public Animal(Integer animalID, String animalNickname, String animalSpecies) {
        this.ANIMALID = animalID;
        this.ANIMALSPECIES = animalSpecies;
        this.animalNickname = animalNickname;
        meal = new Feeding(5, 0); // Set a default meal

        // check the pattern of the animal's name, if there are multiple names, set
        // isOphaned to true.
        Matcher matcher = this.PATTERNISORPHANED.matcher(this.animalNickname);
        if (!matcher.find()) {
            /**
             * If animal is Orphaned, do the following.
             */
            setIsOrphaned(true);
            // Set default activeTime for orphaned animals
            this.activeTime = "NOCTURNAL";
        } else {
            /**
             * If the animal is an not Orphaned, aka an adult,
             * do specific things based on the animal's species.
             */
            switch (this.ANIMALSPECIES) {
                case "coyote":
                    this.activeTime = "CREPUSCULAR";
                    meal = new Feeding(5, 10);
                    break;
                case "porcupine":
                    this.activeTime = "CREPUSCULAR";
                    meal = new Feeding(5, 0);
                    break;
                case "fox":
                    this.activeTime = "NOCTURNAL";
                    meal = new Feeding(5, 5);
                    break;
                case "raccoon":
                    this.activeTime = "NOCTURNAL";
                    meal = new Feeding(5, 0);
                    break;
                case "beaver":
                    this.activeTime = "DIURNAL";
                    meal = new Feeding(5, 0);
                    break;
                default:
                    // Set default activeTime for unspecified species
                    this.activeTime = "NOCTURNAL";
            }
        }
    }

    @Override
    public String toString() {
        return "AnimalID: " + ANIMALID + ", AnimalNickname: " + animalNickname + ", AnimalSpecies: " + ANIMALSPECIES;
    }

    /**
     * Gets the cage cleaning duration for the animal species.
     * 
     * @param species the species of the animal.
     * @return the duration in minutes for cleaning the cage of the species.
     */
    public int getCageCleaningDuration(String species) {
        if (species.equalsIgnoreCase("porcupine")) {
            return 10;
        } else {
            return 5;
        }
    }

    /**
     * Sets whether or not the animal is orphaned.
     * 
     * @param bool true if animal should be orphaned, false if animal should not be
     *             orphaned.
     */
    public void setIsOrphaned(boolean bool) {
        this.isOrphaned = bool;
    }


    /**
     * Gets the feeding object of the animal.
     * 
     * @return the feeding object of the animal.
     */
    public Feeding getMeal() {
        return this.meal;
    }

    /**
     * Gets the species of the animal.
     * 
     * @return the species of the animal.
     */
    public String getSpecies() {
        return this.ANIMALSPECIES;
    }

    /**
     * Gets the activeTime of the animal.
     * 
     * @return the activeTime of the animal.
     */

    public String getActiveTime() {
        return this.activeTime;
    }

    /**
     * Gets the ID of the animal.
     * 
     * @return the ID of the animal.
     */
    public Integer getAnimalID() {
        return this.ANIMALID;
    }

    /**
     * Gets the nickname of the animal.
     * 
     * @return the nickname of the animal.
     */
    public String getAnimalNickname() {
        return this.animalNickname;
    }

    /**
     * Gets the orphan status of the animal.
     * 
     * @return the orphan status of the animal.
     */
    public boolean getIsOrphaned() {
        return this.isOrphaned;
    }
}