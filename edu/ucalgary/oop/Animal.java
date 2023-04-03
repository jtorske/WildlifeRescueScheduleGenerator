package edu.ucalgary.oop;
import java.util.regex.*;

public class Animal{
    private final Integer ANIMALID;
    private final String ANIMALSPECIES;
    private boolean isOrphaned = false;
    private ActiveTimes activeTime;
    private String animalNickname;
    private final Pattern patternIsOrphaned = Pattern.compile("^[a-zA-Z]+$");


    /**
     * Constructor of Animal, sets all initial field values for the animal.
     * @param animalID  An (Integer) ID of the animal.
     * @param animalNickname Name of the animal.
     * @param animalSpecies Species of the animal.
     * Note:
     * You can convert an int to an Integer by using the valueOf() method of the Integer class.
     */
    public Animal( Integer animalID, String animalNickname, String animalSpecies ){
        this.ANIMALID = animalID;
        this.ANIMALSPECIES = animalSpecies;
        this.animalNickname = animalNickname;
        
        //check the pattern of the animal's name, if there are multiple names, set isOphaned to true.
        Matcher matcher = this.patternIsOrphaned.matcher(this.animalNickname);
        if (!matcher.find()){
            setIsOrphaned(true);
        }

        //  do specific things based on the animal's species.
        switch(this.ANIMALSPECIES){
            case "coyote":
                setActiveTime(ActiveTimes.CREPUSCULAR);
                break;
            case "porcupine":
                setActiveTime(ActiveTimes.CREPUSCULAR);
                break;
            case "fox":
                setActiveTime(ActiveTimes.NOCTURNAL);
                break;
            case "raccoon":
                setActiveTime(ActiveTimes.NOCTURNAL);
                break;
            case "beaver":
                setActiveTime(ActiveTimes.DIURNAL);
                break;
        }
    }

    /**
     * Sets whether or not the animal is orphaned.
     * @param bool true if animal should be orphaned, false if animal should not be orphaned.
     */
    public void setIsOrphaned(boolean bool){
        this.isOrphaned = bool;
    }

    /**
     * Sets activeTime of animal to NOCTURNAL, DIURNAL or CREPUSCULAR based on the argument.
     * @param activeTime NOCTURNAL, DIURNAL or CREPUSCULAR.
     */
    public void setActiveTime(ActiveTimes activeTime){
        this.activeTime = activeTime;
    }

    /**
     * Gets the activeTime of the animal.
     * @return the activeTime of the animal.
     */
    public ActiveTimes getActiveTime(){
        return this.activeTime;
    }

    /**
     * Gets the species of the animal.
     * @return the species of the animal.
     */
    public String getSpecies(){
        return this.ANIMALSPECIES;
    }

    /**
     * Gets the ID of the animal.
     * @return the ID of the animal.
     */
    public Integer getAnimalID(){
        return this.ANIMALID;
    }

    /**
     * Gets the nickname of the animal.
     * @return the nickname of the animal.
     */
    public String getAnimalNickname(){
        return this.animalNickname;
    }

    /**
     * Gets the orphan status of the animal.
     * @return the orphan status of the animal.
     */
    public boolean getIsOrphaned(){
        return this.isOrphaned;
    }
}