package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private ArrayList<ScheduledTask> tasks;

    public List<ScheduledTask> getScheduledTasks() {
        return tasks;
    }

    public Schedule() {
        tasks = new ArrayList<>();
    }

    public void addTask(ScheduledTask task) {
        tasks.add(task);
        tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));
    }

    public int getTotalTaskDurationForHour(int hour) {
        int totalDuration = 0;
        for (ScheduledTask task : tasks) {
            if (task.getHour() == hour) {
                totalDuration += task.getDuration();

            }
        }
        return totalDuration;
    }

    public boolean containsFeedingForAnimal(String animalNickname) {
        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == TaskType.FEEDING && task.getAnimalNickname().contains(animalNickname)) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfFeedingsForSpeciesAndHour(String species, int hour) {
        int count = 0;
        for (ScheduledTask task : tasks) {
            if (task.getTaskType() == TaskType.FEEDING && task.getSpecies().equals(species) && task.getHour() == hour) {
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
                System.out.printf("%02d:00 - %s - %s (%d: %s): %d\n", task.getHour(), task.getDescription(),
                        task.getSpecies(),
                        task.getAnimalCount(), task.getAnimalNickname(), task.getDuration());
            } else {
                System.out.printf("%02d:00 - %s (%s): %d\n", task.getHour(), task.getDescription(),
                        task.getAnimalNickname(), task.getDuration());
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("schedule.txt"));
            writer.printf("Schedule for: %s\n", currentDateFormatted);
            writer.println("--------------------------------------------------");
            tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));

            for (ScheduledTask task : tasks) {
                if (task.getTaskType() == TaskType.FEEDING) {
                    writer.printf("%02d:00 - %s - %s (%d: %s): %d\n", task.getHour(), task.getDescription(),
                            task.getSpecies(),
                            task.getAnimalCount(), task.getAnimalNickname(), task.getDuration());
                } else {
                    writer.printf("%02d:00 - %s (%s): %d\n", task.getHour(), task.getDescription(),
                            task.getAnimalNickname(), task.getDuration());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing schedule to file: " + e.getMessage());
        }
    }

}