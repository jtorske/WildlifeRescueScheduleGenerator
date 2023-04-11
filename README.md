Group members are: Hiu Sum Yuen, Bruce Gillis, Jordan Torske, Elea Bahhadi
# Example Wildlife Rescue Schedule Generator
The Wildlife Rescue Schedule Generator is a program designed to automate the scheduling process for volunteers caring for injured and orphaned wild animals. The program reads from a database updated throughout the day to keep track of required medical tasks, and schedules tasks such as feeding, medical treatments, and cage cleaning for the animals in residence. The program optimizes efficiency by grouping similar tasks together and provides a list of tasks to complete within each hour of the day. If the available time is insufficient to complete all tasks, the program notifies the backup volunteer. The primary goal is to ensure the animals receive the necessary care while making the scheduling process more efficient.
## What to install
### Before running this program, make sure you have the following software installed on your system:
Java: This program requires Java 8 or later to be installed on your system. You can download the latest version of Java from the official Java website.

MySQL: This program requires a MySQL database server, MySQL Workbench, MySQL Shell and MySQL Connector/J driver to be installed on your system. You can download the latest version of these from the official MySQL website.

## How to use
To use this application, you would need to have a database set up with animal information, medical tasks, and treatments.

Once you have set up the database, you can run the application and click the "DisplaySchedule" button to generate a schedule. The schedule will be displayed in a text area.

If a treatment cannot be scheduled due to lack of available time, the application will prompt the user to manually readjust the scheduling by either using a backup volunteer or shifting the treatment to a different hour.

The application will also filter out orphaned animals and print any unscheduled feedings.
## Classes
### Animal
This class represents an Animal object with attributes like animal ID, species, orphan status, active time, nickname, and a Feeding object. The class provides a constructor, toString method, and getter methods for each attribute.
### ConnectDatabase
This class is responsible for connecting to a MySQL database, executing queries, and managing the results. It provides methods for connecting to the database, closing the connection, selecting animal, medical task, and treatment records, and inserting and deleting tasks. It also contains a method to get the results of the executed queries.
### Feeding
This class represents a Feeding object with attributes like preparation time, duration per animal, number of animals, and maximum feeding window. The class provides a constructor, setter methods for max window, and getter methods for each attribute. It also contains a method called getFeedingHours that returns a list of feeding hours based on the active time of an animal.
### Schedule
Thiss class represents a day's schedule of tasks for an animal clinic. It contains a list of ScheduledTask objects and provides methods to add tasks, calculate total task durations for specific hours, and check if the schedule contains a feeding task for a particular animal.
### ScheduleTask 
This class represents a task in the animal clinic's schedule. It contains details about the scheduled task, such as the hour, description, task type, and duration. In addition, it provides information about the animal(s) associated with the task, such as the species, nickname, and number of animals involved.
### Treatment
This class represents an individual treatment in the Treatments table. It contains the treatment ID, animal ID, task ID, and start hour. The class also relates the IDs together using the ConnectIDs class.
### Medical Task
This class represents a medical task to be performed in the animal clinic. This class contains the task ID, description, duration, and maximum time window for each task.
### GUI
This class implements User Interface as well as a sorting algorithm. It is used to construct the schedule after sorting treatments, animals and tasks are inputted through SQL. 
## Tests
### testAnimalConstructor 
Tests if the Animal constructor creates an Animal object correctly and initializes its properties.
### testCageCleaningTimeForCoyote 
Tests if the correct cage cleaning time is returned for a coyote.
### testCageCleaningTimeForPorcupine
Tests if the correct cage cleaning time is returned for a porcupine.
### testFeedingConstructor 
Tests if the Feeding constructor creates a Feeding object correctly and initializes its properties.
### testSetMaxWindow
Tests if the max window can be set correctly for a Feeding object.
### testMedicalTaskConstructor
Tests if the MedicalTask constructor creates a MedicalTask object correctly and initializes its properties.
### testTreatmentConstructor
Tests if the Treatment constructor creates a Treatment object correctly and initializes its properties.
### testScheduledTaskConstructor
Tests if the ScheduledTask constructor creates a ScheduledTask object correctly and initializes its properties.
### testScheduleAddTaskAndGetScheduledTasks
Tests if a task can be added to the schedule and the list of scheduled tasks can be retrieved.
### testScheduleTotalTaskDurationForHour
Tests if the total task duration for a specific hour is calculated correctly.
### testScheduleContainsFeedingForAnimal
Tests if the schedule correctly identifies if it contains a feeding task for a specific animal.
### testScheduleNumberOfFeedingsForSpeciesAndHour
Tests if the correct number of feedings for a species in a specific hour is returned.
### testMedicalTaskSetDescription
Tests if the description of a MedicalTask can be set correctly.
### testMedicalTaskSetDuration
Tests if the duration of a MedicalTask can be set correctly.
### testScheduledTaskBackupVolunteer
Tests if the backup volunteer flag can be set and retrieved correctly for a ScheduledTask.
### testScheduleAddMultipleTasksAndSortByHour
Tests if multiple tasks can be added to the schedule, and if they are sorted by hour correctly.
### testScheduleTotalTaskDurationForMultipleHours
Tests if the total task duration for multiple hours is calculated correctly.
### testScheduleNumberOfFeedingsForMultipleSpeciesAndHours
Tests if the correct number of feedings for multiple species and hours is returned.
### testAnimalSetIsOrphaned
Tests if the orphaned status of an animal can be set correctly.
### testAnimalActiveTimeForCoyote
Tests if the correct active time is returned for a coyote.
### testAnimalActiveTimeForPorcupine
Tests if the correct active time is returned for a porcupine.
### testAnimalActiveTimeForOrphanedAnimal
Tests if the correct active time is returned for an orphaned animal.
### testAnimalMealForCoyote
Tests if the correct meal information is returned for a coyote.
### testAnimalMealForPorcupine
Tests if the correct meal information is returned for a porcupine.
### testFeedingGetFeedingHoursNocturnal
Tests if the correct feeding hours are returned for a nocturnal animal.
### testFeedingGetFeedingHoursDiurnal
Tests if the correct feeding hours are returned for a diurnal animal.
### testFeedingGetFeedingHoursCrepuscular
Tests if the correct feeding hours are returned for a crepuscular animal.
### testAnimalConstructorInvalidAnimalID
Tests if an IllegalArgumentException is thrown when providing an invalid animal ID to the Animal constructor.
### testAnimalConstructorEmptyNickname
Tests if an IllegalArgumentException is thrown when providing an empty nickname to the Animal constructor.
### testAnimalConstructorEmptySpecies
Tests if an IllegalArgumentException is thrown when providing an empty species string to the Animal constructor.
### testFeedingConstructorInvalidDuration
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) duration to the Feeding constructor.
### testFeedingConstructorInvalidPrepTime
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) preparation time to the Feeding constructor.
### testMedicalTaskConstructorInvalidDuration 
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) duration to the MedicalTask constructor.
### testMedicalTaskConstructorInvalidMaxWindow
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) maximum window to the MedicalTask constructor.
### testTreatmentConstructorInvalidStartHour
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) start hour to the Treatment constructor.
### testScheduledTaskConstructorInvalidHour
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) hour to the ScheduledTask constructor.
### testScheduledTaskConstructorInvalidDuration
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) duration to the ScheduledTask constructor.
### testScheduledTaskConstructorInvalidAnimalCount
Tests if an IllegalArgumentException is thrown when providing an invalid (negative) animal count to the ScheduledTask constructor.
### testSelectAnimals
Tests if the correct list of animals is retrieved from the database. It checks the size of the returned list, ensuring it contains the expected number of animal objects. It also verifies that the nickname of a specific animal in the list is as expected.
### testSelectTasks
Tests if the correct list of medical tasks is retrieved from the database. It checks the size of the returned list, ensuring it contains the expected number of MedicalTask objects.
### testSelectTreatments
Tests if the correct list of treatments is retrieved from the database. It checks the size of the returned list, ensuring it contains the expected number of Treatment objects.