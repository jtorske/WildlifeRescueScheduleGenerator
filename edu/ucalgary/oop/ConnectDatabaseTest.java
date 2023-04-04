package edu.ucalgary.oop;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class ConnectDatabaseTest {
    private ConnectDatabase connectDB;

    @Before
    public void setUp(){
        connectDB = new ConnectDatabase();
        connectDB.createConnection();
    }

    @After
    public void tearDown(){
        connectDB.close();
    }

    @Test
    public void testSelectAnimals(){
        ArrayList<Animal> animalList = connectDB.selectAnimals();
        assertEquals(15, animalList.size());
    }

    /**
     * two more test files.
     */





}
