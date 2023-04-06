package edu.ucalgary.oop;

public class LogEntry {
    private final String task;
    private final String quantity;
    private final int timeSpent;
    private final int timeAvailable;

    public LogEntry(String task, String quantity, int timeSpent, int timeAvailable) {
        this.task = task;
        this.quantity = quantity;
        this.timeSpent = timeSpent;
        this.timeAvailable = timeAvailable;
    }

    public String getTask() {
        return task;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public int getTimeAvailable() {
        return timeAvailable;
    }
}