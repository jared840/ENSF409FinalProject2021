/*
* ENSF 409 Final Project Winter 2021
* @author         Tony Vo         30091585
* @author         Chace Nielson   30045674
* @author         Jared Lundy     30086687
* @author         Jordan Lundy    30086686
* @version        3.3
*
* Presentation video link: https://youtu.be/g9X8Fx2m_88
*
* The purpose of the program is to read a Supply Chain Management inventory database and 
* select furniture items of a particular type and quantity at the best available price.
*
* In order to run program you will require:
*    mysql-connector-java-8.0.23.jar in order to access database with MySQL
*    Runner.java
*    ReadInput.java
*    ReadDatabase.java
*    Calculator.java
*    OutputFile.java
*
* The SQL database uses username: "scm" and password: "ensf409".
*
* Move the files to a directory and navigate to it with the command line.
*
* To compile, run the command:
* `javac -cp .;mysql-connector-java-8.0.23.jar;. Runner.java`
*
* To run the program, run the command:
* `java -cp .;mysql-connector-java-8.0.23.jar;. Runner`
*
* The program will guide you through thesteps of selecting an item, its type, and the quantity desired.
*
* The output will be a listing of the item's ID and the best price that can 
* be offered for them. An output file will be created in the directory listing
* the full details of the transaction.
*
* If the transaction cannot occur due to a lack of inventory items in stock, an error message will be 
* outputted to the console along with a list of manufacturers that sell the specific furniture item. 
*  
* Runner.java 
* Contains the program driver.
* Calls to other classes to:
*     Read and formulate the user input
*     Access the database
*     Calculate the best deal
*     Update the database
*     Create an output file the details of the transaction
*/

package edu.ucalgary.ensf409;

import java.util.Arrays;
import java.util.ArrayList;

public class Runner {
    private ReadInput userInput;
    private String item;
    private String type;
    private int quantity;
    private ReadDatabase sqlInfo;
    private Calculator bestDeal;
    private String outputFileName = "orderform.txt";
    private OutputFile theFile;

    public void runSCM() {
        System.out.println("Welcome to the University of Calgary Supply Chain Management Ordering System!");
        System.out.println("==============================================================");
        System.out.println("Furniture item: Furniture types");
        System.out.println("Chair: Ergonomic, Executive, Kneeling, Mesh, Task");
        System.out.println("Desk: Adjustable, Standing, Traditional");
        System.out.println("Filing: Small, Medium, Large");
        System.out.println("Lamp: Desk, Swing Arm, Study");
        System.out.println("==============================================================");
        
        // Gets user input for desired furniture type
        userInput = new ReadInput(); 
        item = userInput.getFurnitureItem();
        quantity = userInput.getFurnitureQuantity(); 
        type = userInput.getFurnitureType();
        
        // Database connector
        sqlInfo = new ReadDatabase(userInput); 
        
        try {
            bestDeal = new Calculator(sqlInfo.getResultArray(), item, String.valueOf(quantity));
            // Set bestDeal to correspond to the best of deals possible
            bestDeal.fillChosenItemsArray();
           
            if (bestDeal.getMatchFound()) {
                System.out.println("Purchase " +  bestDeal.getFormatedChosenItems() + " for $" + bestDeal.getPrice() + ".");
            } else {
                System.out.println(bestDeal.errorMessage(item, type)); // 
            }
            
            // Prepare the output file for the combination of deals
            theFile = new OutputFile(outputFileName, userInput.getRequest(), bestDeal.getChosenItems(), bestDeal.getPrice(), "", "");
            
            // Update database with items taken out of system
            sqlInfo.deleteSoldItems(bestDeal.getChosenItems());
            
            // Create the output file
            theFile.makeFile(); 
                
        } catch (Exception e) {
            System.exit(1);
        }
        
        // Close connection to the database
        sqlInfo.close();
    }
    
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.runSCM();
    }
}

