package edu.ucalgary.oop;

import java.util.List;
import java.util.Arrays;

public class Feeding {
    private final int prepTime;
    private final int durationPerAnimal;
    private int numberOfAnimals;
    private int maxWindow = 3;

    /**
     * Constructs a Feeding object with the specified feeding duration per animal
     * and preparation time.
     * 
     * @param durationPerAnimal the feeding duration per animal
     * @param prepTime          the preparation time for feeding
     *                          note: This constructor is called in the Animal.java
     *                          constructor.
     */
    public Feeding(int durationPerAnimal, int prepTime) {
        this.prepTime = prepTime;
        this.durationPerAnimal = durationPerAnimal;
    }

    /**
     * Sets the maximum feeding window.
     * 
     * @param maxWindow the maximum feeding window
     */
    public void setMaxWindow(int maxWindow) {
        this.maxWindow = maxWindow;
    }

    /**
     * Returns the preparation time.
     * 
     * @return the preparation time
     */
    public int getPrepTime() {
        return this.prepTime;
    }

    /**
     * Returns the feeding duration per animal.
     * 
     * @return the feeding duration per animal
     */
    public int getDurationPerAnimal() {
        return this.durationPerAnimal;
    }

    /**
     * Returns the number of animals.
     * 
     * @return the number of animals
     */
    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    /**
     * Returns the maximum feeding window.
     * 
     * @return the maximum feeding window
     */
    public int getMaxWindow() {
        return maxWindow;
    }

    public List<Integer> getFeedingHours(String activeTime) {
        List<Integer> feedingHours;

        switch (activeTime) {
            case "NOCTURNAL":
                feedingHours = Arrays.asList(20, 21, 22); // 8 PM, 9 PM, 10 PM
                break;
            case "DIURNAL":
                feedingHours = Arrays.asList(9, 10, 11); // 9 AM, 10 AM, 11 AM
                break;
            case "CREPUSCULAR":
                feedingHours = Arrays.asList(18, 19, 20); // 6 PM, 7 PM, 8 PM
                break;
            default:
                feedingHours = Arrays.asList(0, 1, 2); // Default value, should not be reached
        }

        return feedingHours;
    }

}
