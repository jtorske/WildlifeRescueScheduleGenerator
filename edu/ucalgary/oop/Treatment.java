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
