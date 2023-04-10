package edu.ucalgary.oop;
    /**
     * Represents a scheduled task in the animal clinic's schedule.
     */

public class ScheduledTask {
    private int hour;
    private String description;
    private TaskType type;
    private String animalNickname;
    private String species;
    private int animalCount;
    private boolean backupVolunteer;

    private int duration; 
    /**
     * Constructs a ScheduledTask with the provided hour, description, task type, and duration.
     * @param hour The hour of the scheduled task.
     * @param description The description of the scheduled task.
     * @param taskType The type of the scheduled task.
     * @param duration The duration of the scheduled task in minutes.
     */
    public ScheduledTask(int hour, String description, TaskType taskType, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.duration = duration;
    }

    /**
     * Constructs a ScheduledTask with the provided hour, description, task type, species, animal nickname, animal count, and duration.
     * @param hour The hour of the scheduled task.
     * @param description The description of the scheduled task.
     * @param taskType The type of the scheduled task.
     * @param species The species of the animal associated with the task.
     * @param animalNickname The nickname of the animal associated with the task.
     * @param animalCount The number of animals involved in the task.
     * @param duration The duration of the scheduled task in minutes.
     */

    public ScheduledTask(int hour, String description, TaskType taskType, String species, String animalNickname,
            int animalCount, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.species = species;
        this.animalNickname = animalNickname;
        this.animalCount = animalCount;
        this.duration = duration;
    }

    /**
     * Constructs a ScheduledTask with the provided hour, description, task type, animal nickname, and duration.
     * @param hour The hour of the scheduled task.
     * @param description The description of the scheduled task.
     * @param taskType The type of the scheduled task.
     * @param animalNickname The nickname of the animal associated with the task.
     * @param duration The duration of the scheduled task in minutes.
     */

    public ScheduledTask(int hour, String description, TaskType taskType, String animalNickname, int duration) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.animalNickname = animalNickname;
        this.duration = duration;
        this.backupVolunteer = false;

    }

    public boolean isBackupVolunteer() {
        return backupVolunteer;
    }

    /**
     * Sets the task to use a backup volunteer or not.
     * @param backupVolunteer true if the task should use a backup volunteer, false otherwise.
     */

    public void setBackupVolunteer(boolean backupVolunteer) {
        this.backupVolunteer = backupVolunteer;
    }

    /**
     * Constructs a ScheduledTask with the provided hour, description, task type, duration, and animal nickname.
     * @param hour The hour of the scheduled task.
     * @param description The description of the scheduled task.
     * @param taskType The type of the scheduled task.
     * @param duration The duration of the scheduled task in minutes.
     * @param animalNickname The nickname of the animal associated with the task.
     */

    public ScheduledTask(int hour, String description, TaskType taskType, int duration, String animalNickname) {
        this.hour = hour;
        this.description = description;
        this.type = taskType;
        this.duration = duration;
        this.animalNickname = animalNickname;
    }

    /**
     * Returns the species of the animal associated with the task.
     * @return The species of the animal.
     */

    public String getSpecies() {
        return species;
    }

        /**
     * Returns the task type of the scheduled task.
     * @return The task type.
     */

    public TaskType getTaskType() {
        return type;
    }
    /**
     * Returns the animal nickname of the animal associated with the task.
     * @return The animal nickname.
     */
    public String getAnimalNickname() {
        return animalNickname;
    }
    /**
     * Returns the number of animals involved in the task.
     * @return The animal count.
     */
    public int getAnimalCount() {
        return animalCount;
    }
    /**
     * Returns the hour of the scheduled task.
     * @return The hour.
     */
    public int getHour() {
        return hour;
    }
    /**
     * Returns the description of the scheduled task.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }
        /**
     * Returns a string representation of the ScheduledTask.
     * @return A string containing the task's hour, description, and animal details.
     */
    @Override
    public String toString() {
        String formattedTime = String.format("%02d:00", hour);
        String taskDetails = (animalCount > 0 ? " - " + species + " (" + animalCount + "): " + animalNickname : "");
        return formattedTime + " * " + description + taskDetails;
    }
    
    /**
     * Returns the duration of the scheduled task.
     * @return The duration in minutes.
     */
    public int getDuration() {
        return duration;
    }
}