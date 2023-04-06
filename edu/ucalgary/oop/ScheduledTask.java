package edu.ucalgary.oop;

public class ScheduledTask {
    private int hour;
    private String description;
    private TaskType type;
    private String animalNickname;
    private int animalCount;

    public ScheduledTask(int hour, String description, TaskType taskType) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
    }

    public ScheduledTask(int hour, String description, TaskType taskType, String animalNickname, int animalCount) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.animalNickname = animalNickname;
        this.animalCount = animalCount;
    }

    public ScheduledTask(int hour, String description, TaskType taskType, int animalCount) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.animalCount = animalCount;
    }

    public TaskType getTaskType() {
        return type;
    }

    public String getAnimalNickname() {
        return animalNickname;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public int getHour() {
        return hour;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String formattedTime = String.format("%02d:00", hour);
        return formattedTime + (animalCount > 0 ? " (" + animalCount + ")" : "") + " - " + description;
    }
}
