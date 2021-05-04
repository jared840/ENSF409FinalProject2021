
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

import org.junit.*;

import jdk.jfr.Timestamp;

public class multiplePriceTests {
    public multiplePriceTests(){

    }

      /**
      Case: Calculate price of two mesh Chairs, an impossible request
      Using a mock database dataset, creates a calulator object with the dataset and 2 mesh chairs specified
      Checks that getMatchFound returns false, since two mesh chairs is impossible with this data 
      Checks that getChosenItems method and getChosenItemsString throws an expected NullPointerException, if not fails the test  
    */
    @Test
  
    public void twoMeshChairsPrice() {
        String [][]dataSet = {
            {"ID", "Type", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuID"},
            {"C0942", "Mesh", "Y", "N", "Y", "Y", "100", "005"},
            {"C6748", "Mesh", "Y", "N", "N", "N", "75", "003"},
            {"C8138", "Mesh", "N", "N", "Y", "N", "75", "005"},
            {"C9890", "Mesh", "N", "Y", "N", "Y", "50", "003"}
        };
        String [][] manufactures = {
            { "001"    ,  "Academic Desks"       , "236-145-2542" , "BC"},
            { "002"    ,  "Office Furnishings"   , "587-890-4387" , "AB"},
            { "003"    ,  "Chairs R Us"          , "705-667-9481" , "ON"},
            { "004"    ,  "Furniture Goods"      , "306-512-5508" , "SK"},
            { "005"    ,  "Fine Office Supplies" , "403-980-9876" , "AB"}    
        };

        Calculator a = new Calculator(dataSet, "chair", "2");
        a.fillChosenItemsArray();

        boolean matched = a.getMatchFound();

        //String expectedPrice = "0"; //shouldn't create a price -> impossible request

        String realPrice = a.getPrice();
       //System.out.println(a.errorMessage(manufactures));

        assertEquals("Should not find match with current inventory:", false,matched);

        try{
            a.getChosenItems();
           
            fail("Expected exception not thrown");     //if no exception thrown (ie. catch block not entered ), then fail the unit test)

        } catch(NullPointerException e) {
            
            assertEquals("getChosenItem method failed to throw nullpointerexception when no matches possible", "No matches found", e.getMessage());
        }

    try{
        a.getChosenItemsString();
            fail("Expected exception not thrown");     //if no exception thrown (ie. catch block not entered ), then fail the unit test)
    } catch(NullPointerException e) {
        assertEquals("getChosenItemsString failed to throw null pointer exception for no matches", "No matches found", e.getMessage());
    }
        

        //assertEquals("Price incorrectly calculated; impossible request therefore should be zero", expectedPrice, realPrice);
    }

     /**
    Case: Successfully calculates price of two adjustable chairs
    Creates calculator with mock database data and calculates price of two adjustable desks
    Asserts that the expected price from the data and the calculated price are equal 
    */
    @Test
   
    public void twoAdjustableDesksPrice() {
        String [][] dataSet = {
            {"ID", "Type", "Legs", "Top", "Drawer", "Price", "ManuID"},
            {"D1030", "Adjustable", "N", "Y", "N", "150", "002"},
            {"D2746", "Adjustable", "Y", "N", "Y", "250", "004"},
            {"D3682", "Adjustable", "N", "N", "Y", "50", "005"},
            {"D4475", "Adjustable", "N", "Y", "Y", "200", "002"},
            {"D5437", "Adjustable", "Y", "N", "N", "50", "001"},
            {"D7373", "Adjustable", "Y", "Y", "N", "350", "005"}
        };

        Calculator a = new Calculator(dataSet, "desk", "2");
        a.fillChosenItemsArray();
        boolean matched = a.getMatchFound();

        String expectedPrice = "700";

        String realPrice = a.getPrice();
        String [] expectedArray = {"D3682", "D5437", "D1030", "D4475", "D2746"};
        String [] resultsArray = a.getChosenItems();
        for(int i = 0; i<resultsArray.length;i++) {
            System.out.print(resultsArray[i] + " ");
        }

        assertArrayEquals("Incorrectly chosen cheapest items to build furniture:", expectedArray, resultsArray);
        assertEquals("Incorrectly failed to find a match:", true, matched);
        assertEquals("Price incorrect for 2 adjustable desks: ", expectedPrice, realPrice);
    }
    
     /**
      Case: Price of three adjustable desks; an impossible request
      Test creates calculator with mock data and tries to calculate for a number of adjustable desks that are not possible (beyon upper limit)
  Therefore test ensures boolean returned is false, and that both getChosenItems and getChosenItemsString return nullpointerexception
    */
    @Test 
   
    public void threeAdjustableDesksPrice() {
        String [][] dataSet = {
            {"ID", "Type", "Legs", "Top", "Drawer", "Price", "ManuID"},
            {"D1030", "Adjustable", "N", "Y", "N", "150", "002"},
            {"D2746", "Adjustable", "Y", "N", "Y", "250", "004"},
            {"D3682", "Adjustable", "N", "N", "Y", "50", "005"},
            {"D4475", "Adjustable", "N", "Y", "Y", "200", "002"},
            {"D5437", "Adjustable", "Y", "N", "N", "50", "001"},
            {"D7373", "Adjustable", "Y", "Y", "N", "350", "005"}
        };

        Calculator a = new Calculator(dataSet, "desk", "3");
        a.fillChosenItemsArray();
        boolean matched = a.getMatchFound();

        assertEquals("Incorrectly found 3 matches when impossible given specific data: ", false,matched);
        try{
            String[] results = a.getChosenItems();
            fail("Expected exception not thrown"); //if no exception thrown, fail the unit test
        }catch (NullPointerException e) {
            assertEquals("Failed to throw nullpointerexception when attempting to retrieve non-existant matching items: ", "No matches found", e.getMessage());
        }
        try{
            String resultString = a.getChosenItemsString();
            fail("Expected exception not thrown");
        }catch (NullPointerException e) {
            assertEquals("Failed to throw NullPointerException when trying to retrieve null String of no matches: ", "No matches found", e.getMessage());
        }
    }

     /**
Case: successful price calculation of three desk lamps 
Test creates calculator with the data for three desk lamps 
Ensures getPrice() matches expected price 
 */
 @Test 

 public void threeDeskLamps() {
     String [][] dataSet = {
         {"ID", "Type", "Base", "Bulb", "Price", "ManuID"}, 
         {"L013", "Desk", "Y", "N", "18", "004"},
         {"L112", "Desk", "Y", "N", "18", "005"},
         {"L132", "Desk", "Y", "N", "18", "005"},
         {"L208", "Desk", "N", "Y", "2", "005"},
         {"L342", "Desk", "N", "Y", "2", "002"},
         {"L564", "Desk", "Y", "Y", "20", "004"},
         {"L649", "Desk", "Y", "N", "18", "004"},
     };

     Calculator a = new Calculator(dataSet, "lamp", "3");
     a.fillChosenItemsArray();
     boolean matched = a.getMatchFound();

     String expectedPrice = "60";

     String realPrice = a.getPrice();

     assertEquals("Incorrectly failed to find 3 unique desk lamps: ", true, matched);
     assertEquals("Incorrect calculated price: ", expectedPrice, realPrice);
 }

 /**
Case: Exception for too many of an item requested (upper limit)
Calculator object created with request of 30 desk lamps 
Ensures getChosenItem and getChosenItemsString throw nullpointerexception and that getMatchFound is false 
*/
 @Test 

 public void tooManyDeskLamps() {
    String [][] dataSet = {
        {"ID", "Type", "Base", "Bulb", "Price", "ManuID"}, 
        {"L013", "Desk", "Y", "N", "18", "004"},
        {"L112", "Desk", "Y", "N", "18", "005"},
        {"L132", "Desk", "Y", "N", "18", "005"},
        {"L208", "Desk", "N", "Y", "2", "005"},
        {"L342", "Desk", "N", "Y", "2", "002"},
        {"L564", "Desk", "Y", "Y", "20", "004"},
        {"L649", "Desk", "Y", "N", "18", "004"},
    };

    Calculator a = new Calculator(dataSet, "lamp", "30");
     a.fillChosenItemsArray();
     boolean matched = a.getMatchFound();
     

     assertEquals("Should not find 30 matches in required dataset; impossible request; ", false, matched);
    
     //try catch to ensure that a nulpointer exception is thrown when trying to retrieve Chosen Items array when no items possibly chosen
        try{
            String [] chosenItems = a.getChosenItems();
            fail("Expected exception not thrown for upper limit");     //if no exception thrown (ie. doesnt go to catch block) fails test)
        }catch (NullPointerException e) {
            assertEquals("NullPointerException should've been thrown but was not: ","No matches found", e.getMessage());       //tell Chace to add this (statements to thrown exceptions) and to remove that first throws at beginning!!!!!!!!!
        }
        try{
            String chosenItemsString = a.getChosenItemsString();
            fail("Expected nullpointerexception not thrown for upper limit");
        }catch (NullPointerException e) {
            assertEquals("Nullpointer exception failed to be thrown for no retrieving matches when no matches possible: ", "No matches found", e.getMessage());
        }
    }

     /**
Case: Successful price calulation
Calculates price of two small filing cabinets, which should be successful 
 */
 @Test

 public void twoSmallFilingPrice() {
String [][] dataSet = {
    {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"},
    {"F001", "Small", "Y", "Y", "N", "50", "005"},
    {"F004", "Small", "N", "Y", "Y", "75", "004"},
    {"F005", "Small", "Y", "N", "Y", "75", "005"},
    {"F006", "Small", "Y", "Y", "N", "50", "005"},
    {"F013", "Small", "N", "N", "Y", "50", "002"}

 };

 Calculator a = new Calculator(dataSet, "filing", "2");
 a.fillChosenItemsArray();
 boolean matched = a.getMatchFound();
 String expPrice = "225";

 String realPrice = a.getPrice();

 assertEquals("Incorrectly failed to find 2 unique small filing matches: ", true, matched);
 assertEquals("Incorrect cheapest price calculated: ", expPrice, realPrice);
 }

     /** 
Case: too many calulation, should fail 

Another test that makes a calculator with too many requested items, and ensures that getChosenItem and getChosenItemsString throws nullpointerexceptions, and that getMatchFound is false
 */ 
 @Test 

 public void threeSmallFilingPrice() {
    String [][] dataSet = {
        {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"},
        {"F001", "Small", "Y", "Y", "N", "50", "005"},
        {"F004", "Small", "N", "Y", "Y", "75", "004"},
        {"F005", "Small", "Y", "N", "Y", "75", "005"},
        {"F006", "Small", "Y", "Y", "N", "50", "005"},
        {"F013", "Small", "N", "N", "Y", "50", "002"}
    
     };
    
     Calculator a = new Calculator(dataSet, "filing", "3");
     a.fillChosenItemsArray();
     boolean matched = a.getMatchFound();

     assertEquals("Incorrectly found 3 unique combinations for provided data; maximum possible is 2: ", false, matched);

     try{
         String results = a.getChosenItemsString();
         fail("Failed to throw expected NullPointerException");    //if no exception thrown -> fail test!
     }catch (NullPointerException e) {
         assertEquals("Did not correctly throw NullPointerException for supposed no items chosen: ", "No matches found", e.getMessage());
     }
     try{
         String [] resultsAsArray = a.getChosenItems();
         fail("Failed to throw expected NullPointerException");
     }catch(NullPointerException e) {
         assertEquals("Did not correctly throw NullPointerException for supposed no items chosen: ", "No matches found", e.getMessage());
     }
 }

     /**
Case: Lower boundary of empty input
Tests when dataSet is empty by ensuring that a instantiated calculator object will not throw ArrayIndexOutOfBoundsException from fillChosenItemsArray method when empty input is given, but calculate nothing
 */
 @Test

 public void testEmptyInput() {
    String [][] dataSet = {
        {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"}
       
    
     };
     try{
        Calculator a = new Calculator(dataSet, "filing", "2");
        a.fillChosenItemsArray();
        boolean matched = a.getMatchFound();
        String expPrice = "225";
       
        String realPrice = a.getPrice();
       
        
     } catch (ArrayIndexOutOfBoundsException e) {
         fail("Calculator threw exception due to attempting to calculate price with no data when it shouldn't");
     }//testing that trying to fillchosenitems array with no inputs will not give arrayoutofbounds exception, but do nothing with no data
    }

         /**
Case: Another empty input test 
Tests again that an empty input into the dataSet for a calculator will not throw an ArrayIndexOutOfBoundsException when calling fillChosenItemsArray method, nut only calculate nothing
     */
     @Test 

     public void testEmptyInput2() {
        String [][] dataSet = {
            {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"}
           
        
         };
         try{
            Calculator a = new Calculator(dataSet, "filing", "2");
            a.fillChosenItemsArray();
            boolean matched = a.getMatchFound();
            String expPrice = "225";
           
            String realPrice = a.getPrice();
            assertEquals("Incorrectly found matches for empty dataset: ", false, matched);
           
     }catch (ArrayIndexOutOfBoundsException e) {
        fail("Calculator throws exception instead of calculating nothing.");
    }



 }

 



 /** Case: NullPointerException test
    Tests that a nullpointer exception is thrown in the case that a calculator is made with an empty array passed to the constructor
    */
@Test 
   
public void nullPoint() {
    String [][]dataSet = new String[3][3];
try{
    Calculator a = new Calculator(dataSet, "chair", "2");
    fail("Failed to throw NullPointerException when null dataSet array passed to ctor");
} catch (Exception e) {
assertEquals("Failed to throw proper NullPointerException for empty/null array passed to ctor", null, e.getMessage());
}

}








}
