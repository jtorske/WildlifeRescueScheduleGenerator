*WIP

Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
# Example Wildlife Rescue Schedule Builder
This project provides a program to generate a daily list of tasks for volunteers to complete in order to support the animals in residence at a wildlife rescue facility.

## Instructions/Installations
Download and install Java JDK 8 or higher.
Download the source code from the project repository.
Install any necessary dependencies.
Run the Main class to generate a daily list of tasks for the volunteers.
Input the date for which the schedule is to be generated.
The output will be displayed in the console. *WIP

## Classes
### Animal.java
This class represents an animal in the wildlife rescue facility. It has attributes such as animal ID, species, nickname, active time, orphan status, and feeding requirements. The class provides methods to get and set these attributes.

### ActiveTimes.java
This class is an enumeration that represents the active times of the animals. The possible values are NOCTURNAL, DIURNAL, and CREPUSCULAR.

### ConnectIDs.java
This class serves as a connector between the Animal class and the MedicalTask class, allowing connections in the Treatment class. It contains HashMaps that store the relationships between animal IDs, task IDs, and their respective objects.

### Cage.java
This class is responsible for representing a cage for a specific animal species in the wildlife rescue. It holds important information about the cage, such as the cage ID, the animal species residing in the cage, and the cleaning time required for the cage.

### HashMaps
This class contains HashMaps that store the relationships between animal IDs, task IDs, and their respective objects. It helps manage the connections between different classes in the project.

### Feeding.java
This class represents the feeding requirements for an animal. It has attributes such as prep time, duration per animal, number of animals, and max window. The class provides methods to get and set these attributes.

### InvalidScheduleException.java
This class is a custom exception that is thrown when a schedule cannot be built with the given time requirements.

### MedicalTask.java
This class represents a medical task to be performed on an animal. It has attributes such as task ID, description, duration, and max window. The class provides methods to get and set these attributes.

### Treatment.java
This class represents an individual treatment in the treatment table. It has attributes such as animal ID, task ID, and start hour. The class provides methods to get these attributes and links the IDs using the ConnectIDs class.

### GUI.java
This class creates the GUI that is going to be used to connect to the sql and create the schedule.
