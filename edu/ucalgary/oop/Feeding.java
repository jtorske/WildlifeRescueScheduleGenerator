package edu.ucalgary.oop;

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

    public int getFeedingHour(String activeTime) {
        int feedingHour;

        switch (activeTime) {
            case "NOCTURNAL":
                feedingHour = 20; // 8 PM
                break;
            case "DIURNAL":
                feedingHour = 9; // 9 AM
                break;
            case "CREPUSCULAR":
                feedingHour = 18; // 6 PM
                break;
            default:
                feedingHour = 0; // Default value, should not be reached
        }

        return feedingHour;
    }

}
