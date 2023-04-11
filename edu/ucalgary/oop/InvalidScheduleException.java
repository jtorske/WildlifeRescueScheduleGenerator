/**
 * Group 69 Final Project
 * Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
 * @version 1.0
 */
package edu.ucalgary.oop;

public class InvalidScheduleException extends Exception{
    public InvalidScheduleException(){
        System.out.println("A schedule cannot be built with the given time requirements.");
    }
}
