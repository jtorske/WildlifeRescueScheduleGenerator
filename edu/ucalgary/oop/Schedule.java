package edu.ucalgary.oop;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

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

    public void printSchedule() {
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
    }

}