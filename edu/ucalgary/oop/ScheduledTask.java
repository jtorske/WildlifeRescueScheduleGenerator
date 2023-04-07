package edu.ucalgary.oop;

public class ScheduledTask {
    private int hour;
    private String description;
    private TaskType type;
    private String animalNickname;
    private String species;
    private int animalCount;

    private int duration; // Add a duration field to store task duration

    // Modify constructors to include duration parameter
    public ScheduledTask(int hour, String description, TaskType taskType, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.duration = duration;
    }

    public ScheduledTask(int hour, String description, TaskType taskType, String species, String animalNickname, int animalCount, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.species = species;
        this.animalNickname = animalNickname;
        this.animalCount = animalCount;
        this.duration = duration;
    }
    public ScheduledTask(int hour, String description, TaskType taskType, String animalNickname, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.animalNickname = animalNickname;
        this.duration = duration;
    }
    public ScheduledTask(int hour, String description, TaskType taskType, int duration, String animalNickname) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.duration = duration;
        this.animalNickname = animalNickname;
    }
    public String getSpecies() {
        return species;
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
        String taskDetails = (animalCount > 0 ? " - " + species + " (" + animalCount + "): " + animalNickname : "");
        return formattedTime + " * " + description + taskDetails;
    }

    public int getDuration() {
        return duration;
    }
}