package edu.ucalgary.oop;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class does not intend to take in object instances, instead it serves to connect
 * the Animal class and the MedicalTask class. Allowing for connections in the Treatment class.
 * HashMaps are basically dictionaries in python. Using a key to get a value responding to the key.
 */
public class ConnectIDs {
    
    public static HashMap <Integer, MedicalTask> medicalTaskMap = new HashMap<>();
    public static HashMap <Integer, Animal> animalMap = new HashMap<>();
    public static HashMap <Integer, ArrayList<Integer>> idMap = new HashMap<>();
    
    public static ArrayList <Animal> animals = new ArrayList<>();
    public static ArrayList <MedicalTask> medicalTasks = new ArrayList<>();
    


    /**
     * Called in `main (alter later on)` after making each MedicalTask object.
     * Adds a MedicalTask object to the medicalTasks ArrayList.
     * @param task a MedicalTasks object.
     */
    public static void addNewTask( MedicalTask task ){
        medicalTasks.add(task);
    }


    /**
     * Called in `main (alter later on)` after making each Animal object.
     * Adds an Animal object to the animals ArrayList.
     * @param animal an Animal object.
     */
    public static void addNewAnimal( Animal animal ){
        animals.add(animal);
    }

    /**
     * Called in `main (alter later on)` after making each MedicalTask object.
     * Add an instance to the medicalTaskMap hashmap that connects 
     * `taskID` (key) to a MedicalTask `task` (value) object.
     * @param taskID (key in medicalTaskMap) ID of MedicalTask object.
     * @param task (value of medicalTaskMap) MedicalTask object.
     */
    public static void linkMedicalTask(Integer taskID, MedicalTask task){
        medicalTaskMap.put(taskID, task);
    }

    /**
     * Called in `main (alter later on)` after making each Animal object.
     * Add an instance to the animalMap hashmap that connects 
     * `animalID` (key) to an `animal` (value) object.
     * @param animalID (key in animalMap) ID of animal.
     * @param animal (value of animalMap) Animal object.
     */
    public static void linkAnimal(Integer animalID, Animal animal){
        animalMap.put(animalID, animal);
    }


    /**
     * Called in Treatment.java constructor.
     * Add an instance to the idMap hashmap that connects `animalID` (key) to `an Array of taskIDs` (value).
     * If animalID already exists in idMap, adds taskID to the array of taskIDs.
     * Else, makes a new Array of task IDs that responds to the animalID.
     * @param animalID (key in idMap) ID of animal.
     * @param taskID (value in idMap) ID of medical task.
     */
    public static void linkIDs(Integer animalID, Integer taskID){
        if (idMap.containsKey(animalID)){
            idMap.get(animalID).add(taskID);
        }else{
            ArrayList<Integer> taskIDList = new ArrayList<>();
            taskIDList.add(taskID);
            idMap.put(animalID, taskIDList);
        }
    }
}
