package edu.ucalgary.oop;

public class Task {
 
    private int taskID;
    private String description;
    private int duration;
    private int maxWindow;

    public Task(int taskID, String description, int duration, int maxWindow) {
        this.taskID = taskID;
        this.description = description;
        this.duration = duration;
        this.maxWindow = maxWindow;
    }


    public int getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getMaxWindow() {
        return maxWindow;
    }
}