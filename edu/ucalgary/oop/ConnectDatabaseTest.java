package edu.ucalgary.oop;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;


public class ConnectDatabaseTest {
    private ConnectDatabase connectDB;
    private ArrayList<MedicalTask> medicalTaskList;

    @Before
    public void setUp(){
        connectDB = new ConnectDatabase();
        connectDB.createConnection();
    }

    @After
    public void tearDown(){
        connectDB.close();
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
