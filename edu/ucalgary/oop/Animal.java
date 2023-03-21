package edu.ucalgary.oop;

public class Animal{
    private final int ANIMALID;
    private final String ANIMALSPECIES;
    private boolean isOrphaned = false;
    private String activeTime;
    private String animalNickname;

    public Animal( int animalID, String animalNickname, String animalSpecies ){
        this.ANIMALID = animalID;
        this.ANIMALSPECIES = animalSpecies;
        this.animalNickname = animalNickname;
        // set is Orphaned based on animalNickname using regex
        //
        // set activeTime based on animalSpecies, for now assuming, uses enumerations.
        // If so, need to add enumeration in UML diagram.
        //
    }

    public String getActiveTime(){
        return this.activeTime;
    }

    public String getSpecies(){
        return this.ANIMALSPECIES;
    }

    public int getAnimalID(){
        return this.ANIMALID;
    }

    public String getAnimalNickname(){
        return this.animalNickname;
    }

    public boolean getIsOrphaned(){
        return this.isOrphaned;
    }
}