/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 * @version 6.3
 */
package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JButton displayScheduleButton;
    private ConnectDatabase connectDatabase;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Finds a task in the list by its ID
     *
     * @param taskList the list of tasks to search
     * @param taskId   the ID of the task to find
     * @return the task with the given ID, or null if not found
     */
    private MedicalTask findTaskById(List<MedicalTask> taskList, Integer taskId) {
        for (MedicalTask task : taskList) {
            if (task.getTaskID().equals(taskId)) {
                return task;
            }
        }
        return null;
    }
    /**
     * Generates a schedule for treatments, feedings, and cage cleanings
     */
    public void generateSchedule() {

        ConnectDatabase connectDB = new ConnectDatabase();
        connectDB.createConnection();

        if (connectDB.createConnection()) {
            String message = "Database connection successful!";
            JOptionPane.showMessageDialog(null, message);
        } else if (!connectDB.createConnection()) {
            String message = "Database connection unsuccessful!";
            JOptionPane.showMessageDialog(null, message);
        }

        ArrayList<Animal> animalList = connectDB.selectAnimals();
        ArrayList<MedicalTask> taskList = connectDB.selectTasks();
        ArrayList<Treatment> treatmentList = connectDB.selectTreatments();

        Schedule schedule = new Schedule();

        //Add all treatments to the schedule
        //Adds them at there original times and stores the duration of the task and other details
        for (Treatment treatment : treatmentList) {
            int treatmentHour = treatment.getStartHour();
            MedicalTask associatedTask = findTaskById(taskList, treatment.getTaskID());
            String treatmentDescription = associatedTask != null ? associatedTask.getDescription()
                    : "Unknown treatment";
            Animal treatmentAnimal = findAnimalById(animalList, treatment.getAnimalID());
            String animalNickname = treatmentAnimal != null ? treatmentAnimal.getAnimalNickname() : "Unknown animal";

            int availableHour = findAvailableHourForTreatment(schedule, treatmentHour, associatedTask.getMaxWindow(),
                    associatedTask.getDuration());

            if (availableHour >= 0) {
                schedule.addTask(
                        new ScheduledTask(availableHour, treatmentDescription, TaskType.TREATMENT, animalNickname,
                                associatedTask.getDuration())); // Pass the correct duration here
            } else {
                promptManualReadjustment(treatmentDescription, treatment.getTaskID(), treatmentHour, schedule,
                        animalList, treatment, taskList);

            }
        }

        connectDB.close();

        //Filter out orphaned animals
        ArrayList<Animal> nonOrphanedAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            if (!animal.getIsOrphaned()) {
                nonOrphanedAnimals.add(animal);
            }
        }

        //Calculate feeding times and durations and stores them
        LinkedHashMap<String, ArrayList<Animal>> speciesAnimalsMap = new LinkedHashMap<>();
        for (Animal animal : nonOrphanedAnimals) {
            String species = animal.getSpecies();
            if (speciesAnimalsMap.containsKey(species)) {
                speciesAnimalsMap.get(species).add(animal);
            } else {
                ArrayList<Animal> speciesAnimals = new ArrayList<>();
                speciesAnimals.add(animal);
                speciesAnimalsMap.put(species, speciesAnimals);
            }
        }

        //Add feeding tasks based on available time in each hour after treatments
        //Adds them at the earliest possible time based of the animals active time and time available in the hour
        for (int hour = 0; hour < 24; hour++) {
            for (Map.Entry<String, ArrayList<Animal>> entry : speciesAnimalsMap.entrySet()) {
                String species = entry.getKey();
                ArrayList<Animal> speciesAnimals = entry.getValue();

                HashMap<Integer, ArrayList<Animal>> hourAnimalMap = new HashMap<>();
                for (Animal animal : speciesAnimals) {
                    List<Integer> possibleFeedingHours = animal.getMeal().getFeedingHours(animal.getActiveTime());
                    boolean hasNotBeenFed = !schedule.containsFeedingForAnimal(animal.getAnimalNickname());
                    if (hasNotBeenFed && possibleFeedingHours.contains(hour)) {

                        if (hourAnimalMap.containsKey(hour)) {
                            hourAnimalMap.get(hour).add(animal);
                        } else {
                            ArrayList<Animal> sameHourAnimals = new ArrayList<>();
                            sameHourAnimals.add(animal);
                            hourAnimalMap.put(hour, sameHourAnimals);
                        }
                    }
                }

                for (Map.Entry<Integer, ArrayList<Animal>> hourEntry : hourAnimalMap.entrySet()) {
                    int feedingHour = hourEntry.getKey();
                    ArrayList<Animal> sameHourAnimals = hourEntry.getValue();

                    int totalFeedingDuration = (sameHourAnimals.size()
                            * sameHourAnimals.get(0).getMeal().getDurationPerAnimal())
                            + sameHourAnimals.get(0).getMeal().getPrepTime();
                    int remainingTimeInHour = 60 - schedule.getTotalTaskDurationForHour(feedingHour);

                    if (totalFeedingDuration > 0 && remainingTimeInHour > 0) {
                        int prepTime = sameHourAnimals.get(0).getMeal().getPrepTime();
                        totalFeedingDuration += prepTime;
                        if (totalFeedingDuration <= remainingTimeInHour) {
                            StringBuilder animalNicknames = new StringBuilder();
                            for (Animal animal : sameHourAnimals) {
                                animalNicknames.append(animal.getAnimalNickname()).append(", ");
                            }
                            // Remove the trailing comma and space
                            animalNicknames.setLength(animalNicknames.length() - 2);

                            schedule.addTask(new ScheduledTask(feedingHour, "Feeding", TaskType.FEEDING, species,
                                    animalNicknames.toString(), sameHourAnimals.size(), totalFeedingDuration));
                            speciesAnimals.removeAll(sameHourAnimals);
                        } else {
                            // Find the maximum number of animals that can be fed within the remaining time
                            int maxAnimalsToFeed = remainingTimeInHour
                                    / sameHourAnimals.get(0).getMeal().getDurationPerAnimal();

                            if (maxAnimalsToFeed > 0) {
                                StringBuilder animalNicknames = new StringBuilder();
                                for (int i = 0; i < maxAnimalsToFeed; i++) {
                                    Animal animal = sameHourAnimals.get(i);
                                    animalNicknames.append(animal.getAnimalNickname()).append(", ");
                                    speciesAnimals.remove(animal);
                                }
                                // Remove the trailing comma and space
                                animalNicknames.setLength(animalNicknames.length() - 2);

                                int adjustedFeedingDuration = (maxAnimalsToFeed
                                        * sameHourAnimals.get(0).getMeal().getDurationPerAnimal())
                                        + sameHourAnimals.get(0).getMeal().getPrepTime(); // Correct calculation for
                                                                                          // adjustedFeedingDuration
                                schedule.addTask(new ScheduledTask(feedingHour, "Feeding", TaskType.FEEDING, species,
                                        animalNicknames.toString(), maxAnimalsToFeed, adjustedFeedingDuration));
                            }
                        }
                    }
                }
            }
        }
        //Schedule cage cleaning with the most available time in each hour
        HashMap<Integer, Integer> availableTimeMap = new HashMap<>();
        for (int hour = 0; hour < 24; hour++) {
            availableTimeMap.put(hour, 60);
        }
        for (ScheduledTask task : schedule.getScheduledTasks()) {
            int hour = task.getHour();
            int duration = task.getDuration();
            availableTimeMap.put(hour, availableTimeMap.get(hour) - duration);
        }
        for (Animal animal : nonOrphanedAnimals) {
            int cleaningDuration = animal.getCageCleaningDuration(animal.getSpecies());
            String animalNickname = animal.getAnimalNickname();

            while (cleaningDuration > 0) {
                int maxAvailableTime = 0;
                int targetHour = -1;
                for (Map.Entry<Integer, Integer> entry : availableTimeMap.entrySet()) {
                    int hour = entry.getKey();
                    int availableTime = entry.getValue();
                    if (availableTime > maxAvailableTime) {
                        maxAvailableTime = availableTime;
                        targetHour = hour;
                    }
                }

                if (targetHour == -1) {

                    break;
                }

                int cleaningToAdd = Math.min(cleaningDuration, maxAvailableTime);
                schedule.addTask(new ScheduledTask(targetHour, "Cage cleaning", TaskType.CLEANING, animalNickname,
                        cleaningToAdd));
                cleaningDuration -= cleaningToAdd;
                availableTimeMap.put(targetHour, availableTimeMap.get(targetHour) - cleaningToAdd);
            }
        }

        //Print any unscheduled feedings
        StringBuilder unscheduledFeedings = new StringBuilder();
        for (Map.Entry<String, ArrayList<Animal>> entry : speciesAnimalsMap.entrySet()) {
            String species = entry.getKey();
            ArrayList<Animal> speciesAnimals = entry.getValue();

            if (!speciesAnimals.isEmpty()) {
                unscheduledFeedings.append("Unscheduled feedings for ").append(species).append(":\n");
                for (Animal animal : speciesAnimals) {
                    unscheduledFeedings.append(" - ").append(animal.getAnimalNickname()).append("\n");
                }
            }
        }

        if (unscheduledFeedings.length() > 0) {
            System.out.println("\nUnscheduled feedings:");
            System.out.println(unscheduledFeedings.toString());
        } else {
            System.out.println("\nAll feedings have been scheduled.");
        }

        schedule.printSchedule();

        connectDB.close();
    }
    /**
     * Finds an animal object in a list of animals by its ID.
     * @param animals The list of animals to search in.
     * @param animalId The ID of the animal to search for.
     * @return The animal object if found, otherwise null.
     */
    private Animal findAnimalById(ArrayList<Animal> animals, Integer animalId) {
        for (Animal animal : animals) {
            if (animal.getAnimalID().equals(animalId)) {
                return animal;
            }
        }
        return null;
    }
    /**
     * Prompts the user to manually readjust a treatment's scheduling.
     * @param treatmentDescription The description of the treatment.
     * @param taskId The ID of the task.
     * @param initialHour The initial hour for the treatment.
     * @param schedule The schedule object to modify.
     * @param animalList The list of animals.
     * @param treatment The treatment object.
     * @param taskList The list of medical tasks.
     */
    private void promptManualReadjustment(String treatmentDescription, int taskId, int initialHour, Schedule schedule,
            ArrayList<Animal> animalList, Treatment treatment, List<MedicalTask> taskList) {
        JFrame manualAdjustmentFrame = new JFrame("Manual Readjustment");
        manualAdjustmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manualAdjustmentFrame.setSize(600, 150);
        manualAdjustmentFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Manual readjustment needed for treatment task: " + treatmentDescription);
        manualAdjustmentFrame.add(label);

        JButton backupVolunteerButton = new JButton("Use Backup Volunteer");
        JButton shiftTreatmentButton = new JButton("Shift Treatment");
        manualAdjustmentFrame.add(backupVolunteerButton);
        manualAdjustmentFrame.add(shiftTreatmentButton);

        backupVolunteerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fetch the associated animal
                Animal associatedAnimal = findAnimalById(animalList, treatment.getAnimalID());
                String animalNickname = associatedAnimal != null ? associatedAnimal.getAnimalNickname()
                        : "Unknown animal";

                // Add the task to the schedule with a note about using a backup volunteer
                ScheduledTask newTask = new ScheduledTask(initialHour, treatmentDescription + " [+ backup volunteer]",
                        TaskType.TREATMENT, animalNickname, 60 - schedule.getTotalTaskDurationForHour(initialHour));
                newTask.setBackupVolunteer(true);
                schedule.addTask(newTask);

                // Print the schedule with the newly added task
                schedule.printSchedule();

                manualAdjustmentFrame.dispose();
            }
        });

        shiftTreatmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame hourSelectionFrame = new JFrame("Shift Treatment");
                hourSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                hourSelectionFrame.setSize(600, 300);

                JPanel hourSelectionPanel = new JPanel();
                hourSelectionPanel.setLayout(new GridLayout(0, 3));

                JLabel titleLabel = new JLabel("Select an hour to place the task:", JLabel.CENTER);
                hourSelectionPanel.add(titleLabel);
                hourSelectionPanel.add(new JLabel(""));

                // Fetch the associated task to get its duration
                MedicalTask associatedTask = findTaskById(taskList, treatment.getTaskID());
                int taskDuration = associatedTask != null ? associatedTask.getDuration() : 0;
                for (int hour = 0; hour < 24; hour++) {
                    int availableTime = 60 - schedule.getTotalTaskDurationForHour(hour);
                    if (availableTime >= taskDuration) {
                        JButton hourButton = new JButton("Hour " + hour);
                        final int selectedHour = hour; // Create a new effectively final variable
                        hourButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Animal associatedAnimal = findAnimalById(animalList, treatment.getAnimalID());
                                String animalNickname = associatedAnimal != null ? associatedAnimal.getAnimalNickname()
                                        : "Unknown animal";
                                schedule.addTask(
                                        new ScheduledTask(selectedHour, treatmentDescription, TaskType.TREATMENT,
                                                animalNickname, taskDuration));
                                schedule.printSchedule();
                                hourSelectionFrame.dispose();
                            }
                        });
                        hourSelectionPanel.add(hourButton);
                    }
                }

                hourSelectionFrame.add(hourSelectionPanel);
                hourSelectionFrame.setVisible(true);
                manualAdjustmentFrame.dispose();
            }
        });

        manualAdjustmentFrame.setVisible(true);
    }
    /**
     * Finds the earliest available hour to schedule a treatment within a given window.
     * @param schedule The schedule object.
     * @param initialHour The initial hour to start searching from.
     * @param maxWindow The maximum window of hours to search.
     * @param taskDuration The duration of the task.
     * @return The first available hour if found, otherwise -1.
     */
    private int findAvailableHourForTreatment(Schedule schedule, int initialHour, int maxWindow, int taskDuration) {
        for (int window = 0; window < maxWindow; window++) {
            int targetHour = initialHour + window;
            int remainingTimeInHour = 60 - schedule.getTotalTaskDurationForHour(targetHour);

            if (taskDuration <= remainingTimeInHour) {
                return targetHour;
            }
        }
        return -1;
    }

    /**
     * Initializes the GUI, creates the frame and sets up the action listeners.
     */
    private void initialize() {
        frame = new JFrame("Database GUI");
        frame.setBounds(100, 100, 200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        connectDatabase = new ConnectDatabase();
        connectDatabase.createConnection();

        displayScheduleButton = new JButton("Display Schedule");
        displayScheduleButton.setBounds(20, 20, 150, 25);
        frame.getContentPane().add(displayScheduleButton);

        displayScheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Generate the schedule and display it in the text area.
                generateSchedule();

            }
        });

    }

    public GUI() {
        initialize();
    }
}
