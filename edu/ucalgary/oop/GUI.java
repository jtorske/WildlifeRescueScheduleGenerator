package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JButton animalsButton, tasksButton, treatmentsButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;
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

    private MedicalTask findTaskById(ArrayList<MedicalTask> tasks, Integer taskId) {
        for (MedicalTask task : tasks) {
            if (task.getTaskID().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

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

        // 1. Add all treatments to the schedule
        for (Treatment treatment : treatmentList) {
            int treatmentHour = treatment.getStartHour();
            MedicalTask associatedTask = findTaskById(taskList, treatment.getTaskID());
            String treatmentDescription = associatedTask != null ? associatedTask.getDescription()
                    : "Unknown treatment";
            Animal treatmentAnimal = findAnimalById(animalList, treatment.getAnimalID());
            String animalNickname = treatmentAnimal != null ? treatmentAnimal.getAnimalNickname() : "Unknown animal";
            schedule.addTask(new ScheduledTask(treatmentHour, treatmentDescription, TaskType.TREATMENT, treatmentHour,
                    animalNickname));
        }

        connectDB.close();

        // 2. Filter out orphaned animals
        ArrayList<Animal> nonOrphanedAnimals = new ArrayList<>();
        for (Animal animal : animalList) {
            if (!animal.getIsOrphaned()) {
                nonOrphanedAnimals.add(animal);
            }
        }

        // 3. Calculate feeding times and durations
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

        // 4. Add feeding tasks based on available time in each hour after treatments
        for (int hour = 0; hour < 24; hour++) {
            for (Map.Entry<String, ArrayList<Animal>> entry : speciesAnimalsMap.entrySet()) {
                String species = entry.getKey();
                ArrayList<Animal> speciesAnimals = entry.getValue();

                HashMap<Integer, ArrayList<Animal>> hourAnimalMap = new HashMap<>();
                for (Animal animal : speciesAnimals) {
                    List<Integer> possibleFeedingHours = animal.getMeal().getFeedingHours(animal.getActiveTime());
                    if (possibleFeedingHours.contains(hour)) {
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
                    int totalFeedingDuration = sameHourAnimals.size()
                            * sameHourAnimals.get(0).getMeal().getDurationPerAnimal();

                    int remainingTimeInHour = 60 - schedule.getTotalTaskDurationForHour(feedingHour);

                    if (totalFeedingDuration > 0 && remainingTimeInHour > 0) {
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

                                int adjustedFeedingDuration = maxAnimalsToFeed
                                        * sameHourAnimals.get(0).getMeal().getDurationPerAnimal();
                                schedule.addTask(new ScheduledTask(feedingHour, "Feeding", TaskType.FEEDING, species,
                                        animalNicknames.toString(), maxAnimalsToFeed, adjustedFeedingDuration));
                            }
                        }
                    }
                }
            }
        }
        // 5. Schedule cage cleaning once a day
        // ... (your existing code for cage cleaning)

        // Call printSchedule() after adding all tasks to the schedule

        // 6. Print unscheduled feedings
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

    private Animal findAnimalById(ArrayList<Animal> animals, Integer animalId) {
        for (Animal animal : animals) {
            if (animal.getAnimalID().equals(animalId)) {
                return animal;
            }
        }
        return null;
    }

    private void initialize() {
        frame = new JFrame("Database GUI");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        connectDatabase = new ConnectDatabase();
        connectDatabase.createConnection();

        animalsButton = new JButton("Show Animals");
        animalsButton.setBounds(20, 20, 150, 25);
        frame.getContentPane().add(animalsButton);

        tasksButton = new JButton("Show Tasks");
        tasksButton.setBounds(220, 20, 150, 25);
        frame.getContentPane().add(tasksButton);

        treatmentsButton = new JButton("Show Treatments");
        treatmentsButton.setBounds(420, 20, 150, 25);
        frame.getContentPane().add(treatmentsButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 60, 550, 280);
        frame.getContentPane().add(scrollPane);

        animalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Animal> animals = connectDatabase.selectAnimals();
                textArea.setText("Animals:\n");
                for (Animal animal : animals) {
                    textArea.append(animal.toString() + "\n");
                }
            }
        });

        tasksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<MedicalTask> tasks = connectDatabase.selectTasks();
                textArea.setText("Tasks:\n");
                for (MedicalTask task : tasks) {
                    textArea.append(task.toString() + "\n");
                }
            }
        });

        treatmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Treatment> treatments = connectDatabase.selectTreatments();
                textArea.setText("Treatments:\n");
                for (Treatment treatment : treatments) {
                    textArea.append(treatment.toString() + "\n");
                }
            }
        });
    }

    public GUI() {
        initialize();
        generateSchedule();
    }
}
