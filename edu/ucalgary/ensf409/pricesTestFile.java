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
Description

The most basic test file to test most basic functionality: 
      That 1 selected item can be selected and can successfully calculate the price
*/
package edu.ucalgary.ensf409;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.*;

import jdk.jfr.Timestamp;

public class pricesTestFile {
    public pricesTestFile(){

    }

    
         /**
CASE: Successful calculation of only one item's price; testing (possible) 1 filing given data

      */
    @Test 
    public void calculatorTest1() {
        System.out.println("calculatorTest1");

        String [][] dataSet = {
            {"ID", "Type", "Rails", "Drawers", "Cabinet", "Price", "ManuID"}, 
            {"F001", "Small", "Y", "Y", "N", "50", "005"}, 
            {"F004", "Small", "N", "Y", "Y", "75", "004"}, 
            {"F005", "Small", "Y", "N", "Y", "75", "005"}, 
            {"F006", "Small", "Y", "Y", "N", "50", "005"},
            {"F013", "Small", "N", "N", "Y", "50", "002"}
        };
        Calculator a = new Calculator(dataSet, "filing", "1");
        
        a.fillChosenItemsArray();
        boolean matched = a.getMatchFound();

        String expResult = "100";

        String realResult = a.getPrice();

        assertEquals("Incorrectly failed to find a match:", true, matched);
        assertEquals("Price incorrectly calculated: ", expResult, realResult);
    }


   /**
CASE: Successful calculation of only one item's price; testing (possible) 1 chair given data

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
boolean matched = a.getMatchFound();
//a.printInfo();
//a.fillChosenItemsArray();
String expResult = "150";

String realResult = a.getPrice();
//System.out.println(a.getPrice());

assertEquals("Incorrectly failed to find match:", true, matched);
assertEquals("Price incorrectly calculated: ", expResult, realResult);

    }

      /**
CASE: Successful calculation of only one item's price; testing (possible) 1 chair given data


    */

    @Test 
    public void calculatorTest3() {
        System.out.println("calculatorTest3");

        String [][] dataSet = {
            {"ID", "Task", "Legs", "Arms", "Seat", "Cushion", "Price", "ManuID"},
            {"C0914", "Task", "N", "N", "Y", "Y", "50", "002"},
            {"C1148", "Task", "Y", "N", "Y", "Y", "100", "005"},
            {"C3405", "Task", "Y", "Y", "N", "N", "100", "003"}
        };

Calculator a = new Calculator(dataSet, "chair", "1");

a.fillChosenItemsArray();
boolean matched = a.getMatchFound();
String [] expResult = {"C0914", "C3405"};

String[] result = a.getChosenItems();
assertEquals("Incorrectly failed to find match:", true, matched);
assertArrayEquals(expResult, result);

    }


       /**
CASE: Successful calculation of only one item's price; testing (possible) 1 lamp given data


    */
    @Test 
    public void calculatorTest4() {
        System.out.println("calculatorTest4");

        String [][]dataSet = {
            {"ID", "Type", "Base", "Bulb", "Price", "ManuID"}, 
            {"L053", "Swing Arm", "Y", "N", "27", "002"}, 
            {"L096", "Swing Arm", "N", "Y", "3", "002"},
            {"L487", "Swing Arm", "Y", "N", "27", "002"}, 
            {"L879", "Swing Arm", "N", "Y", "3", "005"}
        };

        Calculator a = new Calculator(dataSet, "lamp", "1");
        
        a.fillChosenItemsArray();
        boolean matched = a.getMatchFound();
        String expResult = "30";

        String realResult = a.getPrice();
        assertEquals ("Incorrectly failed to find match:", true, matched);
        assertEquals("Price incorrectly calculated: ", expResult, realResult);

    }

    
   /**
CASE: Successful calculation of only one item's price; testing (possible) 1 desk given data


*/ 
@Test 
public void calculatorTest5() {
    System.out.println("calculatorTest5");

    String [][]dataSet = {
        {"ID", "Type", "Legs", "Top", "Drawer", "Price", "ManuID"}, 
        {"D0890", "Traditional", "N", "N", "Y", "25", "002"},
        {"D4231", "Traditional", "N", "Y", "Y", "50", "005"},
        {"D8675", "Traditional", "Y", "Y", "N", "75", "001"},
        {"D9352", "Traditional", "Y", "N", "Y", "75", "002"}
    };

    Calculator a = new Calculator(dataSet, "desk", "1");
   
    a.fillChosenItemsArray();
    boolean matched = a.getMatchFound();
    String expResult = "100";

    String realResult = a.getPrice();
    assertEquals("Incorrectly failed to find match", true, matched);
    assertEquals("Price incorrectly calculated: ", expResult, realResult);

}

}
