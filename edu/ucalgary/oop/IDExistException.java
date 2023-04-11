/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 * @version 1.0
 */
package edu.ucalgary.oop;

public class IDExistException extends Exception{
    public IDExistException ( Integer taskID ){
        System.out.println( "The task with ID of " + 
        taskID + " already exists and should not be repeated." );
    }

}
