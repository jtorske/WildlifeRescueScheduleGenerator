package edu.ucalgary.oop;

public class Treatments {
    private int animalID;
    private int taskID;
    private int startHour;

    public Treatments(int animalID, int taskID, int startHour) {
        this.animalID = animalID;
        this.taskID = taskID;
        this.startHour = startHour;
    }

    public int getAnimalID() {
        return animalID;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getStartHour() {
        return startHour;
    }
}
