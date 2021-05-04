package edu.ucalgary.ensf409;
/**
  *  CalcualtorTest
  * fucntions for testing appropriate best deals or erros 
  * these test are to create basic output from calculator 
  * the actual inventory does not have any affect on these tests as they use hardcoded data
  * therefore the results may not be the same resutls the inventory would give
  * this is becuause they are based on data not found in the inventory
  *
  * the data is provided in each test as a 2d array representing the data that would be recived from ReadDatabase
  *
  * @author         Tony Vo
  * @author         Chace Nielson
  * @author         Jared Lundy
  * @author         Jordan Lundy
  * @version        3.3
  * @since          3.1
*/

import org.junit.*;
import static org.junit.Assert.*;

public class CalculatorTest {
    
    public CalculatorTest() { // default ctor
    }


     /**
    Case: calculate best deal with given data
    this test is to match with the output of the getString method
     */
    @Test
    
    public void calculatorGetStringsTest1() {
        System.out.println("calculatorGetStringsTest1");
        
        String [][] dataSet = {
            {"ID", "type", "Legs", "Arms","Seat", " Cushion", "Price", "ManuID"},
            {"C0942",	"Mesh",	"Y",	"N",	"Y",	"Y",	"100",	"005"},
            {"C6748",	"Mesh",	"Y",	"N",	"N",	"N",	"75",	"003"}, 
            {"C8138",	"Mesh",	"N",	"N",	"Y",	"N",	"75",	"005"},
            {"C9890",	"Mesh",	"N",	"Y",	"N",	"Y",	"50",	"003"} 
            };

        String category = "chair";
        String quantity = "1";

        try {
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();
            String expResult = "C9890 C0942";
    
            String result = a.getChosenItemsString();
            assertEquals("ChosenItem String was incorrect: ", expResult, result);            
        } catch (Exception e) {
            System.out.println("error");        
        }
        
    }

       /**
    Case: calculate best deal with given data
    this test is to match with the output of the getString method
     */
    @Test
  
    public void calculatorGetStringsTest2() {
        System.out.println("calculatorGetStringsTest2");
        
        String [][] dataSet = {
                {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
                {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
                {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
                {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
                {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
            };
      
        String category = "lamp";
        String quantity = "1";

        try{
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();

            String expResult = "L928";
        
            String result = a.getChosenItemsString();
            assertEquals("ChosenItem String was incorrect: ", expResult, result);        
        }catch(Exception e){
            System.out.println("error");        

        }
    }

   
     /**
    Case: calculate best deal with given data
    this test is to match with the output of the getString method
     */
    @Test
    
    public void calculatorGetStringsTest3() {
        System.out.println("calculatorGetStringsTest3");

            String [][] dataSet = {
                        {"ID" , "Type" , "Rails" , "Drawers"    ,"Cabinet"   ,  "Price"  , "ManuID"    },
                        {"F012" , "Large" , "N" , "Y"    ,"N"   ,  "75"  , "005"    },
                        {"F015" , "Large" , "Y" , "N"    ,"N"   ,  "75"  , "004"    },
                        {"F003" , "Large" , "N" , "N"    ,"Y"   ,  "150" , "002"    },
                        {"F010" , "Large" , "Y" , "N"    ,"Y"   ,  "225" , "002"    },
                        {"F011" , "Large" , "N" , "Y"    ,"Y"   ,  "225" , "005"    }    
                    };

        String category = "filing";
        String quantity = "1";

        try {
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();
    
            String expResult = "F012 F010";
    

            String result = a.getChosenItemsString();
            assertEquals("ChosenItem String was incorrect: ", expResult, result);
            
        } catch (Exception e) {
            System.out.println(e + "  error");        
        }
    }
    


   /**
    Case: calculate best deal with given data and get price
    this test is to match with the output of the getprice method
     */
    @Test
    
    public void calculatorGetPriceTest1() {
        System.out.println("calculatorGetPriceTest1");
        
        String [][] dataSet = {
            {"ID", "type", "Legs", "Arms","Seat", " Cushion", "Price", "ManuID"},
            {"C0942",	"Mesh",	"Y",	"N",	"Y",	"Y",	"100",	"005"},
            {"C6748",	"Mesh",	"Y",	"N",	"N",	"N",	"75",	"003"}, 
            {"C8138",	"Mesh",	"N",	"N",	"Y",	"N",	"75",	"004"},
            {"C9890",	"Mesh",	"N",	"Y",	"N",	"Y",	"50",	"003"} 
            };



        String category = "chair";
        String quantity = "1";

        try {
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();

            String expResult = "150";
    
            String result = a.getPrice();
            assertEquals("Price was incorrect: ", expResult, result);
            
        } catch (Exception e) {
            System.out.println("error");        
        }

    }

    /**
    Case: calculate best deal with given data and get price
    this test is to match with the output of the getprice method
     */
    @Test
    
    public void calculatorGetPriceTest2() {
        System.out.println("calculatorGetPriceTest2");
        
        String [][] dataSet = {
                {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
                {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
                {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
                {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
                {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
            };
      
        String category = "lamp";
        String quantity = "1";
        try {
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();

            String expResult = "10";
    
            String result = a.getPrice();
            assertEquals("Price was incorrect: ", expResult, result);
            
        } catch (Exception e) {
            System.out.println("error");        
        }

    }


  
    /**
    Case: calculate best deal with given data and get price
    this test is to match with the output of the getprice method
     */
    @Test
    
    public void calculatorGetPriceTest3() {
        System.out.println("calculatorGetPriceTest3");

            String [][] dataSet = {
                    {"ID" , "Type" , "Rails" , "Drawers"    ,"Cabinet"   ,  "Price"  , "ManuID"    },
                    {"F012" , "Large" , "N" , "Y"    ,"N"   ,  "75"  , "005"    },
                    {"F015" , "Large" , "Y" , "N"    ,"N"   ,  "75"  , "004"    },
                    {"F003" , "Large" , "N" , "N"    ,"Y"   ,  "150" , "002"    },
                    {"F010" , "Large" , "Y" , "N"    ,"Y"   ,  "225" , "002"    },
                    {"F011" , "Large" , "N" , "Y"    ,"Y"   ,  "225" , "005"    }    
            };

        String category = "filing";
        String quantity = "1";
        try{
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();

            String expResult = "300";

    
            String result = a.getPrice();
            assertEquals("Price was incorrect: ", expResult, result);

        }catch (Exception e) {
            System.out.println(e +" error calculatorGetPriceTest3");        
        }
    }

     /**
    Case: get the error message to be outputted when no price can be found
    this test is to match with the excpeted error message
    this test doesn't care if a deal can be made or not from the given data
    if is to see if appropriate manufacures are outputted in error message
     */
    @Test
    
    public void calculatorGetErrorMessage1() {
        System.out.println("calculatorGetErrorMessage1");

            String [][] dataSet = {
                        {"ID" , "Type" , "Rails" , "Drawers"    ,"Cabinet"   ,  "Price"  , "ManuID"    },
                        {"F012" , "Large" , "N" , "Y"    ,"N"   ,  "75"  , "005"    },
                        {"F015" , "Large" , "Y" , "N"    ,"N"   ,  "75"  , "004"    },
                        {"F003" , "Large" , "N" , "N"    ,"Y"   ,  "150" , "002"    },
                        {"F010" , "Large" , "Y" , "N"    ,"Y"   ,  "225" , "002"    },
                        {"F011" , "Large" , "N" , "Y"    ,"Y"   ,  "225" , "005"    }    
            };

        String category = "filing";
        String quantity = "1";
        try{
            Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
            a.fillChosenItemsArray();

            String expResult = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, and Fine Office Supplies.";
            String result = a.errorMessage(category, "Large");
            assertEquals("error Message is incorrect: ", expResult, result);

        }catch (Exception e) {
            System.out.println("error");        
        }
    }

    /**
    Case: get the error message to be outputted when no price can be found
    this test is to match with the excpeted error message
    this test doesn't care if a deal can be made or not from the given data
    if is to see if appropriate manufacures are outputted in error message
     */
    @Test
        
    public void calculatorGetErrorMessage2() {
        System.out.println("calculatorGetErrorMessage2");

        String [][] dataSet = {
            {"ID", "type", "Legs",    "Arms" ,"Price", "ManuID"},
            {"L928",	"Study",	"Y",	"Y",	"10",	"005"},
            {"L223",	"Study",	"N",	"Y"	,   "2",	"003"}, 
            {"L982",	"Study",	"Y",	"N"	,   "8",	"005"},
            {"L980",	"Study",	"N",	"Y",	"2",	"004"} 
        };
  
    String category = "lamp";
    String quantity = "1";
    try {
        Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
        a.fillChosenItemsArray();

            String expResult = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Furniture Goods, and Fine Office Supplies.";
            String result = a.errorMessage(category, "Study");
            assertEquals("error Message is incorrect: ", expResult, result);

        }catch (Exception e) {
            System.out.println("error");        
        }
    }
    

    /**
    Case: get the error message to be outputted when no price can be found
    this test is to match with the excpeted error message
    this test doesn't care if a deal can be made or not from the given data
    if is to see if appropriate manufacures are outputted in error message
     */
    @Test
   
    public void calculatorGetErrorMessage3() {
        System.out.println("calculatorGetErrorMessage3");

        String [][] dataSet = {
            {"ID", "type", "Legs", "Arms","Seat", " Cushion", "Price", "ManuID"},
            {"C0942",	"Mesh",	"Y",	"N",	"Y",	"Y",	"100",	"005"},
            {"C6748",	"Mesh",	"Y",	"N",	"N",	"N",	"75",	"003"}, 
            {"C8138",	"Mesh",	"N",	"N",	"Y",	"N",	"75",	"005"},
            {"C9890",	"Mesh",	"N",	"Y",	"N",	"Y",	"50",	"003"} 
            };
  
    String category = "chair";
    String quantity = "1";
    try {
        Calculator a = new Calculator(dataSet, category, quantity);//pass 2d String array with database information to the        
        a.fillChosenItemsArray();

            String expResult = "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, and Fine Office Supplies.";
            String result = a.errorMessage(category, "Large");
            assertEquals("error Message is incorrect: ", expResult, result);

        }catch (Exception e) {
            System.out.println("error");        
        }
    }


} // end calculator testclass

        
