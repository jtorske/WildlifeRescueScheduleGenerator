package edu.ucalgary.oop;

public class MedicalTask {
 
    private final Integer TASKID;
    private String description;
    private int duration;
    private int maxWindow;

    public MedicalTask(Integer taskID, String description, int duration, int maxWindow) {
        this.TASKID = taskID;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
    }

    /**
     * Sets the description of the medical task.
     * @param description the description of the medical task.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets the duration of the medical task.
     * @param duration the duration of the medical task.
     */
    public void setDuration(int duration){
        this.duration = duration;
    }

    /**
     * Gets the ID of the medical task.
     * @return the ID of the medical task.
     */
    public Integer getTaskID() {
        return TASKID;
    }

    /**
     * Gets the description of the medical task.
     * @return the description of the medical task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the duration of the medical task.
     * @return the duration of the medical task.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the maxWindow of the medical task.
     * @return the maxWindow of the medical task.
     */
    public int getMaxWindow() {
        return maxWindow;
    }
}