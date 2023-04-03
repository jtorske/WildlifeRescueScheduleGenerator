package edu.ucalgary.oop;

public class InvalidScheduleException extends Exception{
    public InvalidScheduleException(){
        System.out.println("A schedule cannot be built with the given time requirements.");
    }
}
