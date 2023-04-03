package edu.ucalgary.oop;

public class Main {
    public static void main(String[] args) {
        // Initialize Animal objects
        Animal animal1 = new Animal(1, "Spike", "coyote");
        Animal animal2 = new Animal(2, "Shadow", "fox");

        // Initialize MedicalTask objects
        MedicalTask task1 = new MedicalTask(1, "Rebandage head wound", 15, 3);
        MedicalTask task2 = new MedicalTask(2, "Administer antibiotics", 10, 2);

        // Link Animal objects and MedicalTask objects using ConnectIDs
        ConnectIDs.addNewAnimal(animal1);
        ConnectIDs.addNewAnimal(animal2);
        ConnectIDs.addNewTask(task1);
        ConnectIDs.addNewTask(task2);
        ConnectIDs.linkAnimal(animal1.getAnimalID(), animal1);
        ConnectIDs.linkAnimal(animal2.getAnimalID(), animal2);
        ConnectIDs.linkMedicalTask(task1.getTaskID(), task1);
        ConnectIDs.linkMedicalTask(task2.getTaskID(), task2);

        // Initialize Treatment objects
        Treatment treatment1 = new Treatment(animal1.getAnimalID(), task1.getTaskID(), 11);
        Treatment treatment2 = new Treatment(animal2.getAnimalID(), task2.getTaskID(), 11);

        // Add your input prompts, error handling, and user interaction logic here

        // Generate the schedule
        // You'll need to implement a Scheduler class that takes the information from the
        // Animal, MedicalTask, and Treatment classes to generate a schedule.
        // For now, you can just print out the treatments for each animal
        System.out.println("Schedule:");
        System.out.println("Animal: " + animal1.getAnimalNickname() + " - Task: " + task1.getDescription() + " - Start Hour: " + treatment1.getStartHour());
        System.out.println("Animal: " + animal2.getAnimalNickname() + " - Task: " + task2.getDescription() + " - Start Hour: " + treatment2.getStartHour());
    }
}