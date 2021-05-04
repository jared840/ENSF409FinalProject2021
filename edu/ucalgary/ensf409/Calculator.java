/**  
* Calculator.java 
* Calculates the best deal from a 2D array of database information.
* Then outputs an array containing items to be sold that make up the best deal.
* Throws a NullPointerException in getChosenItems if no match has been found. 
* Also outputs a boolean telling if a match of items was succesfully found.
* @author         Chace Nielson
* @author         Jared Lundy
* @author         Jordan Lundy
* @author         Tony Vo
* @version        2.3
* @since          1.0
*/

package edu.ucalgary.ensf409;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;
import java.lang.Math;

/**
 * Class to find items that make up the best deal and place them in a String array to be used by other classes.
 * Also, provides a boolean that is set to true when a furniture combination has been found.
 * Receives a 2D String array containing the information from the database for a specific type of furniture. 
 */
public class Calculator {
    // Array contains information about furniture (e.g. contains legs?)
    private String[][] items;
    // Type of furniture item (e.g. chair)
    private String category;
    // Number of furniture items that user wants to purchase
    private int quanitity;
    
    // Index of column that holds the price in each row
    private int priceIndex;
    
    // Contains all the ID of items to be sold
    private String[] chosenItems;
    
    // The price the items are to be sold at
    private int chosenPrice;

    // Ints for the row and column length of an item (excluding title row)
    private int rowLength;
    private int colLength;
    
    // Boolean set to true if a combination of furniture items succesfully found.
    private boolean matchFound = false;

    /**
     * Constructor.
     * Fills the items array with type of item (furniture) information.
     * Also sets the quantity of furniture items requested by user to purchase.
     * @param arg 2D String array with information of specfic type of item.
     *   Top line is the titles of each column; this row does not get added to the items array.
     * @param category Category of item
     * @param quantity Number of complete furniture items requested by user
     */
    public Calculator(String[][] arg , String category, String quantity) {
        
        // Go through first row (titles) to get which column contains prices
        for(int i = 0 ; i < arg[0].length; i++) { 
            if(arg[0][i].equals("Price")){
                //  Set price index to whichever column has the word "Price" in the title row
                priceIndex = i;
            }
        }
        
        // Make items a 2D array, with one less row (not counting the title row)
        items = new String[arg.length-1][arg[0].length]; 
        
        // Copy the 2D array
        for (int i = 0; i < arg.length-1; i++) {  
            for (int j = 0; j< arg[i].length; j++){    
                // Don't copy the title row; but only the furniture information rows
                items[i][j] = arg[i+1][j];
            }
        }

        this.category = category;
        // Convert quantity String to int
        this.quanitity = Integer.valueOf(quantity);

        // For simplicity when going through rows and columns of item array
        this.rowLength = items.length;
        this.colLength = arg[0].length; // set row and columns for the items array( which doesn't incluide the title row)
                                        // arg[0].length is same items[0].length unless no items inventory 
                                         // in which case would not matter since no price can be found   
        
        
        // Chosen item array - used for storage of deals of furniture combinations for user
        // Initially set to empty
        chosenItems = new String[0];

    }

    /**
     * Sorts the 2D by row based on the price in each row.
     * Calls calulateBestDeal() to fill chosen items array with items to be sold.
     * Sets matchFound to true if order can be completed; else, it is set to false.
     */
    public void fillChosenItemsArray(){
        sortItems(); // Sorts array
        calulateBestDeal(quanitity); // Call to recursive fucntion 
    }

     /**
      * Finds the best deal of combination of items.
      * Sets chosenItems array to contain the items to be sold and 
      * sets price to the combined price.
      * Sets matchFound to true if order can be completed succesfully.
      * 
      * @param quan number of complete items to purchase
      */
    private void calulateBestDeal(int quan){
        // Base case
        if (quan <= 0) {  
            return; 
        }
        
        String [] letters = createLetterArray();
        
        // ================ Test if single items is all Y or abcd ======================
        String oneItem = "";    
        int onePrice = 100000000; // Huge price that never will be reached
        
        // Go through rows of letterArray
        for (int i = 0 ; i < rowLength; i++) {
            String union = getDistinctString(letters[i]);
            
            if (rightData(union)){
                // Check this value isn't the same as what already in been put in chosen items
                if (matchesItemInChosenItemArray(items[i][0])) {
                    // Skip rest of loop since this word has already been put in chosenItems array
                    continue;
                }
                // Get ID and price of item
                oneItem = items[i][0];
                onePrice = Integer.valueOf(items[i][priceIndex]);
                // End loop don't try more options since this is the best deal if more than a single item has YYYY
                break; 
            }
        }

        //============= Test if 2 items can make up an item with all Y or abcd ========================  
        // For breaking the second loop once a match is found
        boolean break2 = false;
        // Array for two-item array
        String[] twoItem = new String[2];
        int twoPrice = 100000000; // Huge price that never will be reached
        // Go though rows of letterArray to compare 2 values
        for(int i = 0 ; i < rowLength; i++) {
            for(int j = 0 ; j < rowLength; j++) {   
                String union = getDistinctString(letters[i], letters[j]);
                // i != j - can't be the same item
                if (rightData(union) && i != j) {
                    if (matchesItemInChosenItemArray( items[i][0]) || matchesItemInChosenItemArray(items[j][0])) {
                        // Skip rest of loop since this word has already been put in chosenItems array
                        continue;
                    }
                    // Fill in twoItem array with IDs of matching item IDs
                    twoItem[0] = items[i][0];
                    twoItem[1] = items[j][0];
                    // Add prices of both items together
                    twoPrice = Integer.valueOf(items[i][priceIndex])
                             + Integer.valueOf(items[j][priceIndex]); 
                    break2 = true;
                    // End loop don't try more options since this is the best deal
                    break;
                }
            }
            if (break2) {  
                // Already found best deal with 2 items so stop looking for more
                break;
            }
        }

        //======================Test if 3 items can make up an item with all Y or abcd ===============
        // For breaking the third loop once a match is found
        boolean break3 = false;
        // Array for three-item array
        String[] threeItem = new String[3];
        int threePrice = 100000000; // Huge price that never will be reached
        // Go though rows of letterArray to compare 3 values
        for (int i = 0 ; i < rowLength; i++) {
            for(int j = 0 ; j < rowLength; j++ ){   
                for(int k = 0 ; k < rowLength; k++ ){   
                    String union = getDistinctString(letters[i], letters[j], letters[k]);
                    // Test if i, j, and k are different values
                    if (rightData(union) && i != j && i !=k && j !=k ) {
                        if (matchesItemInChosenItemArray(items[i][0]) || matchesItemInChosenItemArray(items[j][0]) || matchesItemInChosenItemArray(items[k][0])) {
                            // Skip rest of loop since this word has already been put in chosenItems array
                            continue;
                        }
                        // Fill in threeItem array with IDs of matching item IDs
                        threeItem[0]= items[i][0];
                        threeItem[1]= items[j][0];
                        threeItem[2]= items[k][0];
                        // Add prices of all three items together
                        threePrice = Integer.valueOf( items[i][priceIndex]) 
                                   + Integer.valueOf( items[j][priceIndex]) 
                                   + Integer.valueOf( items[k][priceIndex]);
                        break3 = true;
                        // End loop don't try more options since this is the best deal
                        break;
                    }
                }
                if (break3) {
                    // Break middle loop
                    break;
                }
            }
            if (break3) {
                // Break outer loop
                break;
            }
        }
       
        //=================================== Test if 4 items can make up an item with all Y or abcd ===== 
        // For breaking the fourth loop once a match is found
        boolean break4 = false;
        // Array for four-item array
        String[] fourItem = new String[4];
        int fourPrice = 100000000; // Huge price that never will be reached
        // Go though rows of letterArray to compare 4 values
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < rowLength; j++) {   
                for (int k = 0; k < rowLength; k++) {   
                    for (int t = 0; t < rowLength; t++) {   
                        String union = getDistinctString(letters[i], letters[j], letters[k], letters[t]);
                        // Test if i, j, k, and t are different values
                        if (rightData(union) && i != j && i != k && j != k && t != i && t != j && t != k) {
                            if (matchesItemInChosenItemArray(items[i][0]) || matchesItemInChosenItemArray(items[j][0])||  
                                matchesItemInChosenItemArray(items[k][0]) || matchesItemInChosenItemArray(items[t][0])) {
                                // Skip rest of loop since this word has already been put in chosenItems array
                                continue;
                            }
                            
                            // Fill in fourItem array with IDs of matching item IDs
                            fourItem[0]= items[i][0];
                            fourItem[1]= items[j][0];
                            fourItem[2]= items[k][0];
                            fourItem[3]= items[t][0];
                            // Add prices of all four items together
                            fourPrice = Integer.valueOf( items[i][priceIndex]) 
                                      + Integer.valueOf( items[j][priceIndex]) 
                                      + Integer.valueOf( items[k][priceIndex])
                                      + Integer.valueOf( items[t][priceIndex]);
                            break4 = true;
                            // End loop don't try more options since this is the best deal
                            break;
                        }
                    }
                    if (break4) {
                        // Break LOWER middle loop
                        break;
                    }
                }
                if (break4) {
                    // Break UPPER middle loop
                    break;
                }
            }
            if (break4) {
                // Break outer loop
                break;
            }
        }

        //========================Figure out which price is the best one===================================
        int lowPrice = getLowest(onePrice, twoPrice, threePrice, fourPrice);
        // If no combination was found successfully
        if (onePrice == 100000000 && twoPrice == 100000000 && threePrice == 100000000 && fourPrice == 100000000) {
            // Can't find a match
            matchFound = false;
            // No point in continuing since furniture item combination can't be found
            return;
        } else if (onePrice == lowPrice) { // If a single item was found, add it to chosenItem array
            // Add to chosenItem array without changing first item
            chosenItems = Arrays.copyOf(chosenItems, chosenItems.length + 1);
            chosenItems[chosenItems.length - 1] = oneItem; // Fill last open spot
            chosenPrice += onePrice; // Update price
            matchFound = true;   // A match was found in this iteration
        } else if(twoPrice == lowPrice) {  // If two items were found, add it to chosenItem array
            // Add to chosenItem array without changing first two items
            chosenItems = Arrays.copyOf(chosenItems, chosenItems.length + 2);
            // Update the last 2 new spots of chosenItem array 
            chosenItems[chosenItems.length - 2] = twoItem[0];
            chosenItems[chosenItems.length - 1] = twoItem[1];
            chosenPrice += twoPrice;          // Update price
            matchFound = true; // A match was found in this iteration
        }else if(threePrice == lowPrice ){ // If three items were found, add it to chosenItem array
            // Add to chosenItem array without changing first three items
            chosenItems = Arrays.copyOf(chosenItems, chosenItems.length + 3);
            // Update the last 3 new spots of chosenItem array
            chosenItems[chosenItems.length - 3] = threeItem[0];
            chosenItems[chosenItems.length - 2] = threeItem[1];
            chosenItems[chosenItems.length - 1] = threeItem[2];
            chosenPrice += threePrice;     // Update price
            matchFound = true; // A match was found in this iteration
        }else if(fourPrice == lowPrice ){ // If four items were found, add it to chosenItem array
            // Add to chosenItem array without changing first four items
            chosenItems = Arrays.copyOf(chosenItems, chosenItems.length + 4);
            // Update the last 4 new spots of chosenItem array
            chosenItems[chosenItems.length - 4] = fourItem[0];
            chosenItems[chosenItems.length - 3] = fourItem[1];
            chosenItems[chosenItems.length - 2] = fourItem[2];
            chosenItems[chosenItems.length - 1] = fourItem[3];
            chosenPrice += fourPrice;     // Update price
            matchFound = true; // A match was found in this iteration
        }
        
        // Recursive function call to create another item combo if possible 
        // Else, this is all the items combos it needs and it returns right away
        calulateBestDeal(quan-1);
    }



    //-----------------------------Helper methods---------------------------------------

    /**
     * Determines if item is already being sold and is in chosenItems array.
     * @param word A given string to search for
     * @return true if word is found anywhere in chosenItem array, false otherwise
     */
    private boolean matchesItemInChosenItemArray(String word) {
        for (int i = 0; i < chosenItems.length; i++) {
            if (chosenItems[i].equals(word)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Determines the needed conditions to match depending on the furniture category
     * @param union String value of the "abcd" code for a given item
     * @return true if union is correct for the appropriate category, false otherwise
     */
    public boolean rightData(String union) {
        if (union.equals("abcd") && category.equals("chair")) { // 4 conditions
            return true;
        } else if (union.equals("abc") && (category.equals("desk") || category.equals("filing"))){ // 3 conditions
            return true;
        } else if (union.equals("ab") && category.equals("lamp")) {  // 2 conditions
            return true;
        } else {
            return false; // Nothing matches
        }
    }

    /**
     * Returns the "union" of any number of input Strings.
     * That is, compare all of the strings and return a string that contains the characters 
     * that are unique from each input string. For example, if the args are "ab", "c", and "d",
     * the output is "abcd". Another example is if the args are "abc", "cd", and "bd", output is
     * also "abcd".
     * @param strings any number of strings
     * @return returns the union of those strings
     */
    private String getDistinctString(String... strings) {
        return Stream.of(strings)
            .map(String::chars)
            .flatMap(intStream -> intStream.mapToObj(charCode -> new String(new char[]{(char) charCode})))
            .distinct()
            .sorted()
            .collect(Collectors.joining());
    }

    /**
     * Returns the lowest of four input ints.
     * @param a first integer
     * @param b second integer
     * @param c third integer
     * @param d fourth integer
     * @return lowest of the 4 int values
     */
    private int getLowest(int a, int b, int c, int d) {
        int minAB = Math.min(a, b);
        int minCD = Math.min(c, d);
        return Math.min(minAB, minCD);
    }

    /**
     * Takes the item array and makes an array with elements using 'a', 'b', 'c', 'd', or a distinct combination of them,
     * representing which of the conditions for a given item columns has 'Y' for the condition.
     * For example, if row 0 has 4 conditions of YNYY, the letters array will a corresponding String of "acd" at index 0.
     * Each element of this returned array represents a corresponding row in the items array
     * @return an array of length rowlength representing the Y/N values with abcd as shown above.
     */
    private String[] createLetterArray() {
        // Initialize empty array
        String[] letters = new String[rowLength];
        for (int i = 0 ; i < letters.length; i++) {
            // Initilaize each index to "" as opposed to null
            letters[i] = "";
        }
        // Set up array with the letter combinations
        for (int i = 0; i < items.length; i++) {
            for (int j = 2; j< priceIndex ; j++) {  // Starts at 2 for the columns 2 - 6 for category chair
                if (items[i][j].equals("Y")) {
                    if (j == 2) {
                        letters[i] += "a";
                    } else if (j == 3) {
                        letters[i] += "b";
                    } else if (j == 4) {
                        letters[i] += "c";
                    } else if (j == 5) {
                        letters[i] += "d";
                    }
                }
            }
        }
        return letters;
    }

    /**
     * Sorts items array by price in ascending order.
     */
    private void sortItems(){
        // int colLength= items[0].length;
         //        items [?][priceIndex] == the price of a given ? row
        String[] temp = new String[colLength]; // Temp array with length columns size

        for (int i = 1; i < items.length; i++) {
            for (int j = i; j > 0; j--) {
                if (Integer.valueOf(items[j][priceIndex]) < Integer.valueOf(items[j-1][priceIndex])) {
                    // Switch inner arrays
                    temp = Arrays.copyOf(items[j], colLength);                    
                    items[j] = Arrays.copyOf(items[j - 1], colLength);
                    items[j - 1] = Arrays.copyOf(temp, colLength);
                }
            }
        }
    }


    //--------------------------------Getters -------------------------------------------------

    /**
     * Gets matchFound
     * @return the boolean matchFound
     */
    public boolean getMatchFound() {
        return this.matchFound;
    }

    /**
     * Takes chosenItems array and formates it to a string for output.
     * @return a String containing the ID number of the chosen items to be sold
     */
    public String getChosenItemsString() {
        if (!matchFound){
            throw new NullPointerException("No matches found");
        }
       
        String tmp = "";
        for(int i = 0 ; i < chosenItems.length; i++){ // add an array to a string
            tmp += chosenItems[i];
            if (i != chosenItems.length - 1) {
                tmp += " ";
            }
        }
        return tmp;
    }

    /**
     * Gets chosenItems.
     * Throws NullPointerException if array legnth == 0.
     * @return the String array of chosenItems
     */
    public String[] getChosenItems() {
        if (!matchFound) {
            throw new NullPointerException("No matches found");
        }
        return this.chosenItems;
    }

    /**
     * 
     * @return fortmated version of items to be purchased
     */
    public String getFormatedChosenItems(){
        String tmp = "";
        // Add an array to string tmp
        for (int i = 0; i<chosenItems.length ; i++) {
            tmp += chosenItems[i];
            if (i != chosenItems.length - 1) {
                tmp+= ", ";
            }
            if (i == chosenItems.length - 2) {
                tmp += "and ";
            }
        }
        return tmp;
    }

    /**
     * Gets the final combined price of items to be sold as a string.
     * @return chosenPrice 
     */
    public String getPrice(){
        return String.valueOf(this.chosenPrice);
    }
    
    //---------------------Hardcode databse----------------------------------
    /**
     * Returns message to be printed to console if matchFound is false
     * @param categ category (e.g. chair)
     * @param typ type (e.g. mesh)
     * @return an error message String with suppliers for that category
     */
    public String errorMessage(String categ, String typ) {
        String[][] manufacutures = {
            { "001"    , "Academic Desks"       , "236-145-2542" , "BC"},
            { "002"    , "Office Furnishings"   , "587-890-4387" , "AB"},
            { "003"    , "Chairs R Us"          , "705-667-9481" , "ON"},
            { "004"    , "Furniture Goods"      , "306-512-5508" , "SK"},
            { "005"    , "Fine Office Supplies" , "403-980-9876" , "AB"}    
        };
        
        // Gets the number of suppliers that sell this type of furniture item
        String[] arg = hardcodeData(categ, typ);
        
        String message = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are ";
        
        for (int i = 0; i < arg.length; i++) {
            for (int j =0; j<manufacutures.length; j++) {
                if (arg[i].equals(manufacutures[j][0])) {
                    message += manufacutures[j][1];
                }
            }
            
            if( i < arg.length-1){
                message += ", ";
            }
            
            if( i == arg.length-2){
                message += "and ";
            }
            if (i == arg.length-1) {
                message += "." ;
            }
        }
        // Return an appropriate error message to be printed
        return message; 
    }


    /**
     * Gets IDs of manufactures that match the category
     * @param categ furniture category/item
     * @param typ type of the furniture
     * @return String array of manufacter IDs
     */
    private String[] hardcodeData(String categ, String typ) {

        if (categ.equals("chair")) {
            String[] a = {"002","003", "004", "005"};
            return a;
        } else if (categ.equals("desk")) {
            String[] a = {"002", "003", "004", "005"};
            return a;
        } else if (categ.equals("filing")) {
            // All types are the same for filing
            String[] a = {"002", "005"};
            return a;
        } else if (categ.equals("lamp")) {
            String[] a = {"002", "004", "005"};
            return a;
        } else {
            // Nothing found based on data input, so return array with all manufactures
            String[] b = {"001", "002", "003", "004", "005"};
            return b;
        }    
    }
}
