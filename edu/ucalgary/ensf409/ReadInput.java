/**  
* ReadInput.java
* Class is responsible for taking in user inputs for furniture item, type, and quantity.
* Before the data is stored, verify the data to make sure that it is valid.
* @author         Tony Vo
* @author         Chace Nielson
* @author         Jared Lundy
* @author         Jordan Lundy
* @version        2.1
* @since          1.0
*/
package edu.ucalgary.ensf409;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReadInput {
    private Scanner scanner;
    private String furnitureItem;
    private String furnitureType;
    private int furnitureQuantity;

    // Regular expression patterns for valid furniture items
    private static String objectREGEX = "(chair|desk|filing|lamp)";
    private static Pattern objectPATTERN = Pattern.compile(objectREGEX);

    // Regular expression patterns for valid furniture types
    private static String chairREGEX = "(task|mesh|kneeling|executive|ergonomic)";
    private static Pattern chairPATTERN = Pattern.compile(chairREGEX);

    private static String deskREGEX = "(traditional|adjustable|standing)";
    private static Pattern deskPATTERN = Pattern.compile(deskREGEX);

    private static String filingREGEX = "(small|medium|large)";
    private static Pattern filingPATTERN = Pattern.compile(filingREGEX);

    private static String lampREGEX = "(desk|swing arm|study)";
    private static Pattern lampPATTERN = Pattern.compile(lampREGEX);

    /**
     * Default constructor. Program will continuously ask user to input
     * furniture item, furniture type, and quantity until a valid input
     * is entered before moving onto the next input.
     * Inputs for the furniture item and furniture type are 
     * case insensitive.
     */
    public ReadInput() {
        scanner = new Scanner(System.in);

        boolean objectStatus = false;
        boolean typeStatus = false;
        boolean quantityStatus = false;

        do {
            System.out.print("Requested furniture item: ");
            objectStatus = setObject(scanner.nextLine());
        } while (!objectStatus);

        do {
            System.out.print("Type of " + this.furnitureItem + ": ");
            typeStatus = setType(scanner.nextLine());
        } while (!typeStatus);

        do {
            System.out.print("Number of " + this.furnitureType + " " + 
                this.furnitureItem + " requested: ");
            try {
                quantityStatus = setQuantity(scanner.nextInt());
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Quantity must be a positive integer.");
                scanner.next();
            }
            
        } while (!quantityStatus);
    } 

    /**
     * Sets the input to lowercase and trims leading/trailing spaces, then
     * matches input to objectREGEX to check if furniture item is valid.
     * If so, set furnitureItem to item.
     * @param item String to check if valid.
     * @return Boolean true if input is valid, false otherwise.
     */
    public boolean setObject(String item) {
        String cleanItem = item.toLowerCase().trim();

        if (!objectPATTERN.matcher(cleanItem).matches()) {
            return false;
        }

        this.furnitureItem = cleanItem;
        return true;
    }

    /**
     * Sets the input to lowercase and trims leading/trailing spaces, then 
     * checks REGEX pattern by furnitureItem to check if the type is valid. 
     * If so, set furnitureType to type.
     * @param type String to check if valid.
     * @return Boolean true if input is valid, false otherwise.
     */
    public boolean setType(String type) {
        String cleanType = type.toLowerCase().trim();

        if (furnitureItem.equals("chair")) {
            if (!chairPATTERN.matcher(cleanType).matches()) {
                return false;
            }
        } else if (furnitureItem.equals("desk")) {
            if (!deskPATTERN.matcher(cleanType).matches()) {
                return false;
            }
        } else if (furnitureItem.equals("filing")) {
            if (!filingPATTERN.matcher(cleanType).matches()) {
                return false;
            }
        } else if (furnitureItem.equals("lamp")) {
            if (!lampPATTERN.matcher(cleanType).matches()) {
                return false;
            }
        }

        this.furnitureType = cleanType;
        return true;
    }

    /**
     * Verify if the input is a positive quantity.
     * If so, set furnitureQuantity to value.
     * @param value int number of furniture items requested
     * @return Boolean true if input is valid, false otherwise.
     */
    public boolean setQuantity(int value) {
        if (value <= 0) {
            return false;
        }

        this.furnitureQuantity = value;
        return true;
    }

    // Gets furnitureItem
    public String getFurnitureItem() {
        return this.furnitureItem;
    }

    // Gets getfurnitureType
    public String getFurnitureType() {
        return this.furnitureType;
    }
    
    // Gets furnitureQuantity
    public int getFurnitureQuantity() {
        return this.furnitureQuantity;
    }

    // Returns a readable form of the full request
    public String getRequest() {
        return furnitureType + " " + furnitureItem + ", " 
            + Integer.toString(furnitureQuantity);
    }
}
