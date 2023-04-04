package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

/**
 * Class meant to access the database.
 * Note:    
 *                     (THIS IS IMPORTANT!!!)
 * Every time, to call a method, it is expected that you follow the 3 steps below:
 * 1.   Use createConnection() to connect and get the latest version of the database.
 * 2.   Use the desired method to make queries to the database.
 * 3.   Use the close() method to disconnect from the database.
 */
public class ConnectDatabase {
    
    final private String user = "oop";
    final private String  password = "password";
    private Connection dbConnect;
    private ResultSet results;


    public ConnectDatabase(){
        // default constructor.
    }

    /**
     * Creates a connection between the sql database and the program.
     */
    public void createConnection(){
        try{
            this.dbConnect = DriverManager.getConnection(
                "jdbc:mysql://localhost/EWR", 
                this.user, this.password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Closes the database.
     */
    public void close(){
        try{
            this.results.close();
            this.dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends query to database to get the ANIMALS table to make an ArrayList of the animals.
     * @return an ArrayList of Animal objects made based on the database.
     */
    public ArrayList<Animal> selectAnimals(){

        ArrayList<Animal> animalList = new ArrayList<>();

        try{
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while( results.next() ){
                Animal animal = new Animal( 
                    Integer.valueOf(this.results.getString("AnimalID")),
                    this.results.getString("AnimalNickname"),
                    this.results.getString("AnimalSpecies")
                 );
                animalList.add(animal);
            }
            myStmt.close();
        
        }catch(SQLException e){
            e.printStackTrace();
        }

        return animalList;

    }

    /**
     * Sends query to database to get the TASKS table to make an ArrayList of the MedicalTasks.
     * @return an ArrayList of MedicalTask objects made based on the database.
     */
    public ArrayList<MedicalTask> selectTasks(){

        ArrayList<MedicalTask> medicalTaskList = new ArrayList<>();

        try{
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM TASKS");

            while( results.next() ){
                MedicalTask task = new MedicalTask( 
                    Integer.valueOf( this.results.getString("TaskID") ),
                    this.results.getString("Description"), 
                    Integer.parseInt(this.results.getString("Duration")),
                    Integer.parseInt(this.results.getString("MaxWindow"))
                 );
                 medicalTaskList.add(task);
            }
            myStmt.close();

        }catch( SQLException e ){
            e.printStackTrace();
        }

        return medicalTaskList;
    }

    /**
     * Sends query to database to get the TREATMENTS table to make an ArrayList of the treatments.
     * @return an ArrayList of Treatment objects made based on the database.
     */
    public ArrayList<Treatment> selectTreatments(){
        ArrayList<Treatment> treatmentList = new ArrayList<>();

        try{
            Statement myStmt = this.dbConnect.createStatement();
            this.results = myStmt.executeQuery("SELECT * FROM TREATMENTS");

            while( results.next() ){
                Treatment treatment = new Treatment(
                    Integer.valueOf( this.results.getString("TreatmentID") ),
                    Integer.valueOf( this.results.getString("AnimalID") ),
                    Integer.valueOf( this.results.getString("TaskID") ),
                    Integer.parseInt(this.results.getString("StartHour"))
                );
                treatmentList.add(treatment);
            }
            myStmt.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return treatmentList;
    }

    /**
     * Missing:
     * insertTask(){}
     * deleteTask(){}
     */



}
