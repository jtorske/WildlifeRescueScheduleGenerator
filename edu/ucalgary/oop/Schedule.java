/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 * @version 2.1
 */
package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Schedule class represents a schedule of tasks for a day.
 */
public class Schedule {
    private ArrayList<ScheduledTask> tasks;

    /**
     * Returns the list of scheduled tasks.
     * 
     * @return The list of scheduled tasks.
     */
    public List<ScheduledTask> getScheduledTasks() {
        return tasks;
    }

    /**
     * Constructs a new empty Schedule.
     */
    public Schedule() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the schedule and sorts the tasks by hour.
     * 
     * @param task The task to add to the schedule.
     */
    public void addTask(ScheduledTask task) {
        tasks.add(task);
        tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));
    }

    /**
     * Returns the total duration of tasks scheduled for a given hour.
     * 
     * @param hour The hour for which to calculate the total duration of tasks.
     * @return The total duration of tasks for the given hour.
     */
    public int getTotalTaskDurationForHour(int hour) {
        int totalDuration = 0;
        for (ScheduledTask task : tasks) {
            if (task.getHour() == hour) {
                totalDuration += task.getDuration();

            }
        }
        return totalDuration;
    }

    /**
     * Checks if the schedule contains a feeding task for a given animal.
     * 
     * @param animalNickname The nickname of the animal to check for a feeding task.
     * @return true if the schedule contains a feeding task for the given animal,
     *         false otherwise.
     */
    public boolean containsFeedingForAnimal(String animalNickname) {
        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == TaskType.FEEDING && task.getAnimalNickname().contains(animalNickname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of feedings for a given species and hour.
     * 
     * @param species The species for which to count feedings.
     * @param hour    The hour for which to count feedings.
     * @return The number of feedings for the given species and hour.
     */
    public int getNumberOfFeedingsForSpeciesAndHour(String species, int hour) {
        int count = 0;
        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == TaskType.FEEDING && task.getSpecies().equals(species) && task.getHour() == hour) {
                count++;
            }
        }
        return count;
    }

    /**
     * Prints the schedule to the console and writes it to a file.
     */

    public int getTaskCountForTaskType(TaskType taskType) {
        int count = 0;
        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == taskType) {
                count++;
            }
        }
        return count;
    }

    public void printSchedule() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate currentDate = LocalDate.now();
        String currentDateFormatted = currentDate.format(dateFormatter);

        System.out.printf("Schedule for: %s\n", currentDateFormatted);
        System.out.println("--------------------------------------------------");
        tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));

        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == TaskType.FEEDING) {
                System.out.printf("%02d:00 - %s - %s (%d: %s)\n", task.getHour(), task.getDescription(),
                        task.getSpecies(),
                        task.getAnimalCount(), task.getAnimalNickname());
            } else {
                System.out.printf("%02d:00 - %s (%s)\n", task.getHour(), task.getDescription(),
                        task.getAnimalNickname());
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("schedule.txt"));
            writer.printf("Schedule for: %s\n", currentDateFormatted);
            writer.println("--------------------------------------------------");
            tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));

            for (ScheduledTask task : tasks) {
                if (task.getTaskType() == TaskType.FEEDING) {
                    writer.printf("%02d:00 - %s - %s (%d: %s)\n", task.getHour(), task.getDescription(),
                            task.getSpecies(),
                            task.getAnimalCount(), task.getAnimalNickname());
                } else {
                    writer.printf("%02d:00 - %s (%s)\n", task.getHour(), task.getDescription(),
                            task.getAnimalNickname());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing schedule to file: " + e.getMessage());
        }
    }

}