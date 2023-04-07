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
        tasks.sort(Comparator.comparingInt(ScheduledTask::getHour));
    }

    public int getTotalTaskDurationForHour(int hour) {
        int totalDuration = 0;
        for (ScheduledTask task : tasks) {
            if (task.getHour() == hour) {
                totalDuration += task.getDuration(); // Assumes you have a getDuration() method in the ScheduledTask class
            }
        }
        return totalDuration;
    }

    public void printSchedule() {
        System.out.println("Schedule:");
        for (int i = 0; i < 24; i++) {
            System.out.println("Hour " + i + ":");
            for (ScheduledTask task : tasks) {
                if (task.getHour() == i) {
                    String taskDescription = task.getDescription() + " (" + task.getTaskType().toString() + ")";
                    if (task.getTaskType() == TaskType.TREATMENT) {
                        taskDescription += " - " + task.getAnimalNickname();
                    }
                    System.out.println(taskDescription);
                }
            }
        }
    }
}