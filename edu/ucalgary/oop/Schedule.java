package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
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
                totalDuration += task.getDuration(); // Assumes you have a getDuration() method in the ScheduledTask
                                                     // class
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
    }
}