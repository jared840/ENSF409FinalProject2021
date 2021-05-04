# ENSF 409 Winter 2021 Final Project - Group 23
* @author         Tony Vo         
* @author         Chace Nielson   
* @author         Jared Lundy    
* @author         Jordan Lundy    
* @version        3.3

Presentation video link: https://youtu.be/g9X8Fx2m_88

The purpose of the program is to read an inventory database and select furniture items of a particular type and quantity at the best available price.

In order to run program you will require:
* mysql-connector-java-8.0.23.jar in order to access database with MySQL
* Runner.java
* ReadInput.java
* ReadDatabase.java
* Calculator.java
* OutputFile.java

The SQL database uses username: "scm" and password: "ensf409".

Move the files to a directory and navigate to it with the command line.

To compile, run the command:
`javac -cp .;mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/Runner.java`

To run the program, run the command:
`java -cp .;mysql-connector-java-8.0.23.jar;. edu.ucalgary.ensf409.Runner`

The program will guide you through the steps of selecting an item, its type, and the quantity desired.

The output will be a listing of the item's ID and the best price that can 
be offered for them. An output file will be created in the directory listing
the full details of the transaction.

If the transaction cannot occur due to a lack of inventory items in stock, an error message will be outputted to the console along with a list of manufacturers that sell the specific furniture item. 
