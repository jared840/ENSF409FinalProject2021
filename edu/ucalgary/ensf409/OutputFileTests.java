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
Description:
A Test file that tests the functionality of creating the output file using makeFile method. 
This test tests both the successful creation of an output test file, 
using mock data, a calculator to calculate the price and select items, and the makeFile method in OutputFile class.
Also tests that in the case of an item not possibly being made, an appropraite message with manufacturers is made via errorMessage method of OutputFile

*/

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;
import java.io.File;

import jdk.jfr.Timestamp;

public class OutputFileTests {
    public OutputFileTests(){

    }

     /**
      Case: Successful generation of output file
      Test makes an output file Furniture_Order_Form.txt and ensures that the makeFile method indeed creates this output file successfully, as expected 
    */
    @Test 
   
    public void outputFileCreationTest() {
        String [][] manufactures = {
            { "001"    , "Academic Desks"       , "236-145-2542" , "BC"},
            { "002"    , "Office Furnishings"   , "587-890-4387" , "AB"},
            { "003"    , "Chairs R Us"          , "705-667-9481" , "ON"},
            { "004"    , "Furniture Goods"      , "306-512-5508" , "SK"},
            { "005"    , "Fine Office Supplies" , "403-980-9876" , "AB"}    
        };

        String [][] dataSet = {
            { "ID", "type","Rails", "Drawers" , "Cabinets", "Price", "ManuID"},
            {"F012" , "Large" , "N" , "Y"    ,"N"   ,  "75"  , "005"    },
            {"F015" , "Large" , "Y" , "N"    ,"N"   ,  "75"  , "004"    },
            {"F003" , "Large" , "N" , "N"    ,"Y"   ,  "150" , "002"    },
            {"F010" , "Large" , "Y" , "N"    ,"Y"   ,  "225" , "002"    },
            {"F011" , "Large" , "N" , "Y"    ,"Y"   ,  "225" , "005"    }    
        };

        ReadInput test = new ReadInput();

        Calculator a = new Calculator(dataSet, "filing", "1");
        a.fillChosenItemsArray();

        assertTrue("Failed to find match when match possible ", a.getMatchFound());

        String myFile = "Furniture_Order_Form.txt";
        OutputFile theFile = new OutputFile(myFile, test.getRequest(), a.getChosenItems(), a.getPrice(), "", "");
        File myFileName = new File(myFile);

        boolean made = theFile.makeFile();

        assertTrue("Failed to generate output order text file: ", myFileName.exists());
    }

 /**
    Case: errorMessage for no OutputFile being able to be created is outputted 
    This test tests the errorMessage method of calculator when no match is able to be made, and therefore no file is to be outputted
    Tests that the errorMessage method indeed creates the expected manufacturer message 
     */
    @Test 
   
    public void noOutputTest() {
        String [][] manufactures = {
            { "001"    , "Academic Desks"       , "236-145-2542" , "BC"},
            { "002"    , "Office Furnishings"   , "587-890-4387" , "AB"},
            { "003"    , "Chairs R Us"          , "705-667-9481" , "ON"},
            { "004"    , "Furniture Goods"      , "306-512-5508" , "SK"},
            { "005"    , "Fine Office Supplies" , "403-980-9876" , "AB"}    
        };

        String [][] dataSet = {
            { "ID", "type","Rails", "Drawers" , "Cabinets", "Price", "ManuID"},
            {"F012" , "Large" , "N" , "Y"    ,"N"   ,  "75"  , "005"    },
            {"F003" , "Large" , "N" , "N"    ,"Y"   ,  "150" , "002"    },
            {"F011" , "Large" , "N" , "Y"    ,"Y"   ,  "225" , "005"    }    
        };

        try{
        Calculator a = new Calculator(dataSet, "filing", "2");  //impossible request given mock database dataSet
        a.fillChosenItemsArray();
        //System.out.println(a.errorMessage("filing", "large"));
        String [] appropriateManufacturers = new String[5];            //max 5 since 5 possible manufacturers
        int matchCount = 0;
        boolean messageMatch = false;

        for(int i = 0; i<manufactures.length; i++) {
            if(manufactures[i][0].equals("002")){
                appropriateManufacturers[matchCount] = manufactures[i][1];
                matchCount++;
            }
            else if(manufactures[i][0].equals("005")){
                appropriateManufacturers[matchCount] = manufactures[i][1];
                matchCount++;
            }
        }
        String expectedNoOutputMessage = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are " + appropriateManufacturers[matchCount-1] + ", and " + appropriateManufacturers[matchCount-2] + ".";
        //System.out.println(expectedNoOutputMessage);
        String expectedNoOutputMessageReverseOrder = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are " + appropriateManufacturers[matchCount-2] + ", and " + appropriateManufacturers[matchCount-1] + ".";

        if(a.errorMessage("filing", "large").equals(expectedNoOutputMessage) || a.errorMessage("filing", "large").equals(expectedNoOutputMessageReverseOrder)){
            messageMatch = true;
        }

        System.out.println();
        System.out.println(a.errorMessage("filing", "large"));
        System.out.println(expectedNoOutputMessageReverseOrder);
        assertTrue("errorMessage failed to retrieve applicable manufacturers and/or display error message: ", messageMatch);

        } catch (Exception e) {

        }
        
    }


}
