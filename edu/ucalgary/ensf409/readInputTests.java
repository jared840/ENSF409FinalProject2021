

/**  
* 
* @author         Jared Lundy
* @author         Jordan Lundy
* @author         Chace Nielson
* @author         Tony Vo
* @version        1.0
* @since          1.0
*/
package edu.ucalgary.ensf409;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.sql.SQLException;

import org.junit.*;

import jdk.jfr.Timestamp;

//The goal of these tests is to determine whether calculator chooses the correct items based on database data, and returns the proper items (mewntioned specific methods tested)



public class readInputTests {
    public readInputTests(){

    }

    /**
    Case: Command Line test for furniture item 
    Tests that the item entered on command line is recognized and set by readInput 
    Asserts that returned item from getFurnitureItem is one of the valid items and therefore set properly to command line argument 
   */ 
    @Test                                                                   
    
    public void testReadInput() {
        System.out.println("-----------PLEASE ENTER A VALID FURNITURE ITEM, TYPE AND QUANTITY TO TEST READINPUT CMD LINE RECOGNITION (3/4)-------------");
        System.out.println();
        System.out.println("***Note: improper/invalid input will prompt re-entry to that field***");
        ReadInput testIn = new ReadInput();

        String item = testIn.getFurnitureItem();
        boolean recognizeValidItemArg = false;
        
        if(item.equals("chair") || item.equals("filing") || item.equals("desk") || item.equals("lamp")){
            recognizeValidItemArg = true;
        }

        assertTrue("ReadInput was unable to recognize a valid command line furniture type: ", recognizeValidItemArg);
        //System.out.println("!!!!! Successfully recognized a valid command line argument for furniture type!!!!!!");


    }

       /**
    Case: Command Line test for furniture type
    Tests that the type entered on command line is recognized and set by readInput 
    Asserts that returned item from getFurniturType is one of the valid items and therefore set properly to command line argument
    */
    @Test 
 
    public void testReadInput2() {
        System.out.println("-----------PLEASE ENTER A VALID FURNITURE ITEM, TYPE AND QUANTITY TO TEST READINPUT CMD LINE RECOGNITION (2/4)-------------");
        System.out.println();
        System.out.println("***Note: improper/invalid input will prompt re-entry to that field***");
        ReadInput testIn = new ReadInput();

        String item = testIn.getFurnitureType();
        boolean recognizeValidTypeArg = false;
        
        if(item.equals("mesh") || item.equals("executive") || item.equals("task") || item.equals("kneeling") || item.equals("ergonomic")){
            recognizeValidTypeArg = true;
        }
        else if (item.equals("traditional")|| item.equals("adjustable") || item.equals("standing")  ){
            recognizeValidTypeArg = true;
        }
        else if(item.equals("small") || item.equals("large") || item.equals("medium")) {
            recognizeValidTypeArg = true;
        }
        else if (item.equals("desk") || item.equals("swing arm") || item.equals("study")){
            recognizeValidTypeArg = true;
        }

        assertTrue("ReadInput was unable to recognize a valid command line furniture type: ", recognizeValidTypeArg);
    }

     /**
    Case: Command Line test for furniture getRequest 
    Tests that the item entered on command line is recognized and returned by getRequest
    Asserts that returned item from getRequest is one of the valid items and therefore set properly to command line argument
    Asserts that getRequest matches the item, type and quantity fields set from the command line argument

    */
    @Test 
   
    public void testReadInput3() {
        System.out.println("-----------PLEASE ENTER A VALID FURNITURE ITEM, TYPE AND QUANTITY TO TEST READINPUT CMD LINE RECOGNITION (1/4)-------------");
        System.out.println();
        System.out.println("***Note: improper/invalid input will prompt re-entry to that field***");
        ReadInput testIn = new ReadInput();

        int itemQuantity = testIn.getFurnitureQuantity();
        String itemType = testIn.getFurnitureType();
        String itemItem = testIn.getFurnitureItem();
        String expReturn = itemType + " " + itemItem + ", " + String.valueOf(itemQuantity);
       // System.out.println(testIn.getRequest());
       // System.out.println(expReturn);

        boolean returnCorrect = expReturn.equals(testIn.getRequest());

        assertTrue("getRequest method of readInput failed to return correctly formatted request based on command line arguments: ", returnCorrect);
    }
    /**
Case: tests that ReadInput private members will never be null due to its re-entry configuration for invalid inputs (ie. seeks re-entry when invalid input).
*/
@Test
public void nullInput() {

        System.out.println("-----------PLEASE ENTER A VALID FURNITURE ITEM, TYPE AND QUANTITY TO TEST READINPUT CMD LINE RECOGNITION (4/4)-------------");
        System.out.println();
        System.out.println("***Note: improper/invalid input will prompt re-entry to that field***");
        ReadInput testIn = new ReadInput();

assertNotNull("Private member incorrectly set to null: " ,testIn.getFurnitureType());
assertNotNull("Private member incorrectly set to null: " ,testIn.getFurnitureItem());
assertNotNull("Private member incorrectly set to null: " ,testIn.getFurnitureQuantity());
}

}
