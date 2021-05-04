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


public class priceTests {
    public priceTests() {

    }

     /**
  Case: Successful retrieval(via getChosenItems) of 2 ids for 1 task chair given data
  Asserts that retrieved items from getChosenItems matches the expected from the mock data

    */
    @Test 
    public void calculatorTest2() {
        System.out.println("calculatorTest2");

        String [][] dataSet = {
            {"ID", "Task", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuID"},
            {"C0914", "Task", "N", "N", "Y", "Y", "50", "002"},
            {"C1148", "Task", "Y", "N", "Y", "Y", "100", "005"},
            {"C3405", "Task", "Y", "Y", "N", "N", "100", "003"}
        };

Calculator a = new Calculator(dataSet, "chair", "1");
a.fillChosenItemsArray();
String [] expResult = {"C0914", "C3405"};

String[] result = a.getChosenItems();
assertArrayEquals(expResult, result);

    }

/**
CASE: Successful price calculation of 1 task chair given data using calculator and getPrice()
Asserts that the expected price and the calculated price via fillChosenItemsArray and getPrice match
*/
@Test
public void testPriceCalculation() {
String [][] dataSet = {
    {"ID", "Task", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuID"},
    {"C0914", "Task", "N", "N", "Y", "Y", "50", "002"},
    {"C1148", "Task", "Y", "N", "Y", "Y", "100", "005"},
    {"C3405", "Task", "Y", "Y", "N", "N", "100", "003"}
};

Calculator priceTest  = new Calculator(dataSet, "chair", "1");
priceTest.fillChosenItemsArray();
String expResult = "150";
String priceResult = priceTest.getPrice();
assertEquals("Calculated price incorrectly: ", expResult, priceResult);
}

/**
CASE: Successful test of price calulation for 1 small filing using getPrice() and calculator class
Asserts that the expected price and the calculated price via fillChosenItemsArray and getPrice match
*/
@Test 
public void testPriceFiling() {
    String [][] dataSet = {
        {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"},
        {"F001", "Small", "Y", "Y", "N", "50", "005"}, 
        {"F004", "Small", "N", "Y", "Y", "75", "004"}, 
        {"F005", "Small", "Y", "N", "Y", "75", "005"},
        {"F006", "Small", "Y", "Y", "N", "50", "005"}, 
        {"F013", "Small", "N", "N", "Y", "50", "002"}
    };

    Calculator selectTest = new Calculator(dataSet, "filing", "1");
    selectTest.fillChosenItemsArray();
    String expResult = "100";
    String realResult = selectTest.getPrice();
    assertEquals("Calculated price incorrect: ", expResult, realResult);
}

    /**
CASE: Successful calculation of 1 study lamp using calculator and getPrice()
Asserts that the expected price and the calculated price via fillChosenItemsArray and getPrice match
*/
@Test 
public void testPriceLamp() {
    String [][]dataSet = {
        {"ID", "Type", "Base", "Bulb", "Price", "ManuID"},
        {"L223", "Study", "N", "Y", "2", "002"},
        {"L928", "Study", "Y", "Y", "10", "002"},
        {"L980", "Study", "N", "Y", "2", "004"}, 
        {"L982", "Study", "Y", "N", "8", "002"}
    };

    Calculator priceLampTest = new Calculator(dataSet, "lamp", "1");
    priceLampTest.fillChosenItemsArray();
    String expPrice = "10";
    String realPrice = priceLampTest.getPrice();
    assertEquals("Calculated price incorrect: ", expPrice, realPrice);
}

   /**
CASE: Price calculation for zero elements - expects no exception  (ie don't calculate anything with no data therefore expects price=0)
Test asserts that an ArrayIndexOutOfBoundsException is not thrown and fails otherwise (when trying to call getPrice() with zero elements)
Asserts price calculated = 0 when empty data passed.

*/ 
@Test //test for zero elements!
public void testZeroElementsLamp() {
    String[][]dataSet = {
        {"ID", "Type", "Base", "Bulb", "Price", "ManuID"}
    };
try{
    String expectedPrice = "0";
    Calculator c = new Calculator(dataSet, "lamp", "1");
    
    String price = c.getPrice();
    
    assertEquals("Calculated price is incorrect, should be zero with no data: ", expectedPrice, price);      
} catch (ArrayIndexOutOfBoundsException e) {
    fail("Calculator throws exception by trying to calculate data with empty input rather than doing nothing");
    //only enters block if exception occurs (which is not what is expected), therefore fail the test!
    
}

    
}

    /**
CASE: Test for price calculation of 1 traditional desk(expects success)
Asserts that the expected price and the calculated price via fillChosenItemsArray and getPrice match

*/
@Test //test for desk
public void testDeskPrice() {
    String [][]dataSet = {
        {"ID", "Type", "Legs", "Top", "Drawer", "Price", "ManuID"},
        {"D0890", "Traditional", "N", "N", "Y", "25", "002"}, 
        {"D4231", "Traditional", "N", "Y", "Y", "50", "005"},
        {"D8675", "Traditional", "Y", "Y", "N", "75", "001"},
        {"D9352", "Traditional", "Y", "N", "Y", "75", "002"}
    };
    Calculator d = new Calculator(dataSet, "desk", "1");
    d.fillChosenItemsArray();
    String priceExpected = "100";
    String realPrice = d.getPrice();

    assertEquals("Failed to find cheapest price for 1 traditional desk: ", priceExpected, realPrice);
}



}
