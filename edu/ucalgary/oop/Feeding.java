package edu.ucalgary.oop;

// Missing comments for this class.
public class Feeding {
    private final int prepTime;
    private final int durationPerAnimal;
    private int numberOfAnimals;
    private int maxWindow = 3;

    // Called in Animal.java constructor.
    public Feeding( int durationPerAnimal, int prepTime  ){
        this.prepTime = prepTime;
        this.durationPerAnimal = durationPerAnimal;
    }

    public void setMaxWindow( int maxWindow ){
        this.maxWindow = maxWindow;
    }

    public int getPrepTime(){
        return this.prepTime;
    }

    public int getDurationPerAnimal(){
        return this.durationPerAnimal;
    }

    public int getNumberOfAnimals(){
        return numberOfAnimals;
    }

    public int getMaxWindow(){
        return maxWindow;
    }

}
