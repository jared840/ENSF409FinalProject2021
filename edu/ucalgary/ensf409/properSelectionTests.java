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



public class properSelectionTests {
    public properSelectionTests(){

    }

     /**
    CASE: Successful choice of ID based on order and data
  Test creates a calculator with the goal of finding one of an item in given data. 
  First asserts that the getMatchFound method returns true, as it should be true for a successful choice
  Then, test compares the arrays of the expected selected IDs and what actually was returned from getChosenItems()
  Uses loops to ensure the size of the arrays match, and that it contains the exact same elements, and therefore contain the same IDs (order doesn't matter)

    */
    @Test
    public void chooseOneExecutiveChair() {
        String [][] dataSet = {
            {"ID", "Type", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuId"},
            {"C2483", "Executive", "Y", "Y", "N", "N", "175", "002"},
            {"C5784", "Executive", "Y", "N", "N", "Y", "150", "004"},
            {"C7268", "Executive", "N", "N", "Y", "N", "75", "004"}
        };

        Calculator a = new Calculator(dataSet, "chair", "1");
        a.fillChosenItemsArray();

        boolean matched = a.getMatchFound();
        assertEquals("Incorrectly failed to find one Executive Chair: ", true, matched);

        String [] expSelectedIDs = {"C2483", "C5784", "C7268"};
        String [] selectedIDs = a.getChosenItems();
        boolean arrayMatch = false;
        int matchCount = 0;
        if(selectedIDs.length == expSelectedIDs.length) {               //step one: ensure they are the same length
            arrayMatch = true;
        
        for(int i = 0; i<selectedIDs.length; i++) {
            for(int j = 0; j<expSelectedIDs.length; j++) {
                if(selectedIDs[i].equals(expSelectedIDs[j])){
                    matchCount++;
                }
            }
        }
    }
    if(matchCount == expSelectedIDs.length) {
        arrayMatch = true;
    }

        assertTrue("Chosen IDs returned from Calculator's getChosenItems method does not match expected chosen items: ", arrayMatch);
    }

     /**
Case: Successful choice of IDs based on data
Same test as above, but tested method getChosenItemsString() instead 
    */
    @Test
    public void chooseOneExecutiveChairAgain() {
        String [][] dataSet = {
            {"ID", "Type", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuId"},
            {"C2483", "Executive", "Y", "Y", "N", "N", "175", "002"},
            {"C5784", "Executive", "Y", "N", "N", "Y", "150", "004"},
            {"C7268", "Executive", "N", "N", "Y", "N", "75", "004"}
        };

        Calculator a = new Calculator(dataSet, "chair", "1");
        a.fillChosenItemsArray();

        
        String expectedSelectedIDs = "C2483 C5784 C7268";
        String expectedSelectedIDOne = "C2483"; 
        String expectedSelectedIDTwo = "C5784";
        String expectedSelectedIDThree = "C7268";
        String selectedIDs = a.getChosenItemsString();
        //System.out.println(selectedIDs);

        boolean stringMatch = true;

        if(expectedSelectedIDs.length() != selectedIDs.length()) {
            stringMatch = false;
        }
        if(selectedIDs.contains(expectedSelectedIDOne)!=true || selectedIDs.contains(expectedSelectedIDTwo)!=true || selectedIDs.contains(expectedSelectedIDThree)!=true) {
            stringMatch = false;
        }

        assertTrue("getChosenItemString method of calculator failed to return expected String: ", stringMatch);
        
    }

    /**
  CASE: Too many requested items, expect exception 
  Creates a calculator and attempts to select too many of the item
  Test expects to catch a nullpointerexception from getChosenItems() call, and fails if none is thrown by the method

    */
    @Test 
    public void chooseTooMany() {
        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };

        try{
        Calculator a = new Calculator(dataSet, "lamp", "10");
        a.fillChosenItemsArray();

        //expected return = nullpointer exception ("no matches found")
        a.getChosenItems();
        fail("getChosenItems method in calculator failed to throw nullpointerexception for impossible quantity request");
        } catch(NullPointerException e) {
            assertEquals("Failed to throw correct exception for impossibly high quantity", "No matches found", e.getMessage());
        }
        
    }

    /**
    Test lower boundary of 0 selected items from dataset. Expects that getChosenItems will not succeed in choosing zero, and ensures this by asserting nullpointerexception.
    */
    @Test 
    public void chooseZero() {
        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };

        try{
        Calculator a = new Calculator(dataSet, "lamp", "0");
        a.fillChosenItemsArray();

        //expected return = nullpointer exception ("no matches found")
        a.getChosenItems();
        fail("getChosenItems method in calculator failed to throw nullpointerexception for impossible quantity request");
        } catch(NullPointerException e) {
            assertEquals("Failed to throw correct exception for impossibly zero quantity", "No matches found", e.getMessage());
        }
        
    }
   
      
    /**
    Tests lowest boundary exception of negative number requested (impossible with readInput's re-entry configuration therefore expect NullPointerException)
    */
 @Test 
    public void chooseNegative() {
        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };

        try{
        Calculator a = new Calculator(dataSet, "lamp", "-5");
        a.fillChosenItemsArray();

        //expected return = nullpointer exception ("no matches found")
        a.getChosenItems();
        fail("getChosenItems method in calculator failed to throw nullpointerexception for impossible quantity request");
        } catch(NullPointerException e) {
            assertEquals("Failed to throw correct exception for impossibly low quantity", "No matches found", e.getMessage());
        }
        
    }
     /**
    Case: No possible selection. Should go to catch block and not continue with calculations.
    */
    @Test 
    public void noPossible() {
         String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"N",	"N",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L223",	"Study",	"N",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };
try{
Calculator a = new Calculator(dataSet, "lamp", "1");
 a.fillChosenItemsArray();
System.out.println(a.getChosenItemsString());
a.getPrice();
fail("Did not throw exception that allows for errorMessage and manufacturer list to be printed");
} catch (Exception e) {
	//this is where errorMessage call should be
}
    }

}
