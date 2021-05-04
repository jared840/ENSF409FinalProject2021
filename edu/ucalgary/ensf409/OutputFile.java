/**  
* Class for creating an output file with the full transaction information.
* @author         Chace Nielson
* @author         Jordan Lundy
* @author         Jared Lundy
* @author         Tony Vo
* @version        1.1
* @since          2.0
*/

package edu.ucalgary.ensf409;

import java.io.BufferedWriter;
import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.Date;

/**
* Example format:
*
*    Furniture Order Form
*
*    Faculty Name:
*    Contact:
*    Date:
*
*    Original Request: mesh chair, 1
*
*    Items Ordered
*    ID: 
*    ID: 
*
*    Total Price: $XXX.XX
* Faculty name and Contact are left blank,
* although the program provides options to change
* fields in future versions.
* Date is filled with current data.
*/
public class OutputFile {
    // Name of output file
    private String outFile;
    // Contains information that the user inputted for their request
    private String orig;
    // The items that match that order from the Calculator object
    private String[] items;
    // Total price of the transaction
    private String price;
    
    // Faculty name, contact name, and date of the request made
    private String facultyName;
    private String contact;
    private Date date;
   
    /**
     * Constructor.
     * @param outFile Name of output fule
     * @param orig  Original request information from user
     * @param items  A String array containing the items being sold that have been removed from the database
     * @param price The total price of those items 
     */
    public OutputFile(String outFile, String orig, String[] items, String price, String facultyName, String contact) {
        this.outFile = outFile;
        this.orig = orig;
        
        // Initialize array items to length of argument items array
        this.items = new String[items.length];
        
        // Iterate over array, copying items over
        for (int i = 0; i < items.length; i++) {   
            this.items[i] = items[i];   
        }

        this.price = price;
        
        // Can be changed if another argument was added to constructor
        this.facultyName = "";
        this.contact = "";
        
        // Set the date of when the request was made
        this.date = new Date();
    }
    
    /**
     * Create a file with the appropriate format.
     * If file can't be made, print "----File cannot be made----" to console.
     * @return true if file can be made successfully
     */
    public boolean makeFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile)); 

            bw.write("Furniture Order Form\n\n");
            bw.write("Faculty Name: " + facultyName + "\n");
            bw.write("Contact: " + contact+ "\n");
            bw.write("Date: " + date.toString()+ "\n"+ "\n"); // Gives the date in full
            bw.write("Original Request: " + orig + "\n"); // Provide the original order
            bw.write("\nItems Ordered\n");
            
            // Write the items purchased
            for (int i = 0; i < items.length; i++) {
                bw.write("ID: " + items[i]);
                if(i < items.length - 1) {
                    bw.write("\n");
                }
            }
            
            bw.write ("\n\nTotal Price: $" + price); // write the total combined price
            // Close the BufferedWriter object
            bw.close(); 
            
            // File made successfully
            return true;
        } catch (Exception e) {
            System.out.println(e + "----File cannot be made----");
            // File failed to be created successfully
            return false;
        }
    }
}
