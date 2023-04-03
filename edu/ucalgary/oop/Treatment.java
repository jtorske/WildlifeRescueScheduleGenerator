package edu.ucalgary.oop;


/**
 * Each individual Treatment in the Treaments table is a Treatment object.
 */
public class Treatment {
    private Integer animalID;
    private Integer taskID;
    private int startHour;

    public Treatment(Integer animalID, Integer taskID, int startHour) {
        this.animalID = animalID;
        this.taskID = taskID;
        this.startHour = startHour;

        // relate the IDs together using the ConnectIDs class.
        ConnectIDs.linkIDs( animalID, taskID );
    }

    public Integer getAnimalID() {
        return animalID;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public int getStartHour() {
        return startHour;
    }
}
