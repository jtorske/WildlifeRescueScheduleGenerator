package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

        ArrayList<Animal> animalList = connectDB.selectAnimals();
        ArrayList<MedicalTask> taskList = connectDB.selectTasks();
        ArrayList<Treatment> treatmentList = connectDB.selectTreatments();

        Schedule schedule = new Schedule();

        // 5. Add all treatments to the schedule
        for (Treatment treatment : treatmentList) {
            int treatmentHour = treatment.getStartHour(); // Assume you have a method to get treatment hour
            MedicalTask associatedTask = findTaskById(taskList, treatment.getTaskID());
            String treatmentDescription = associatedTask != null ? associatedTask.getDescription()
                    : "Unknown treatment";
            schedule.addTask(new ScheduledTask(treatmentHour, treatmentDescription, TaskType.TREATMENT));
        }
        schedule.printSchedule();

        connectDB.close();

        // 2. Place one feeding time for each animal based on their sleep schedule
        Map<String, Integer> speciesFeedingTimes = new HashMap<>();
        for (Animal animal : animalList) {
            int feedingHour = animal.getMeal().getFeedingHour(animal.getActiveTime());
            speciesFeedingTimes.put(animal.getSpecies(), feedingHour);
        }

        // 3. Stack same species feeding times when viable
        for (Map.Entry<String, Integer> entry : speciesFeedingTimes.entrySet()) {
            String species = entry.getKey();
            int feedingHour = entry.getValue();
            List<Animal> sameSpeciesAnimals = new ArrayList<>();
            for (Animal animal : animalList) {
                if (animal.getSpecies().equals(species)) {
                    sameSpeciesAnimals.add(animal);
                }
            }
            if (sameSpeciesAnimals.size() > 1) {
                schedule.addTask(new ScheduledTask(feedingHour, "Feeding all " + species, TaskType.FEEDING,
                        sameSpeciesAnimals.size()));
            } else {
                for (Animal animal : sameSpeciesAnimals) {
                    schedule.addTask(new ScheduledTask(feedingHour, "Feeding " + animal.getAnimalNickname(),
                            TaskType.FEEDING, 0));
                }
            }
        }

        // 4. Schedule cage cleaning once a day
        int cleaningHour = 10; // Adjust the cleaning hour as needed
        schedule.addTask(new ScheduledTask(cleaningHour, "Cage cleaning", TaskType.CAGE_CLEANING));

        // Add your scheduling logic here, updating the schedule object with tasks
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
