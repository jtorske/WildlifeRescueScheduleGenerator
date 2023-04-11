/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 */
package edu.ucalgary.oop;

/**
 * Each individual Treatment in the Treaments table is a Treatment object.
 */
public class Treatment {
    private final Integer TREATMENTID;
    private Integer animalID;
    private Integer taskID;
    private int startHour;

    @Override
    public String toString() {
        return "TreatmentID: " + TREATMENTID + ", AnimalID: " + animalID + ", TaskID: " + taskID + ", StartHour: "
                + startHour;
    }

    public Treatment(Integer treatmentID, Integer animalID, Integer taskID, int startHour) {
        if (treatmentID == null || treatmentID < 1) {
            throw new IllegalArgumentException("Treatment ID must be a positive integer.");
        }
        if (animalID == null || animalID < 1) {
            throw new IllegalArgumentException("Animal ID must be a positive integer.");
        }
        if (taskID == null || taskID < 1) {
            throw new IllegalArgumentException("Task ID must be a positive integer.");
        }
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23.");
        }
        this.TREATMENTID = treatmentID;
        this.animalID = animalID;
        this.taskID = taskID;
        this.startHour = startHour;

        // relate the IDs together using the ConnectIDs class.
        ConnectIDs.linkIDs(animalID, taskID);
    }

    public Integer getTreatmentID() {
        return this.TREATMENTID;
    }

    public Integer getAnimalID() {
        return this.animalID;
    }

    public Integer getTaskID() {
        return this.taskID;
    }

    public int getStartHour() {
        return this.startHour;
    }
}
