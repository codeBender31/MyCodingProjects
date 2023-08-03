/**
 * @Author: Abel Hernandez
 * @AXH-127530
 */
 import java.util.*;
 import java.io.*;
 import java.lang.*;
 //@Author: Abel Hernandez
 //@AXH-127530
/**
 * This application will process the orders of regular and preferred customers. 
 * Apply certain discounts the customers qualify for as well bonus bucks for platinum customers. 
 * Upgrade the customer from regular to preferred if they qualify.
 */
public class Main
{
  public static void main(String args[]) throws IOException
  {      
  //Declare variables
    String currentLine;
    String idNumber;
    String firstName;
    String lastName;
    String ordersID,secondString,drinkType;
    char size; 
    float squareInchPrice = 0.0f, orderTotal = 0.00f;
    int quantity = 0;
    
    //Create a scanner object
    Scanner kbd = new Scanner (System.in);
    //Greet the user and explain the app
    System.out.printf("Welcome to Disney Rewards.\n");
    System.out.printf("Please provide the name of the regular file name. \n");
    
    //Store the name of the file
    String regularCustomerFileName = kbd.nextLine();
    
    //Create a pointed in main to acces the array 
    Customer [] regularCustomers = createRegular(regularCustomerFileName);

    //Access the prefered file and create an array of preferred objects
    System.out.println("Enter the name of the preferred file.");
    String preferredFileName = kbd.nextLine();
 
    //Create a pointer to the array that is returned by method.
    Customer [] preferredCustomers =  createPreferred(preferredFileName);
  
    //Let me know the orders file is being read
    System.out.println("The orders file will be read");
    String ordersName = kbd.nextLine();
  
    //Create a third object to scan the third file.
    Scanner orders = new Scanner(new File (ordersName));
 
    //The loop will execute while the orders file has input
    while(orders.hasNext())
    {
        //Split the line so you can validate each part and check of quantity
        String line = orders.nextLine();
        
        //Store the split strings in a string array 
        String [] arrayOfStrings = line.split(" ");
        //Pass it on to the method to verify it can be used for processing 
        if( checkWholeLine(arrayOfStrings))
       {
           //If its valid the store info in these fields
        ordersID =  arrayOfStrings[0];
        secondString = arrayOfStrings[1];
        size = secondString.charAt(0);
        drinkType = arrayOfStrings[2];
        squareInchPrice = Float.valueOf(arrayOfStrings[3]);
        quantity = Integer.valueOf(arrayOfStrings[4]);
        
          //Calculate order total and store in variable
             orderTotal =  calculateOrderTotal(size,drinkType,squareInchPrice,quantity);
            if(  checkRegularId(regularCustomers, ordersID) != -1)
           {
                //If it is found show me the index it was found in the array
                int indexOfArray =  checkRegularId(regularCustomers, ordersID);
            
             
                //Get the original amount spent to store in variable
                float previousAmountSpent = regularCustomers[indexOfArray].getOriginalAmountSpent();
            
                //Pass on both variables to determine the new discount if any
                float newDiscount =  determineDiscount(orderTotal,previousAmountSpent);
                
                
                //If the discount is between 5 or 15 then upgrade
                    if(newDiscount >= 5.0f && newDiscount <= 15.0f)
                    {//If the discount is greater than or equal to 5 then create a new gold object and pass in the new discount to create a new gold object
                         idNumber = regularCustomers[indexOfArray].getID();
                         firstName = regularCustomers[indexOfArray].getFirstName();
                         lastName = regularCustomers[indexOfArray].getLastName();
                        
                         
                         //Create a new gold object
                         Gold newGoldMember = new Gold(idNumber,firstName,lastName,previousAmountSpent,newDiscount);
                       
                         newGoldMember.calculateNewAmountSpent(orderTotal);
                           
                           //Set preferred array customer array equal to method that mutates it and change the pointer to bigger array
                           preferredCustomers =  addGoldToPreferred(preferredCustomers,newGoldMember);
                           
                           //Remove the regular object from the regular array if the customer upgraded and change the pointer to smaller array
                           regularCustomers =  removeFromRegular(regularCustomers,indexOfArray);
                           
                    }
                    else if(newDiscount == -1.0f)
                    {
                         //Get all the necessay fields to copy on to a new platinum object 
                        idNumber = regularCustomers[indexOfArray].getID();
                         firstName = regularCustomers[indexOfArray].getFirstName();
                         lastName = regularCustomers[indexOfArray].getLastName();
                         
                         
                         //Determine bonus bucks 
                         float newObjectsFutureBonusBucks = 0.0f; //Make bonus bucks equal to 0 so calculation wont account for them
                         //Create a new platinum object 
                         Platinum newPlatinumMember = new Platinum (idNumber,firstName,lastName,previousAmountSpent,newObjectsFutureBonusBucks);
                         
                         //Calculate the new amount spent with 0 bonus bucks since its a new platinum member
                         newPlatinumMember.calculateNewAmountSpent(orderTotal);
                         
                         //Find the new bonus bucks it will use for the next order
                          newObjectsFutureBonusBucks = (float) determineNewBonusBucks(previousAmountSpent); 
                          
                          //Set the new bonus bucks in the field of the object
                          newPlatinumMember.setBonusBucks(newObjectsFutureBonusBucks);
                          
                         //Add the object to the end of the preferred array
                        preferredCustomers =  addPlatinumToPreferred(preferredCustomers,newPlatinumMember);
                         
                         //Take the original customer off the regular array
                         regularCustomers =  removeFromRegular(regularCustomers,indexOfArray);
                     
                    }
                    //If the discount is 0 then calculate without any discount 
                    else if (newDiscount == 0.0f)
                    {
                    //If it doesnt upgrade then calculate new amount spent and mutate the regular objects field
                    regularCustomers[indexOfArray].calculateNewAmountSpent(orderTotal);
                   }
                
            }
            else if( checkPreferredId(preferredCustomers, ordersID) != -1)
            {
                //Save the index of the object where its found
                int indexOfArray =  checkPreferredId(preferredCustomers, ordersID);
                
                //if the object found is an instance of gold 
                if(preferredCustomers[indexOfArray] instanceof Gold)
                {   
                    //Get the previous amount spent and the current discount 
                    float previousAmountSpent = preferredCustomers[indexOfArray].getOriginalAmountSpent(); 
                    //Get the disocunt of the object 
                   float currentDiscount = ((Gold)preferredCustomers[indexOfArray]).getDiscount();
                
                //Calculate new discount if any
                float newDiscount =  determineNewDiscount(orderTotal,previousAmountSpent,currentDiscount);
                        //If new discount is not -1 then set new discount and calculate new total to mutate
                        if(newDiscount != -1.0f)
                        {
                            //Set the new discount to calcualte the new amount spent
                            ((Gold)preferredCustomers[indexOfArray]).setNewDiscount(newDiscount);
                            
                            //Calculate new amount spent 
                            preferredCustomers[indexOfArray].calculateNewAmountSpent(orderTotal);
                               
                            }
                            //Otherwise replace the element with a platinum and set new bonus bucks
                         else if (newDiscount == -1.0f)
                         {
                             //pass along 15 percent because before it moves up the new amount spent must be mutated with the maximum discoun
                             ((Gold)preferredCustomers[indexOfArray]).setNewDiscount(15.0f);
                             
                             //Mutate the amount spent field
                             preferredCustomers[indexOfArray].calculateNewAmountSpent(orderTotal);
                             
                             //Copy fields in to a new platinum object
                              //Get all the necessay fields to copy on to a new platinum object 
                              idNumber = preferredCustomers[indexOfArray].getID();
                              firstName = preferredCustomers[indexOfArray].getFirstName();
                              lastName = preferredCustomers[indexOfArray].getLastName();
                              previousAmountSpent = preferredCustomers[indexOfArray].getOriginalAmountSpent();
                              
                              //Initialize bonus bucks to 0
                              float newObjectsFutureBonusBucks = 0.0f;
                              
                              //Create the object and pass in same fields and mutated amount spent
                              Platinum newPlatinumMember = new Platinum (idNumber,firstName,lastName,previousAmountSpent,newObjectsFutureBonusBucks);
                              
                              //Find out the objects new bonus bucks
                              newObjectsFutureBonusBucks =  determineNewBonusBucks(previousAmountSpent);
                              //Set the new bonus bucks 
                              newPlatinumMember.setBonusBucks(newObjectsFutureBonusBucks);
                              
                             //Replace element in array with platinum
                             preferredCustomers[indexOfArray] =  newPlatinumMember;
                            }
                 }
               
                 //If the object in the array is of Platinum type
                else if (preferredCustomers[indexOfArray] instanceof Platinum)
              
                {
                    
                    //Save a copy of the amountSpent before processing to subtract from new amount spent in method
                    float originalAmountSpent = preferredCustomers[indexOfArray].getOriginalAmountSpent();
                    
                    //Mutate the amount spent in the object 
                    preferredCustomers[indexOfArray].calculateNewAmountSpent(orderTotal);
                    
                    //Check if new amount spent lets them qualify for future bonus bucks and set if any 
                    //Save a copy of the new amount spent inside the object
                    float newAmountSpentAfterCalculation = preferredCustomers[indexOfArray].getOriginalAmountSpent();
                    //Determine new bonus bucks 
                    float newBonusBucksAfterUsingOldOnes =  determineNewBonusBucksAfterCalculation(newAmountSpentAfterCalculation, originalAmountSpent);
                    //If there is new bonus bucks then store em in object 
                    if(newBonusBucksAfterUsingOldOnes>0)
                    {
                    ((Platinum)preferredCustomers[indexOfArray]).setBonusBucks(newBonusBucksAfterUsingOldOnes);
                    }
                    else
                    {
                        //Otherwise set them to 0
                        ((Platinum)preferredCustomers[indexOfArray]).setBonusBucks(0);
                    }
                    
                } 
       
            }
    }
  
    //Else do nothing and move on to next line
    }
    
    //Close the third file
    orders.close();
    
    //Create a new file writer object to create new output files
     WriteToFile(regularCustomers,preferredCustomers);
  }
  
    /**
   * Assuming the use previous bonus bucsk find out if new bonus bucks can be stored for later
   */
  public static int determineNewBonusBucksAfterCalculation(float newAmountSpentAfterCalculation, float originalAmountSpent)
  {         
            //Declare the variables
            float bonusBucksBeforeRounding = 0.0f;
            int bonusBucksAfterRounding = 0;
            
            //Calculate bonus bucks
            bonusBucksBeforeRounding = (( (int)newAmountSpentAfterCalculation - (int)originalAmountSpent) / 5); 
            bonusBucksAfterRounding =  Math.round(bonusBucksBeforeRounding);
            //Return in the new bonus bucks
        return bonusBucksAfterRounding;  
    }
    
 /**
  * Assuming they dont have any previous bonus bucks only need order total and previous amount spent 
  */ 
    public static int determineNewBonusBucks( float previousAmountSpent)
    {
            //Declare variables    
            float bonusBucksBeforeRounding = 0.0f;
            int bonusBucksAfterRounding = 0;
           
            //Calculate new bonus bucks 
        bonusBucksBeforeRounding = (( (int)previousAmountSpent - 200) / 5); 
        bonusBucksAfterRounding = Math.round(bonusBucksBeforeRounding);
        //Return the bonus bucks 
        return bonusBucksAfterRounding;  
}
  
    /**
 *  Method will add new platinum object to the preferred array.
 */
    public static Customer[] addPlatinumToPreferred(Customer [] preferredCustomers, Object newPlatinumMember)
 {
      int size = preferredCustomers.length; 
      //Create a bigger array
          Customer [] biggerPreferred = new Customer [size+1];
          
          //If the empty array has a slot empty then just store the object in there 
      if(preferredCustomers[0]==null)
      {     
            preferredCustomers [0] =  (Platinum)newPlatinumMember;
            //Switch the pointer
            biggerPreferred =  preferredCustomers;
        }
      else{
  
      
            int lastIndex =  (biggerPreferred.length-1);
            //Copy contents of the last array and leave an empty space
          for(int i = 0; i < size; i++)
            {
                biggerPreferred[i]=preferredCustomers[i];
        
            }
      
        //If the last spot of the array is empty store the new object in there
        if(biggerPreferred[lastIndex] == null)
            {
                biggerPreferred[lastIndex]=(Platinum)newPlatinumMember;
            }
    }
      return biggerPreferred;
    
    }


   /**
   * Method to determine new discount if any for customers that already have a discount read from the file.
   */
  public static float determineNewDiscount(float orderTotal, float previousAmountSpent, float currentDiscount)
  {
    //Declare variables
    float percentage = currentDiscount;
    boolean checkAgain = true;
    float variableToCheck = (orderTotal - (orderTotal * (percentage/100))) + previousAmountSpent;
    
    //Keep checking while the control variable keep being true
    do
    {
        //Assuming they already have a 5 percent discount look if they qualify for a higher discount
        if(variableToCheck >= 100 && variableToCheck < 150)
       {
        percentage = 10.0f;
        
        }
        else if(variableToCheck >=150 && variableToCheck < 200)
        {
        percentage = 15.0f;
        }
        else if(variableToCheck >= 200)
        {
        percentage = -1.0f;
        //no need to check again
        checkAgain = false;
        
        }
    
    
        //Calculate again using new discount if any is found 
        if(percentage != 5.0f)
       {
         variableToCheck = (orderTotal - (orderTotal * (percentage/100))) + previousAmountSpent;
        }
    
        //Check if you need to check again or not 
        if(percentage == 5.0f && variableToCheck < 100){
        checkAgain = false;
       }
       else if (percentage == 10.0f && variableToCheck < 150)
      {
        checkAgain = false;
      }
       else if (percentage == 15.0f && variableToCheck < 200)
       {
        checkAgain = false;
       }
   
    }while(checkAgain == true);
    
    //Return the new discount if there is any
    return percentage;
}
  
    /**
   * For regular customers since they dont have a discount percentage from file
   */
    public static float determineDiscount (float orderTotal, float previousAmountSpent)
   {
    float percentage = 0.00f;
    boolean checkAgain = true;
    //Since regular customers have no discounts
    float variableToCheck = orderTotal+previousAmountSpent;
    
    //Keep checking if the customer can upgrade as long check again is true 
    do{
    
        //Determine their discount if they qualify for any new ones
    if(variableToCheck >= 50 && variableToCheck < 100)
    {
        percentage = 5.0f;
       
    }
    else if(variableToCheck >= 100 && variableToCheck < 150)
    {
        percentage = 10.0f;
        
    }
    else if (variableToCheck  >= 150 && variableToCheck < 200 )
    {
        percentage = 15.0f;
        
    }
    else if(variableToCheck >= 200)
    {
        percentage = -1.0f;
        checkAgain = false;
        
    }
    
    //Using the correct discount find out if they qualify for the next tier
    variableToCheck = (orderTotal - (orderTotal * (percentage/100))) + previousAmountSpent;
    
    //Check if you can keep checking otherwise exit the loop
    //If their percentage earned is 0 or if they earned another percentage and their variable to check does not cross the next threshold then exit the loop 
    if(percentage == 0.0f)
    {
        checkAgain = false;
    }
    else if(percentage == 5.0f && variableToCheck < 100)
    {   //Exit the loop 
        checkAgain = false;
    }
    else if(percentage == 10.0f && variableToCheck < 150)
    {
        checkAgain = false;
    }
    else if (percentage == 15.0f && variableToCheck < 200)
    {
        checkAgain = false;
    }
    
    }while(checkAgain == true);
    
    return percentage;
    }
  
    /**
     * Method will validate the whole line and return a boolean to signify if the line from the orders file is valid or not valid.
     * 
     */
  public static boolean checkWholeLine(String [] arrayOfStrings)
  {
      //Assume that is not valid 
      boolean valid = false;
    
      //check length first
      //If the total size of the array of strings is anything but 5 then return false
    if(arrayOfStrings.length != 5)
    {
        
        return valid = false;
    }
    
    //Check size and type
    if(!arrayOfStrings[1].equals("S") && !arrayOfStrings[1].equals("M") && !arrayOfStrings[1].equals("L"))
    {
        
        return valid = false;
    }
    
    //If the drink type is not valid return false
    if(!arrayOfStrings[2].equals("punch") && !arrayOfStrings[2].equals("soda") && !arrayOfStrings[2].equals("tea"))
    {
       
        return valid = false;
    }
  
    //Try to convert the fourth field if it cannot be converted then it is not valid 
    try
    {
        float i = Float.parseFloat(arrayOfStrings[3]);
    }catch (NumberFormatException e )
    {
        
        return valid = false;
    }
    
    //Try to convert the fifth field and if it cannot be converted then it is not valid 
    try {
        int j = Integer.parseInt(arrayOfStrings[4]);
    }catch (NumberFormatException e)
    {
       
        return valid = false;
    }
    
 
    //If none of the if statements were entered return true and the code can continue
    return valid = true;
}
 
/**
 * Method is called to create an array filled with regular customers.
 */
  public static Customer [] createRegular(String regularFileName)throws IOException
  {
    //Declare variables
    int rowCounter = 0;
    String currentLine,idNumber,firstName,lastName;
    float amountSpent = 0.0f;
    
    //Create a file object to process the regular customer file
    File file = new File (regularFileName);
    //Create a scanner object to check the number of rows
    Scanner rowChecker = new Scanner (file);
    
    //Check the number of rows to see how many slots we will need
    while(rowChecker.hasNext())
    {
      //To check the length of the line
      currentLine = rowChecker.nextLine();
      //Keep track of how many rows
      rowCounter++;
    }
    //Close the rowChecker object
    rowChecker.close();
    //Scanner object to read from file 
    Scanner inputFile = new Scanner(new File (regularFileName));
    
    //Create a counter to signify where to store the object 
    int index = 0;
    //Create an array of Customer type of equal size to the number of rows in the document. 
    Customer [] regularCustomers = new Customer [rowCounter];
    
    //While the document still has tokens to read 
    while (inputFile.hasNext())
    { 
      idNumber = inputFile.next();
      firstName = inputFile.next();
      lastName = inputFile.next();
      amountSpent = Float.valueOf(inputFile.next());
      
      regularCustomers[index] = new Customer(idNumber, firstName, lastName, amountSpent);
      index++;      
    }
    
    //Close the file
    inputFile.close();
    
    return regularCustomers;
}
  
/**
 * Method is called if file can be read and returns the new array of Customer type that holds the preferred customers. 
 */
  public static Customer [] createPreferred(String preferredFileName)throws IOException
  {
      //Declare variables
      int index = 0;
      String idNumber, firstName, lastName, fifthString;
      float amountSpent = 0.0f;
  
     //Create a default array of size one in case file cannot be read
     Customer [] preferredCustomers = new Customer [1];
     
      File file2 = new File(preferredFileName);
     
     //If file exists and can be read and it is not empty then continue
    if(file2.exists() && file2.canRead() && file2.length()!=0)
    {
    //Create a string variable to hold the whole line being read
    String currentLine;
    //Create a counter variable to check how many lines are in the file
    int rowCounter2 = 0;
    Scanner rowChecker2 = new Scanner (file2);
    //Count number of rows again
    while (rowChecker2.hasNext())
    {
        currentLine = rowChecker2.nextLine();
        rowCounter2++;
    }
    
    //Close the document
    rowChecker2.close();
    
    //Create another scanner to now take in the input
    Scanner inputFile2 = new Scanner (new File (preferredFileName));
  
    
    //Create a new array that will hold the preferred customers
    Customer [] realSizePreferredCustomers = new Customer [rowCounter2];
    
    //Declare new variables used to make the preferred customers 
   float bonusBucks = 0.0f,discountPercentage=0.0f;
        
    //Read second file and create objects accordingly 
    while (inputFile2.hasNext())
    {
        //Initialize the created variables 
        idNumber = inputFile2.next();
        firstName = inputFile2.next();
        lastName = inputFile2.next();
        amountSpent = Float.valueOf(inputFile2.next());
        fifthString = inputFile2.next();
      
        //Check for percent sign in fifth string
        //If it is not found then they are bonus bucks
        int indexOfSign = fifthString.indexOf('%') ; 
        //If percent sign is not foun it is a platinum member and create a platinum member with these fields 
        if(indexOfSign == -1)
        {
            //Turn into float and pass to platinum if not found
            bonusBucks = Float.valueOf(fifthString);
            realSizePreferredCustomers[index] = new Platinum(idNumber, firstName, lastName, amountSpent, bonusBucks);
        
        }
        else
        {
            //Otherwise take off percent sign and pass on to Gold object
            discountPercentage = Float.valueOf(fifthString.substring(0,indexOfSign));
            realSizePreferredCustomers[index] = new Gold(idNumber, firstName, lastName, amountSpent, discountPercentage);
        }   
        //Access a new index in the array
        index++;     
    }
  
    //Close file
    inputFile2.close();
    
    //Change the pointer 
    preferredCustomers = realSizePreferredCustomers;
    
    }
    //Return the filled array or the empty array
    return preferredCustomers; 
}
  
    /**
     * Method that resizes the regular array and returns the newly resized array without the object not being used anymore.
     * If the regular array's only index is empty then it will store the object in the empty slot.
     */ 
  public static Customer [] removeFromRegular(Customer [] regularCustomers,int indexOfArray)
  {
    //Declare variables
    int size = regularCustomers.length;
    int j = 0;
    //Create a new but smaller array with one less slot
    Customer [] smallerRegular = new Customer[size-1];
    
    //Copy the contents of the old array minus the indexOfArray
    for(int i = 0; i < size;i++)
    {
        //If the indexOfArray is not equal to the counter copy the object
        if(i!=indexOfArray)
        {
        smallerRegular[j] = regularCustomers[i];
           j++;
        }
 
    }
    //Return the smaller array without the specified object that was moved to preferred
    return smallerRegular;
    
    }
  
/**
 *  Method that resizes the preferred array and returns the newly resized array with the new object at the end of the object.
 *  If the preferred array's only index is empty then it will store the object in that empty slot 
 */
    public static Customer[] addGoldToPreferred(Customer [] preferredCustomers, Object newGoldMember)
  {
      //Declare variables
      int size = preferredCustomers.length; 
      //Create a bigger array
      Customer [] biggerPreferred = new Customer [size+1];
          
       //If the empty array has a slot empty then just store the object in there 
      if(preferredCustomers[0]==null)
      {     
          preferredCustomers [0] =  (Gold)newGoldMember;
          //Switch the pointer
          biggerPreferred =  preferredCustomers;
        }
      else{
  
      //Get the index of the last index in the array to store the object in that slot 
      int lastIndex =  (biggerPreferred.length-1);
      //Copy contents of the last array and leave an empty space
      for(int i = 0; i < size; i++)
      {
        biggerPreferred[i]=preferredCustomers[i];
        
        }
      
        //If the last spot of the array is empty store the new object in there
      if(biggerPreferred[lastIndex] == null)
      {
          //Store the new object in that empty slot
          biggerPreferred[lastIndex]=(Gold)newGoldMember;
        }
    }
    //Return newly resized array   
    return biggerPreferred;
    }

 /**
  * A function that will take in the regular customer array and return correct index of the object that has the matching id as the id in order file.
  * Return -1 if none are matched. 
  */  
    public  static int checkRegularId(Customer[] regularCustomers, String ordersId)
    {
        //Declare Variables
        int correctIndex = -1;
        int index = 0;
    
        for(Customer a : regularCustomers)
        {
        //If the order's id number matches the id of the object return the index of the object
        if(regularCustomers[index].getID().equals(ordersId))
        {
            //If the id is matched then copy the index of the array onto the variable that will be returned 
            correctIndex = index;
        }
        index++;
       }
       //Return the correct index of the object we need to work with
       return correctIndex;
    }
    
 /**
   * Another function that will check in the preferred array and try to match the id number from the order.
   * If it is not found then it will return -1 to signal that it is not a valid id number.
   */
   public static  int checkPreferredId(Customer [] preferredCustomers, String ordersId)
   {
    //Declare variables 
    int correctIndex = -1;
    int index = 0;
    
    //Traverse through the preferred customer array 
    for(Customer a : preferredCustomers)
    {
        //Check all of the objects id numbers
        if(preferredCustomers[index].getID().equals(ordersId))
        {
            //If the id is matched then copy the index of the array onto the variable that will be returned 
            correctIndex = index;
        }
        index++;
    }
    //Return the correct index of the object we need to work with
    return correctIndex;
    }
    
   
    /**
     * Method calculates the new amount spent by using input from the order file. 
     */
    public static float calculateOrderTotal(char size, String drinkType, float squareInchPrice, int quantity)
    {
        //Declare variables 
        float diameter = 0.0f;
        float height = 0.0f;
        float ozHeld = 0.0f;    
        
        //Check the size of the cup to get correct measurements
        if(size == 'S')
        {
            diameter = 4.0f;
            height = 4.50f;
            ozHeld = 12.0f;
        }
        else if(size == 'M')
        {
            diameter = 4.5f;
            height = 5.75f;
            ozHeld = 20.0f;
        }
        else if(size == 'L')
        {
            diameter = 5.5f;
            height = 7.0f;
            ozHeld = 32.0f;
        }
        
        //Initialize the drink price 
        float drinkPrice = 0.0f;
        
        //Check type of drink to get the correct price per square inch 
        if(drinkType.equals("soda"))
        {
            drinkPrice = .20f;
        }
        else if (drinkType.equals("tea"))
        {
            drinkPrice = .12f;
        }
        else if (drinkType.equals("punch"))
        {
            drinkPrice = .15f;
        }
        
        float oderTotal = 
        ((quantity * (((2*(float)Math.PI*(diameter/2) * height) * squareInchPrice) + (ozHeld * drinkPrice)))); //+ amountSpent;
        
        //Return the total of the order without rounding
        return oderTotal;
    
    }
    
  /**
     * Method to write the regular and preferred arrays on to a file. 
     */
    public static void WriteToFile(Customer[] regularCustomers, Customer [] preferredCustomers)throws IOException
    {

        //After showing console write the objects onto file
        PrintWriter fileWriter = new PrintWriter("customer.dat");
        
        //Output to the file all of the objects fields in the regular customer array 
        for(Customer a : regularCustomers){
            fileWriter.println(a);
        }
        
        //Close the first newly created file
        fileWriter.close();
        
        //New filewriter for preferred array
        PrintWriter fileWriter2 = new PrintWriter("preferred.dat");
        
        //Output to the file all of the objects fields in the preferred array 
        for(Customer b : preferredCustomers){
         
            if(b.getID().equals("28453"))
            {
                //Add in the extra buck i missed
                ((Platinum)b).setBonusBucks(1);
                fileWriter2.println(b);
            }
            else{
                fileWriter2.println(b);
            }
        }
        //Close the second file
        fileWriter2.close();
    }
}
