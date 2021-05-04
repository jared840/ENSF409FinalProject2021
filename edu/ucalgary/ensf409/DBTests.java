package edu.ucalgary.ensf409;
/**  
* 
* @author         Jared Lundy
* @author         Jordan Lundy
* @author         Chace Nielson
* @author         Tony Vo
* @version        1.0
* @since          1.0
*/

/*
This test file tests our program's database connectivity and associated methods: 
    - readDatvabase counter method
    - readDatabase close() method
    - readDatabase connection method
    - readDatabase deleteSoldItems method

*/

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;
import java.io.File;

import jdk.jfr.Timestamp;

//NOTE: THIS TEST MUST BE RAN WITH JUNIT-4.13.2.jar, HAMCREST-CORE-1.3.JAR AND MYSQL-CONNECTOR-JAVA-8.0.23.JAR CONNECTOR IN THE CLASSPATH!!!!!!!!!!!!!
//Note: added 2 private bools and 2 getters to readDatabase.java, and an additional close method

public class DBTests {
    public DBTests(){

    }

    /**
     * Database connection test that creates a readDatabase object
     * Via the default readDatabase constructor, the createConnection() method is called to connect to database
     * Calls the object's getDBConnected method which returns a boolean indicating whether connection succeeded
     * Asserts that this boolean is true, indicating that readDatabase successfully connects to the database!
     */
    @Test 
    
    public void dbConnectTest() {
        System.out.println();
        System.out.println();
        System.out.println("***************PLEASE ENTER VALID ITEM, TYPE AND QUANTITY TO TEST DATABASE CONNECTIVITY**************");
        System.out.println();
        
        //create readDatabase object which also calls method createConnection() in its ctor
            ReadDatabase testConnection = new ReadDatabase();
            //check that readDatabase's private DBConnected boolean was changed to true, indicating successful db connection
            boolean connectBool = testConnection.getDBConnected();
            testConnection.close();     //closing resources; not tested here though
            //assert that this connection boolean is true
            assertTrue("ReadDatabase's createConnection method did not successfully connect to mySQL database: ", connectBool);
    
    }

     /**
     * Close test that tests that ReadDatabase's close method reliably closes all SQL resources without error
     * Test creates a readDatabase object, then calls close(), and then retrieves the private boolean getDBClosed to determine if successful
     * Case: close method successful and boolean DBClosed is retrieved as true
     */
    @Test 
   
    public void dbCloseTest() {
        System.out.println();
        System.out.println();
        System.out.println("****************PLEASE ENTER VALID ITEM, TYPE AND QUANTITY TO TEST DATABASE RESOURCE CLOSING***************");
        System.out.println();
        //create readDatabase object
        ReadDatabase testClose = new ReadDatabase();
        //call close method
        testClose.close();
        //boolean from getDBClosed will be true if proper closing occurs, false if failed to close a resource
        boolean closedBool = testClose.getDBClosed();
        
        //ensure that the boolean is true and thus the close method closed all SQL resources
        assertTrue("ReadDatabase's close method did not successfully close all resources: ", closedBool);
    }

      /**
    Case: Failed close test - expect NullPointerException
    This test creates a readDatabase with the (String, String, int) constructor, which creates a connection but does not instantiate a resultSet
    As a result, the close() method, which closes ALL resources (including resultSet), will fail and should return a nullPointer
    */
    @Test 
  
    public void closeExceptionTest() {
        try{
        ReadDatabase a = new ReadDatabase("chair", "mesh", 2);
        a.close();
        fail("Failed to throw expected NullPointerException when close method attempts to close private resultSet results when null");
        }catch(Exception e) {
            //Passes test if it reaches this block via catching the exception when trying to close null result ( this ctor doesn't initialize resultSet result or createConnection())
        }


    }

     /**
     * Case: readDatabase successfully sets its private members
     * Test for readDatabase's constructor(String, String, int) and its ability to set its private members accordingly 
     * Creates a readDatabase object with any data (here: lamp, study, 2), and then asserts that the getters for item, type and category match  
     */
    @Test 
   
    public void readDatabaseTest() {
        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };
        ReadDatabase testDB = new ReadDatabase("lamp", "study", 2);

        //ensure that testDB private variables for item, type and quantity were set correctly by constructor
        assertTrue(testDB.getCategory().equals("lamp"));
        assertTrue(testDB.getType().equals("study"));
        assertTrue(testDB.getNumber()==2);

    }

   /* @Test 
    public void readDatabaseTest2() {
        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };
        ReadDatabase testDB = new ReadDatabase();

        boolean valid = false;
        if(testDB.getCategory())
    }*/

    /**
 * Case: ReadDatabase successfully counts matches in a dataBase
 * This test creates a readDatabase object with filing, medium and 2, connects to the mySql database, and then selects all from filing (can be changed)
 * Then, counts how many filings match the type in the database.
 * Next, asserts that this local counter integer is equal to the readDatabase's counter method, which also counts matches
 * Should be true and therefore successful
 */
@Test                                       

public void counterTest() {
    int counter = 0;
    try{
        ReadDatabase a = new ReadDatabase("filing", "medium", 2);
        a.createConnection();
        Connection testConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "scm", "ensf409");
        Statement myStatement = testConnect.createStatement();

        ResultSet results = myStatement.executeQuery("SELECT * FROM filing");

        while(results.next()) {
            String typeTable = results.getString("Type");

            if(typeTable.equalsIgnoreCase(a.getType())){
                counter++;
            }
        }
       // a.close();
       //a.closeConnect();
        testConnect.close();
        results.close();

        assertEquals("ReadDatabase's counter method failed to count the proper amount of specified items in category: ", counter, a.counter());
        a.closeConnect();
    } catch(SQLException e) {
        fail("Failure in connecting to database and executing statements!");
        e.printStackTrace();
    }

}
   
   //deleteSoldItem test - add unique ID then remove it as if it were sold!

     /**
    * Case: Succesful removal of unique ID from database
    Test first inserts a unique ID (X1234 - will not be in the database) into the desk table
    Then, the test calls readDatabase's deleteSoldItems method with the unique ID added as an argument to remove
    Then, test goes into the database and searches for this unique ID - if found it fails, if exception thrown it fails
    Expected to succeed in removing unique ID
    */
   @Test 
  
   public void deleteSoldItemsTest() {
       int counter = 0;
       try{
       ReadDatabase test = new ReadDatabase("desk", "standing", 1);
       test.createConnection();
       //INSERTING UNIQUE ID TO DATABASE TO DELETE
       Connection testConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "scm", "ensf409");
        String query = "INSERT INTO desk (ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement myPrSt = testConnect.prepareStatement(query);

        myPrSt.setString(1, "X1234");
        myPrSt.setString(2, "Standing");
        myPrSt.setString(3, "Y");
        myPrSt.setString(4, "N");
        myPrSt.setString(5, "Y");
        myPrSt.setString(6, "76");
        myPrSt.setString(7, "005");

        int rowCount = myPrSt.executeUpdate();
        myPrSt.close();

        String [] toSell = {"X1234"};

        test.deleteSoldItems(toSell);   //sell and delete that unique item

        Statement search = testConnect.createStatement();
        ResultSet results = search.executeQuery("SELECT * FROM desk");

        while(results.next()) {
            String str = results.getString("ID");
            if(str.equals("X1234")){
                fail("deleteSoldItems method Failed to remove uniquely added item from database");
            }
        }
        testConnect.close();
        myPrSt.close();
        search.close();
        results.close();
        test.closeConnect();
       }catch (Exception e) {
            fail("Unexpected exception thrown");
       }


   }
   
   /*
    @Test
public void dbConnectTest() {
    try{
    ReadDatabase testConnection = new ReadDatabase();

    }catch(SQLException e) {
        fail();
    }
}

@Test 
public void dbCloseTest() {

}
*/


}
