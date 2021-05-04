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

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class OutputFileTest {
    
    public OutputFileTest() {
    }
    
    /**
    Case: successful creation of output file
    Tests that OutputFile constructor works and that it's makeFile method creates the correct output file.
    Asserts that the boolean returned from makeFile() is true, indicating a successful creation of the output file
    */
    @Test
    public void makeFileTest() {
        System.out.println("makeFileTest");
        

        String outFile = "TestFile.txt";
        String originalStatement = "mesh chair, 1";
        String [] items = {"L928" , "L223" , "L980"};
        String price = "150";

        OutputFile a = new OutputFile(outFile, originalStatement, items,  price, "",  "" );//pass 2d String array with database information to the
        boolean result = a.makeFile();

        boolean expResult = true;

        assertEquals("File to create file: ", expResult, result);
    }

//(expected = IllegalArgumentException.class)
    /**
    Case: Unsuccessful output file creation. Test expects a successful call to the outputfile constructor, however, 
    it expects that the boolean returned from makeFile() is now false, as given the data it must fail to make an output file
    */
    @Test
    public void failToMakeFile() {
        System.out.println("failToMakeFile");
        
        String outFile = ""; // invalid file name
        String originalStatement = "mesh chair, 1";
        String [] items = {"L980"};
        String price = "15";

        OutputFile a = new OutputFile(outFile, originalStatement, items,  price, "",  "" );
        boolean result = a.makeFile();

        boolean expResult = false;

        assertEquals("File to create file: ", expResult, result);
    }
    




}//end class

        
