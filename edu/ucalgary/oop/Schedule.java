package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.Comparator;

public class Schedule {
    private ArrayList<ScheduledTask> tasks;

    public Schedule() {
        tasks = new ArrayList<>();
    }

    public void addTask(ScheduledTask task) {
        tasks.add(task);
    }

    public void printSchedule() {
        System.out.println("Schedule:");
        tasks.sort(Comparator.comparing(ScheduledTask::getHour));
        int currentHour = -1;
        for (ScheduledTask task : tasks) {
            if (task.getHour() != currentHour) {
                if (currentHour != -1) {
                    System.out.println(); // Add a blank line between hours
                }
                currentHour = task.getHour();
                System.out.printf("%02d:00\n", currentHour);
            }
            String animalDetails = task.getAnimalCount() > 1
                    ? String.format("(%d: %s)", task.getAnimalCount(), task.getAnimalNickname())
                    : "";
            System.out.printf("* %s %s\n", task.getDescription(), animalDetails);
        }
    }

}
