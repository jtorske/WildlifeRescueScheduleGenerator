package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

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

    public void close(){
        try{
            this.results.close();
            this.dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Missing:
     * insertTask(){}
     * deleteTask(){}
     * selectTasks(){}
     * selectTreatments(){}
     */



}
