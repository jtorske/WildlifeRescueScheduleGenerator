package edu.ucalgary.oop;

public class IDNotExistException extends Exception {
    public IDNotExistException(Integer taskID){
        System.out.println( "The task with ID of " + 
        taskID + " does not exists and cannot be deleted." );
    }
}
