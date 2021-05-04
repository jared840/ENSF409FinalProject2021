/**  
* ReadDatabase.java
* Class responsible for reading the inventory database.
* Also for deleting items from database when items have been selected for the transaction.
* @author         Jared Lundy
* @author         Jordan Lundy
* @author         Tony Vo
* @author         Chace Nielson
* @version        2.3
* @since          1.0
*/
package edu.ucalgary.ensf409;


import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReadDatabase {
    private String category;
    private String type;
    private int number;
    private Connection dbConnect;
    private ResultSet results;
    private String[][] resultArray;
    private ReadInput inputRead;
    private boolean dbConnected;                    
    private boolean dbClosed;

    /**
     * ReadDatabase constructor that brings in a ReadInput object to allow for direct instantiation with command line arguments
     * @param readInto ReadInput object; contains a furniture item, furniture type, and number of items ordered
     */
    public ReadDatabase(ReadInput readInto) {
        // Setting private variables category, type, and number
        this.category = readInto.getFurnitureItem();
        this.type = readInto.getFurnitureType();
        this.number = readInto.getFurnitureQuantity();
        
        // Connect to database
        this.createConnection();
        
        // Set private ResultArray by reading into database
        this.setResultArray();
        
    }

    /**
     * ReadDatabase default constructor that sets the private ReadInput object and calls for ReadInput's command line input
     * Also, sets the private variables category, type, and number to the corresponding command line arguments.
     * Finally, creates a mySQL database connection and calls setResultArray to retrieve all items in the database that match the 3 command line inputs.
     */
    public ReadDatabase() {
        // Instantiating a ReadInput object to retrieve cmd line input
        inputRead = new ReadInput();
        
        // Setting private members
        this.category = inputRead.getFurnitureItem();
        this.type = inputRead.getFurnitureType();
        this.number = inputRead.getFurnitureQuantity();
        
        // Connect to database and retrieve available items from database
        this.createConnection();
        this.setResultArray();
    } 

    /**
     * Constructor for ReadDatabase. 
     * Sets private variables category, type, and number.
     * @param category Furniture category
     * @param type Type specifier of furniture category
     * @param num number of items requested
     */
    public ReadDatabase(String category, String type, int num) {
        // Sets the private members
        this.category = category;
        this.type = type;
        this.number = num;
    }
    
    /**
     * Retrieves the private type of furniture
     * @return Type of furniture
     */
    public String getType() {
        return this.type;
    }

   /**
    * Retrieves the category of furniture requested
    * @return Furniture category 
    */ 
   public String getCategory() {
        return this.category;
    }

    /**
     * Retrieves the quantity of furniture selected (for private variable number)
     * @return Number of furniture items requested
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Retrieves the amount of columns for the requested furniture type
     * @return number of columns for that furniture type
     */
    public int getSize() {
        // Returning size of column depending on matching category
        if (this.category.equals("chair")) {
            return 8;
        }
        else if (this.category.equals("desk")) {
            return 7;
        }
        else if (this.category.equals("filing")) {
            return 7;
        }
        else if (this.category.equals("lamp")) {
            return 6;
        }
        else {
            return 0;
        }
    }

    /**
     * Retrieves the private dbConnected variable that indicates whether a connection to mySQL database was successful.
     * @return boolean dbConnected private
     */
    public boolean getDBConnected() {
        return this.dbConnected;
    }

    /**
     * Retrieves private boolean that indicates whether closing all sql resources was successful upon calling the close() method
     * @return boolean dbClosed private member
     */
    public boolean getDBClosed() {
        return this.dbClosed;
    }

    /**
     * Initializes a connection to the mySQL database using the URL, username, and password
     */
    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "scm", "ensf409");
            this.dbConnected = true;
        } catch(SQLException e) {
            this.dbConnected = false;
            e.printStackTrace();
        }
    }

    /**
     * Counts the number of furniture types within the selected category table.
     * @return Number of specific type in the category table 
     */
    public int counter(){
        int typeCounter = 0;
        // Creating statement for selecting rows from database
        try {
            Statement mySt = dbConnect.createStatement();
            results = mySt.executeQuery("SELECT * FROM " + category);
            // Go through database and increment typeCounter for every type match
            while(results.next()) {
                String typeString = results.getString("Type");
                
                if (typeString.equalsIgnoreCase(getType())) {
                    typeCounter++;
                }
            }
            mySt.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return typeCounter;
    }
    
    /**
     * Updates the database to delete any of the chosen furnitures from the database
     * @param ID String array of IDs to delete from database
     */
    public void deleteSoldItems(String[] ID)
    {
        int tracker = 0;
      
        while (tracker < ID.length) {
            try {
                // Create delete preparedStatement
                String query = "DELETE FROM " + category + " WHERE ID = ?";
                PreparedStatement std = dbConnect.prepareStatement(query);
    
                std.setString(1, ID[tracker]);
    
                int rowCount = std.executeUpdate();
    
                std.close();
            } catch( SQLException ex) {
                ex.printStackTrace();
            }
            tracker++;
        }
    }

    /**
     * Retrieves each line of data for each type within the selected category table. 
     * Then sets the resultArray to contain each line of data for each instance of furniture type found.
     * @return Multidimensional array that contains each line of data for each found type
     */
    public void setResultArray() {
        resultArray = new String[this.counter()+1][this.getSize()];
     
        // Setting first row of array depending on matching category
        if (getCategory().equals("chair")) {
            resultArray[0][0] = "ID";
            resultArray[0][1] = "Type";
            resultArray[0][2] = "Legs";
            resultArray[0][3] = "Arms";
            resultArray[0][4] = "Seat";
            resultArray[0][5] = "Cushion";
            resultArray[0][6] = "Price";
            resultArray[0][7] = "manuID";
        }

        if (getCategory().equals("desk")) {
            resultArray[0][0] = "ID";
            resultArray[0][1] = "Type";
            resultArray[0][2] = "Legs";
            resultArray[0][3] = "Top";
            resultArray[0][4] = "Drawer";
            resultArray[0][5] = "Price";
            resultArray[0][6] = "manuID";
        }

        if (getCategory().equals("lamp")) {
            resultArray[0][0] = "ID";
            resultArray[0][1] = "Type";
            resultArray[0][2] = "Base";
            resultArray[0][3] = "Bulb";
            resultArray[0][4] = "Price";
            resultArray[0][5] = "manuID";
        }

        if(getCategory().equals("filing")) {
            resultArray[0][0] = "ID";
            resultArray[0][1] = "Type";
            resultArray[0][2] = "Rails";
            resultArray[0][3] = "Drawers";
            resultArray[0][4] = "Cabinet";
            resultArray[0][5] = "Price";
            resultArray[0][6] = "manuID";
        }

        // Setting all other rows of the array to what is stored in the database using a statement
        try {
            Statement std = dbConnect.createStatement();
            results = std.executeQuery("SELECT * FROM " + category);
            int i = 1;
            String test = getCategory();
           
            while (results.next())
            {
                if (getType().equalsIgnoreCase(results.getString("Type")))
                {
                    if (getCategory().equals("chair")) {
                        resultArray[i][0] = results.getString("ID");
                        resultArray[i][1]= results.getString("Type");
                        resultArray[i][2] = results.getString("Legs");
                        resultArray[i][3] = results.getString("Arms");
                        resultArray[i][4] = results.getString("Seat");
                        resultArray[i][5] = results.getString("Cushion");
                        resultArray[i][6] = results.getString("Price");
                        resultArray[i][7] = results.getString("manuID");
                        i++;
                    }
                    else if(getCategory().equals("desk")) {
                        resultArray[i][0] = results.getString("ID");
                        resultArray[i][1]= results.getString("Type");
                        resultArray[i][2] = results.getString("Legs");
                        resultArray[i][3] = results.getString("Top");
                        resultArray[i][4] = results.getString("Drawer");
                        resultArray[i][5] = results.getString("Price");
                        resultArray[i][6] = results.getString("ManuID");
                        i++;
                    }
                    else if(getCategory().equals("filing")) {
                        resultArray[i][0] = results.getString("ID");
                        resultArray[i][1]= results.getString("Type");
                        resultArray[i][2] = results.getString("Rails");
                        resultArray[i][3] = results.getString("Drawers");
                        resultArray[i][4] = results.getString("Cabinet");
                        resultArray[i][5] = results.getString("Price");
                        resultArray[i][6] = results.getString("ManuID");
                        i++;
                    }
                    else if (getCategory().equals("lamp")) {
                        resultArray[i][0] = results.getString("ID");
                        resultArray[i][1]= results.getString("Type");
                        resultArray[i][2] = results.getString("Base");
                        resultArray[i][3] = results.getString("Bulb");
                        resultArray[i][4] = results.getString("Price");
                        resultArray[i][5] = results.getString("ManuID");
                        i++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Close method to close all SQL resources used
     */
    public void close() {
        // Closing the database connection and the resultSet
        try {
            results.close();
            dbConnect.close();
            this.dbClosed = true;
        } catch (SQLException e) {
            this.dbClosed = false;
            e.printStackTrace();
        }
    }

    /**
     * Close method to close only the database.
     * Only used for testing.
     */
    public void closeConnect() {
        try {
            dbConnect.close();
            this.dbClosed = true;
        } catch (SQLException e) {
            this.dbClosed = false;
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves the resultArray storing all available items.
     * @return String multidimensional array holding all possible items
     */
    public String[][] getResultArray() {
        return this.resultArray;
    }
}
