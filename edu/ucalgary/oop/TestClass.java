package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;

public class TestClass {
    private Animal animal1 = new Animal(1, "Fido", "dog");
    private Animal animal2 = new Animal(2, "Joe, Bambi", "dog");
    private Animal animal3 = new Animal(3, "ALEX", "porcupine");
    private Animal animal4 = new Animal(4, "ALEX", "coyote");

    private MedicalTask medicalTask1;
    private Treatment treatment1;
    private ScheduledTask scheduledTask1;
    private Schedule schedule1;
    private MedicalTask medicalTask2;
    private Treatment treatment2;
    private ScheduledTask scheduledTask2;
    private ScheduledTask scheduledTask3;

    private ConnectDatabase connectDB;
    private ArrayList<MedicalTask> medicalTaskList;

    @Before
    public void setUp() {
        medicalTask1 = new MedicalTask(1, "X-Ray", 30, 60);
        treatment1 = new Treatment(1, 1, 1, 10);
        scheduledTask1 = new ScheduledTask(10, "Feeding", TaskType.FEEDING, "dog", "Fido", 1, 20);
        schedule1 = new Schedule();

        medicalTask2 = new MedicalTask(2, "Ultrasound", 45, 90);
        treatment2 = new Treatment(2, 2, 2, 14);
        scheduledTask2 = new ScheduledTask(12, "Medical Checkup", TaskType.TREATMENT, "dog", "Joe, Bambi", 2, 40);
        scheduledTask3 = new ScheduledTask(15, "Cage Cleaning", TaskType.CLEANING, "coyote", "ALEX", 1, 30);

        connectDB = new ConnectDatabase();
        connectDB.createConnection();
    }

    @After
    public void tearDown(){
        connectDB.close();
    }

    @Test
    public void testAnimalConstructor() {
        assertEquals(1, animal1.getAnimalID().intValue());
        assertEquals("Fido", animal1.getAnimalNickname());
        assertEquals("dog", animal1.getSpecies());
        assertFalse(animal1.getIsOrphaned());
    }

    @Test
    public void testCageCleaningTimeForCoyote() {
        assertEquals(5, animal4.getCageCleaningDuration(animal4.getSpecies()));
    }

    @Test
    public void testCageCleaningTimeForPorcupine() {
        assertEquals(10, animal3.getCageCleaningDuration(animal3.getSpecies()));
    }

    @Test
    public void testFeedingConstructor() {
        Feeding feeding = new Feeding(5, 10);
        assertEquals(10, feeding.getPrepTime());
        assertEquals(5, feeding.getDurationPerAnimal());
        assertEquals(0, feeding.getNumberOfAnimals());
        assertEquals(3, feeding.getMaxWindow());
    }

    @Test
    public void testSetMaxWindow() {
        Feeding feeding = new Feeding(5, 10);
        feeding.setMaxWindow(4);
        assertEquals(4, feeding.getMaxWindow());
    }

    @Test
    public void testMedicalTaskConstructor() {
        assertEquals(1, medicalTask1.getTaskID().intValue());
        assertEquals("X-Ray", medicalTask1.getDescription());
        assertEquals(30, medicalTask1.getDuration());
        assertEquals(60, medicalTask1.getMaxWindow());
    }

    @Test
    public void testTreatmentConstructor() {
        assertEquals(1, treatment1.getTreatmentID().intValue());
        assertEquals(1, treatment1.getAnimalID().intValue());
        assertEquals(1, treatment1.getTaskID().intValue());
        assertEquals(10, treatment1.getStartHour());
    }

    @Test
    public void testScheduledTaskConstructor() {
        assertEquals(10, scheduledTask1.getHour());
        assertEquals("Feeding", scheduledTask1.getDescription());
        assertEquals(TaskType.FEEDING, scheduledTask1.getTaskType());
        assertEquals("dog", scheduledTask1.getSpecies());
        assertEquals("Fido", scheduledTask1.getAnimalNickname());
        assertEquals(1, scheduledTask1.getAnimalCount());
        assertEquals(20, scheduledTask1.getDuration());
    }

    @Test
    public void testScheduleAddTaskAndGetScheduledTasks() {
        schedule1.addTask(scheduledTask1);
        assertEquals(1, schedule1.getScheduledTasks().size());
        assertEquals(scheduledTask1, schedule1.getScheduledTasks().get(0));
    }

    @Test
    public void testScheduleTotalTaskDurationForHour() {
        schedule1.addTask(scheduledTask1);
        assertEquals(20, schedule1.getTotalTaskDurationForHour(10));
    }

    @Test
    public void testScheduleContainsFeedingForAnimal() {
        schedule1.addTask(scheduledTask1);
        assertTrue(schedule1.containsFeedingForAnimal("Fido"));
        assertFalse(schedule1.containsFeedingForAnimal("Bambi"));
    }

    @Test
    public void testScheduleNumberOfFeedingsForSpeciesAndHour() {
        schedule1.addTask(scheduledTask1);
        assertEquals(1, schedule1.getNumberOfFeedingsForSpeciesAndHour("dog", 10));
    }

    @Test
    public void testMedicalTaskSetDescription() {
        medicalTask1.setDescription("MRI");
        assertEquals("MRI", medicalTask1.getDescription());
    }

    @Test
    public void testMedicalTaskSetDuration() {
        medicalTask1.setDuration(45);
        assertEquals(45, medicalTask1.getDuration());
    }

    @Test
    public void testScheduledTaskBackupVolunteer() {
        assertFalse(scheduledTask1.isBackupVolunteer());
        scheduledTask1.setBackupVolunteer(true);
        assertTrue(scheduledTask1.isBackupVolunteer());
    }

    @Test
    public void testScheduleAddMultipleTasksAndSortByHour() {
        schedule1.addTask(scheduledTask1);
        schedule1.addTask(scheduledTask2);
        schedule1.addTask(scheduledTask3);

        assertEquals(3, schedule1.getScheduledTasks().size());
        assertEquals(scheduledTask1, schedule1.getScheduledTasks().get(0));
        assertEquals(scheduledTask2, schedule1.getScheduledTasks().get(1));
        assertEquals(scheduledTask3, schedule1.getScheduledTasks().get(2));
    }

    @Test
    public void testScheduleTotalTaskDurationForMultipleHours() {
        schedule1.addTask(scheduledTask1);
        schedule1.addTask(scheduledTask2);
        schedule1.addTask(scheduledTask3);

        assertEquals(20, schedule1.getTotalTaskDurationForHour(10));
        assertEquals(40, schedule1.getTotalTaskDurationForHour(12));
        assertEquals(30, schedule1.getTotalTaskDurationForHour(15));
    }

    @Test
    public void testScheduleNumberOfFeedingsForMultipleSpeciesAndHours() {
        schedule1.addTask(scheduledTask1);
        schedule1.addTask(scheduledTask2);

        assertEquals(1, schedule1.getNumberOfFeedingsForSpeciesAndHour("dog", 10));
        assertEquals(0, schedule1.getNumberOfFeedingsForSpeciesAndHour("dog", 11));

    }

    @Test
    public void testAnimalSetIsOrphaned() {
        assertFalse(animal1.getIsOrphaned());
        animal1.setIsOrphaned(true);
        assertTrue(animal1.getIsOrphaned());
    }

    @Test
    public void testAnimalActiveTimeForCoyote() {
        assertEquals("CREPUSCULAR", animal4.getActiveTime());
    }

    @Test
    public void testAnimalActiveTimeForPorcupine() {
        assertEquals("CREPUSCULAR", animal3.getActiveTime());
    }

    @Test
    public void testAnimalActiveTimeForOrphanedAnimal() {
        assertEquals("NOCTURNAL", animal2.getActiveTime());
    }

    @Test
    public void testAnimalMealForCoyote() {
        assertEquals(5, animal4.getMeal().getDurationPerAnimal());
        assertEquals(10, animal4.getMeal().getPrepTime());
    }

    @Test
    public void testAnimalMealForPorcupine() {
        assertEquals(5, animal3.getMeal().getDurationPerAnimal());
        assertEquals(0, animal3.getMeal().getPrepTime());
    }

    @Test
    public void testFeedingGetFeedingHoursNocturnal() {
        Feeding feeding = new Feeding(5, 10);
        List<Integer> expectedHours = Arrays.asList(20, 21, 22);
        assertEquals(expectedHours, feeding.getFeedingHours("NOCTURNAL"));
    }

    @Test
    public void testFeedingGetFeedingHoursDiurnal() {
        Feeding feeding = new Feeding(5, 10);
        List<Integer> expectedHours = Arrays.asList(9, 10, 11);
        assertEquals(expectedHours, feeding.getFeedingHours("DIURNAL"));
    }

    @Test
    public void testFeedingGetFeedingHoursCrepuscular() {
        Feeding feeding = new Feeding(5, 10);
        List<Integer> expectedHours = Arrays.asList(18, 19, 20);
        assertEquals(expectedHours, feeding.getFeedingHours("CREPUSCULAR"));
    }

    @Test
    public void testFeedingConstructorInvalidDuration() {
        assertThrows(IllegalArgumentException.class, () -> new Feeding(-5, 10));
    }

    @Test
    public void testFeedingConstructorInvalidPrepTime() {
        assertThrows(IllegalArgumentException.class, () -> new Feeding(5, -10));
    }

    @Test
    public void testMedicalTaskConstructorInvalidDuration() {
        assertThrows(IllegalArgumentException.class, () -> new MedicalTask(1, "X-Ray", -30, 60));
    }

    @Test
    public void testMedicalTaskConstructorInvalidMaxWindow() {
        assertThrows(IllegalArgumentException.class, () -> new MedicalTask(1, "X-Ray", 30, -60));
    }

    @Test
    public void testTreatmentConstructorInvalidStartHour() {
        assertThrows(IllegalArgumentException.class, () -> new Treatment(1, 1, 1, -10));
    }

    @Test
    public void testScheduledTaskConstructorInvalidHour() {
        assertThrows(IllegalArgumentException.class,
                () -> new ScheduledTask(-10, "Feeding", TaskType.FEEDING, "dog", "Fido", 1, 20));
    }

    @Test
    public void testScheduledTaskConstructorInvalidDuration() {
        assertThrows(IllegalArgumentException.class,
                () -> new ScheduledTask(10, "Feeding", TaskType.FEEDING, "dog", "Fido", 1, -20));
    }

    @Test
    public void testScheduledTaskConstructorInvalidAnimalCount() {
        assertThrows(IllegalArgumentException.class,
                () -> new ScheduledTask(10, "Feeding", TaskType.FEEDING, "dog", "Fido", -1, 20));
    }

    
    @Test
    public void testSelectAnimals(){
        ArrayList<Animal> animalList = connectDB.selectAnimals();
        System.out.println(animalList.get(7).getAnimalNickname());
        assertEquals(15, animalList.size());
    }

    @Test
    public void testSelectTasks() {
        this.medicalTaskList = connectDB.selectTasks();
        assertEquals(10, medicalTaskList.size());
    }


    // @Test
    // public void testInsertNewTask() throws IDExistException{
    //     try {
    //         Thread.sleep(100); // delay for 0.1 seconds
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    //     Integer testInsertID = 11;
    //     connectDB.insertNewTask(testInsertID, "testing insert method", 10, 1, this.medicalTaskList);
    //     try{
    //         connectDB.getResults().close();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     System.out.println("Please now remove the task with id of: " + testInsertID);
    //     assertEquals(11, connectDB.selectTasks().size());
    // }

    // @Test
    // public void testDeleteTask() throws IDNotExistException{
    //     try {
    //         Thread.sleep(500); // delay for 0.5 seconds
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    //     Integer testDeleteID = 11;
    //     connectDB.deleteTask(testDeleteID, this.medicalTaskList);
    //     try{
    //         connectDB.getResults().close();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     System.out.println("The tasks with ID of " + testDeleteID + " has been deleted.");
    //     assertEquals(10, connectDB.selectTasks().size());
    // }




    @Test
    public void testSelectTreatments(){
        ArrayList<Treatment> TreatmentList = connectDB.selectTreatments();
        assertEquals(30, TreatmentList.size());
    }
}
