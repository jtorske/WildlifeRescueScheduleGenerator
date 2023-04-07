package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

/**
 * Class meant to access the database.
 * Note:
 * (THIS IS IMPORTANT!!!)
 * Follow the steps below to access the database:
 * 1. Use createConnection() to connect and get the latest version of the
 * database.
 * 2. Use the desired methods to make queries to the database. `results`
 * implicitly
 * closes everytime a new results is open through a different method.
 * 3. Use the close() method to disconnect from the database.
 */
public class ConnectDatabase {

    final private String USER = "oop";
    final private String PASSWORD = "password";
    private Connection dbConnect;
    private ResultSet results;

    public ConnectDatabase() {
        // default constructor.
    }

    /**
     * Creates a connection between the sql database and the program.
     */
    public void createConnection() {
        try {
            this.dbConnect = DriverManager.getConnection(
                    "jdbc:mysql://localhost/EWR",
                    this.USER, this.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database.
     */
    public void close() {
        try {
            this.results.close();
            this.dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends query to database to get the ANIMALS table to make an ArrayList of the
     * animals.
     * 
     * @return an ArrayList of Animal objects made based on the database.
     */
    public ArrayList<Animal> selectAnimals() {

        ArrayList<Animal> animalList = new ArrayList<>();

        try {
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while (results.next()) {
                Animal animal = new Animal(
                        Integer.valueOf(this.results.getString("AnimalID")),
                        this.results.getString("AnimalNickname"),
                        this.results.getString("AnimalSpecies"));
                animalList.add(animal);
            }
            myStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animalList;

    }

    /**
     * Sends query to database to get the TASKS table to make an ArrayList of the
     * MedicalTasks.
     * 
     * @return an ArrayList of MedicalTask objects made based on the database.
     */
    public ArrayList<MedicalTask> selectTasks() {

        ArrayList<MedicalTask> medicalTaskList = new ArrayList<>();

        try {
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM TASKS");

            while (results.next()) {
                MedicalTask task = new MedicalTask(
                        Integer.valueOf(this.results.getString("TaskID")),
                        this.results.getString("Description"),
                        Integer.parseInt(this.results.getString("Duration")),
                        Integer.parseInt(this.results.getString("MaxWindow")));
                medicalTaskList.add(task);
            }
            myStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalTaskList;
    }

    /**
     * Sends query to database to get the TREATMENTS table to make an ArrayList of
     * the treatments.
     * 
     * @return an ArrayList of Treatment objects made based on the database.
     */
    public ArrayList<Treatment> selectTreatments() {
        ArrayList<Treatment> treatmentList = new ArrayList<>();

        try {
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM TREATMENTS");

            while (results.next()) {
                Treatment treatment = new Treatment(
                        Integer.valueOf(this.results.getString("TreatmentID")),
                        Integer.valueOf(this.results.getString("AnimalID")),
                        Integer.valueOf(this.results.getString("TaskID")),
                        Integer.parseInt(this.results.getString("StartHour")));
                treatmentList.add(treatment);
            }
            myStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentList;
    }

    /**
     * I don't believe we should be using this in this program.
     * It is a perminent alteration.
     * Inserts a task into the database.
     * 
     * @param taskID      the Id of the task you are creating,
     *                    PLEASE DO NOT CLASh WITH AN EXISTING ID.
     * @param description the description of the task.
     * @param duration    the duration of the task.
     * @param maxWindow   the window of time that the task has to be done in, in
     *                    hours.
     *                    note:
     *                    just remove, `int rowCount =` and
     *                    `System.out.println("Rows affected: " + rowCount);`
     *                    if you do not want the println.
     *                    You should be left with only `myStmt.executeUpdate();` if
     *                    you do so.
     */
    public void insertNewTask(Integer taskID, String description, int duration,
            int maxWindow, ArrayList<MedicalTask> list) throws IDExistException {
        boolean isExist = false;
        for (MedicalTask obj : list) {
            if (obj.getTaskID() == taskID) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            try {
                String query = "INSERT INTO TASKS (TaskID, Description, Duration, MaxWindow) VALUES (?,?,?,?)";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);

                myStmt.setString(1, taskID.toString());
                myStmt.setString(2, description);
                myStmt.setString(3, Integer.toString(duration));
                myStmt.setString(4, Integer.toString(maxWindow));

                int rowCount = myStmt.executeUpdate();
                System.out.println("Rows affected: " + rowCount);

                myStmt.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new IDExistException(taskID);
        }
    }

    /**
     * I don't believe we should be using this in this program.
     * It is a perminent alteration.
     * Deletes a task from the database.
     * 
     * @param taskID the Id of the task you are deleting.
     *               note:
     *               just remove, `int rowCount =` and `System.out.println("Rows
     *               affected: " + rowCount);`
     *               if you do not want the println.
     *               You should be left with only `myStmt.executeUpdate();` if you
     *               do so.
     */
    public void deleteTask(Integer taskID, ArrayList<MedicalTask> list) throws IDNotExistException {
        boolean isExist = false;
        for (MedicalTask obj : list) {
            if (obj.getTaskID() == taskID) {
                isExist = true;
                break;
            }
        }
        if (isExist) {
            try {
                String query = "DELETE FROM TASKS WHERE TaskID = ?";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);

                myStmt.setString(1, taskID.toString());

                int rowCount = myStmt.executeUpdate();
                System.out.println("Rows affected: " + rowCount);

                myStmt.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new IDNotExistException(taskID);
        }
    }

    public ResultSet getResults() {
        return this.results;
    }
}
