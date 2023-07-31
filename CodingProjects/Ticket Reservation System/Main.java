//Abel Hernandez
//AXH-127530
import java.lang.*;
import java.util.*;
import java.io.*;
/**
 * @Author: Abel Hernandez
 * @AXH-127530
 */
class Orders{
    //Declare variables
    private int auditoriumNumber;
    private int numOfAdults;
    private int numOfKids;
    private int numOfSeniors;
    private List<String> seatList = new ArrayList<String>(); 
    /**
     * Regular constructor
     * @param Nothing
     * @return Nothing
     */
    public Orders(){}
    /**
     * Overloaded Constructor
     * @param auditorium number, number of adults, number of kids, and number of seniors
     * @return Nothing 
     */
    public Orders(int auditoriumNumber,int numOfAdults, int numOfKids, int numOfSeniors){
    this.auditoriumNumber = auditoriumNumber;
    this.numOfAdults = numOfAdults;
    this.numOfKids = numOfKids;
    this.numOfSeniors = numOfSeniors;}
    /**
     * Adding seats to the list of seats
     * @param String seat 
     */
    public void addSeats(String seat){
        //Add the seat to the list
        seatList.add(seat); 
        //Sort the seat list
        Collections.sort(this.seatList);}
    /**
     * Find the index of the requested seat 
     * @param String seat 
     * @return integer indicating the index of the seat within the list 
     */
    public int findSeatIndex(String seat){return seatList.indexOf(seat);}
    /**
     * Remove the indicated seat from the list of strings
     * @param Integer indicating the index of the seat to remove 
     * @return Nothing
     */
    public void removeSeat(int index){seatList.remove(index);}
    /**
     * Get the list of seats in the order.
     * @param Nothing
     * @return List of strings
     */
    public List<String> getListOfSeats(){return this.seatList;}
    /**
     * Set the number of adult tickets in the order
     * @param Integer amount of adult tickets 
     * @return Nothing 
     */
    public void setAdultQuant(int numOfAdults){this.numOfAdults += numOfAdults;}
    /**
     * Get the quantity of adult tickets in the order
     * @param Nothing
     * @return Number of adults
     */
    public int getAdultQuant(){return this.numOfAdults;}
    /**
     * Update the number of adult tickets in the order minus one.
     * @param Nothing
     * @return Nothing
     */
    public void minusOneAdult(){this.numOfAdults--;}
    /**
     * Set the number of kid tickets in the order
     * @param Integer amount of kid tickets 
     * @return Nothing  
     */
    public void setChildQuant(int numOfKids){this.numOfKids += numOfKids;}
    /**
     * Get the quantity of kid tickets in the order
     * @param Nothing
     * @return Number of kids
     */
    public int getChildQuant(){return this.numOfKids;}
    /**
     * Update the number of kid tickets in the order minus one.
     * @param Nothing
     * @return Nothing
     */
    public void minusOneKid(){this.numOfKids--;}
    /**
     * Set the number of senior tickets in the order
     * @param Integer amount of senior tickets 
     * @return Nothing 
     */
    public void setSeniorQuant(int numOfSeniors){this.numOfSeniors += numOfSeniors;}
    /**
     * Get the quantity of senior tickets in the order
     * @param Nothing
     * @return Number of seniors
     */
    public int getSeniorQuant(){return this.numOfSeniors;}
    /**
     * Update the number of senior tickets in the order minus one.
     * @param Nothing
     * @return Nothing
     */
    public void minusOneSenior(){this.numOfSeniors--;}
    /**
     * Get the auditorium number in the order to select the correct auditorium object.
     * @param Nothing
     * @return Integer number of auditorium
     */
    public int getAuditoriumNumber(){return this.auditoriumNumber;}
    /**
     * Override the toString function to format how to return the list of seats.
     * @param Nothing
     * @return Formatted string 
     */
    @Override
    public String toString(){
        return String.format("Auditorium %d, %s \n  %d adult, %d child, %d senior \n" ,this.auditoriumNumber,this.seatList.toString().replace("[","").replace("]",""),
        this.numOfAdults, this.numOfKids, this.numOfSeniors);
    }
}
 class User{
    //Declare variables 
    private String username;
    private String password;
    private List<Orders> cart = new ArrayList<Orders>(); 
/**
 * Simple no arg constructor
 */
public User(){}
/**
 * Overloaded constructor
 * @param username, password
 */
public User(String username, String password){
this.username = username;
this.password = password;}
/**
 * Method to get password to match up with input password
 * @param Nothing
 * return boolean
 */
public boolean passwordEquals(String password)
{return this.password.equals(password);}
/**
 * Get the username to check if it is the admin who is logged in
 * @param Nothing
 * @return String username
 */
public String getUsername(){
    return this.username;}
/**
 * Get the cart holding the order objects
 * @param Nothing
 * @return The order cart
 */
public List<Orders> getCart(){
    return this.cart;}
/**
 * Method to add order to list of orders
 * @param Order object
 * @return Nothing
 */
public void addOrder(Orders order){ cart.add(order);}
/**
 * Get the cart size to see how many orders are in there
 * @param Nothing
 * @return Integer size of the cart
 */
public int getCartSize(){return this.cart.size();}
/**
 * Get the specified order with the given index
 * @param Integer index
 * @return Order object in the specified index 
 */
public Orders getOrder(int index){return cart.get(index);}
/**
 * Method to remove whole order
 * @param Integer index
 * @return Nothing
 */
public void removeOrder(int index){cart.remove(index);}
/**
 * Method to override the to String 
 */
public String toString(){
return String.format("%s",this.cart.toString().replace("[", "").replace("]", ""));}
}
public class Main
{
  public static void main(String args[])throws IOException
  {
     //Read customer information and create user objects 
     //Create theater objects to reserve seats
     Auditorium [] roomHolder = new Auditorium[3];
     try{
     roomHolder[0] = new Auditorium(createSeatGuide("A1.txt"),1);//Auditorium 1
     roomHolder[1] = new Auditorium(createSeatGuide("A2.txt"),2);//Auditorium 2
     roomHolder[2]= new Auditorium(createSeatGuide("A3.txt"),3);//Auditorium 3
    }catch(Exception e)
    {System.out.println("Please double check the seating arrangement files.");}
     //Reference the returned hash map
     Map<String, User> map = getMap();
     //create a scanner object 
     Scanner kbd = new Scanner(System.in);
     //Declare variables
     boolean userVerified = false,  exited = false, logOut = false;
     int userMainAction = 0, adminMainAction = 0, theaterSelection = 0, rowRequested = 0;
     int adultTickets = 0, childTickets = 0, seniorTickets = 0;
     int totalTicketsRequested;
     char requestedColumn;
     User user = null;
  
     do{
        //While the user is not verified return to the greeting page or if the logged out
          while(userVerified == false)
            {
            //Greet the user
            System.out.println("Welcome to Ticket Master.");
            System.out.println("Please log in.");
            user = getProfile(kbd,map);
                //If the user pointer returns actual user then no need to loop again
                 if(user != null){
                userVerified = true;}
             }
             //Keep executing until the user decides to log out 
             while(logOut != true)
             { //If the username is not admin then display the proper menu selection
                 if(!(user.getUsername().equals("admin"))){
                 //Get user action
                 userMainAction = getUserMainAction(kbd);
                  if(userMainAction == 1){ //Walk user through reserving tickets process
                     //Get the selection for theater number but minus 1 since roomHolder index starts at 0
                     theaterSelection = getTheaterSelection(kbd) - 1;
                     System.out.println("Here is the current seating arrangement.");
                     //Reference the theater object selected and show the seats
                     roomHolder[theaterSelection].showSeats();
                     //Ask for input to check availability/reserve seats
                     //Ask for row
                     rowRequested = getRowNumber(kbd,roomHolder[theaterSelection].getNumOfRows());
                     //Ask for column
                     requestedColumn = getStartingColumn(kbd,roomHolder[theaterSelection].getNumOfColumns());
                     //Get quantities for ticket type 
                     System.out.println("How many adult tickets do you need?");
                     adultTickets = getQuantity(kbd);
                     System.out.println("How many child tickets do you need?");
                     childTickets = getQuantity(kbd);
                     System.out.println("How many senior tickets do you need?");
                     seniorTickets = getQuantity(kbd);
                     //Sum up the total amount of tickets to find best available if seats are not avaialble
                     totalTicketsRequested = adultTickets + childTickets + seniorTickets;
                        if(roomHolder[theaterSelection].checkAvailability(rowRequested,requestedColumn,totalTicketsRequested))
                        {
                            //Reserve the seats
                            roomHolder[theaterSelection].reserveSeats(rowRequested,requestedColumn,adultTickets,childTickets,seniorTickets);
                            //Create a new order object with the necessary information
                            Orders newOrder  = new Orders(roomHolder[theaterSelection].getAuditoriumNumber(),adultTickets,childTickets,seniorTickets);
                            //Get the string value of the row 
                            String startingRow = String.valueOf(rowRequested);
                            //Loop to store the necessary strings into the order string list of seats 
                            for(int i = 0; i < totalTicketsRequested;i++)
                            { 
                              //Concatenate the row and the column of the seat 
                              String seatsToAdd = rowRequested + String.valueOf(requestedColumn);
                              //Add the seat string one by one
                              newOrder.addSeats(seatsToAdd);
                              //Update the column letter
                              requestedColumn++;
                            }
                            //Add the order to cart of the user
                            user.addOrder(newOrder);
                        }
                        else {
                           //If the best seats exist then ask the user if they wish to reserve them 
                            if(roomHolder[theaterSelection].bestSeats(totalTicketsRequested) != null){
                            Seat bestSeat = (Seat)roomHolder[theaterSelection].bestSeats(totalTicketsRequested);
                            //Ask the user if they would like to reserve the best seats
                            System.out.println("Would you like to reserve these instead?");
                            System.out.println("Y/N?");
                            //Display the best seats
                            System.out.println(bestSeat.getRow() + "" + bestSeat.getColumn() + " - " + bestSeat.getRow() + "" + (char)(bestSeat.getColumn() + (totalTicketsRequested-1)));
                            //Declare variable to get user answer 
                            char userAnswer = kbd.next().charAt(0);
                            //If they answered yes 
                            if(userAnswer == 'Y'|| userAnswer == 'y')
                            {
                                //Reserve the best seats 
                                roomHolder[theaterSelection].reserveSeats(bestSeat.getRow(),bestSeat.getColumn(),adultTickets,childTickets,seniorTickets);
                                //Make a new order object to add to the user cart
                                Orders newOrder = new Orders(roomHolder[theaterSelection].getAuditoriumNumber(),adultTickets,childTickets,seniorTickets);
                                //Get the starting row string value 
                                String startingRow = String.valueOf(bestSeat.getRow());
                                char column = bestSeat.getColumn();
                                for(int i = 0; i < totalTicketsRequested;i++)
                                { //Concatenate the row and the column of the seat to store in the list of seats 
                                  String seatsToAdd = startingRow + String.valueOf(column);
                                  //Add the seat string to the list of seats 
                                  newOrder.addSeats(seatsToAdd);
                                  //Update the column letter
                                  column++;
                                }
                                //Add the order to users cart
                                user.addOrder(newOrder);
                               
                            }
                            //Eat up the remainder of the line after getting the first character in the stream.
                            kbd.nextLine();
                              }
                            else{
                                //Otherwise let the user the seats are not available 
                                System.out.println("no seats available.");}
                        }
                
                }
                    else if(userMainAction == 2 && !user.getCart().isEmpty())//View Orders only if the action is 2 and the cart is not empty
                    {   
                       //Show the user the current orders 
                        System.out.println("The current orders are: ");
                        for(Orders a: user.getCart())
                        {
                            //Output each order 
                            System.out.println(a);
                        }
                
                    }
                    else if(userMainAction == 3 && !user.getCart().isEmpty())//Update Order
                    {
                        //Index i will keep the orders numbered 
                        int i = 1;
                        for(Orders a: user.getCart())
                        {
                            //Output the numbered orders 
                            System.out.println(i+"."+a);
                            i++;
                        }
                       //Call the update order function
                        updateOrders(kbd,user,roomHolder);
                    }
                    else if(userMainAction == 4 )//Show Receipt if the action is 4 
                    { 
                        //If the users cart is not empty then display the receipts 
                        if(!user.getCart().isEmpty()){
                        //Create variables to show the total of the orders and the whole carts 
                        float orderTotal = 0.0f;
                        float customerTotal = 0.0f;
                        System.out.println("The current receipts are: ");
                        for(Orders a: user.getCart())
                        {
                            //Calculate the order total
                            orderTotal = (a.getAdultQuant() * 10.0f) + (a.getChildQuant() * 5.0f) + (a.getSeniorQuant() * 7.50f);
                            System.out.println(a); // Print the order 
                            System.out.printf("Order Total: $%.2f\n",orderTotal); //Print the order total 
                            //Calculate the customer total 
                            customerTotal += orderTotal;
                        }
                        //Display the customer total 
                        System.out.printf("Customer Total: $%.2f\n",customerTotal);}
                        //Otherwise let them know the cart is empty and they have no total 
                        else{
                            System.out.println("No orders");
                            System.out.println("Customer Total: $0.00");
                        }
                      }
                    else if(userMainAction == 5)//Logout if the action is 5
                    {
                        //Turn logOut to true as the key to exit the loop 
                        logOut = true;}
                    else{//Let them know there is no orders
                        System.out.println("No orders");
                    } 
             }
             //If the user's username is admin then they get different abilities
             else if(user.getUsername().equals("admin")){
                   //Get their input to see what they want to do 
                   adminMainAction = getAdminMainAction(kbd);
                   if(adminMainAction == 1)
                   {//Call print reports
                       printReports(roomHolder);
                    }
                   else if(adminMainAction == 2)
                   {
                       //If they wish to log out then give them the key to log out 
                       logOut = true;
                    }
                   else if(adminMainAction == 3)
                   {     //Admin has option to exit
                       //Give key to log out and exit 
                       logOut = true;
                       exited = true;}
              
                }
               }
               //reset keys 
               userVerified = false;
               logOut = false;
            }while(exited != true);
            //Let user know the program was exited 
            System.out.println("Exited the program");
   }
   /**
    * Method to write the report of each theater.
    * @param Auditorium room holder
    * @return Nothing 
    */
   public static void printReports(Auditorium [] roomHolder) throws IOException{
       int overAllOpen = 0, overAllSold = 0, overAllAdults = 0, overAllKids = 0, overAllSeniors = 0;
       float overAllSales = 0.0f;
       //System.out.println("Auditorium #   Open    Sold    Adult    Child    Senior   Total  ");
       for(Auditorium a : roomHolder)
       {
    
           //Declare variables to keep track of seat quantities
           int totalAdults = 0, totalKids = 0, totalSeniors = 0, totalTicketsSold = 0, totalTicketsOpen = 0;
           float totalSales = 0.0f;
           //Create pointers to go through the whole auditorium
           Node <Seat> rowPointer = a.getHeadNode();
           Node <Seat> columnPointer = a.getHeadNode();
           String outputFile = "";
           if(a.getAuditoriumNumber() == 1)
           {
           //Create a file object to write the current seating to a file
            outputFile = "A1Final.txt";}
           else if(a.getAuditoriumNumber() == 2){
               outputFile = "A2Final.txt";
            }
           else if(a.getAuditoriumNumber() == 3)
           {
               outputFile = "A3Final.txt";
            }
           //Create a file writer pointer 
           PrintWriter fileWriter =  null;
           //Try to create a new print writer object 
           try{
           fileWriter = new PrintWriter(outputFile);
            }catch(Exception e){System.out.println("Double check the output file names.");}
      
           //Loop to run through the rows
           while(rowPointer != null)
           {
               //Loop through the columns
            while(columnPointer != null)
            {
                //Print the objects character onto file where the pointer is currently in
                fileWriter.print(columnPointer.getPayLoad().getTicketType());
                //Keep track of what kind of seats are being checked
                if(columnPointer.getPayLoad().getTicketType() == 'A')
                {
                    totalAdults++;
                    totalTicketsSold++;
                }
                else if(columnPointer.getPayLoad().getTicketType() == 'C')
                {
                    totalKids++;
                    totalTicketsSold++;
                }
                else if(columnPointer.getPayLoad().getTicketType() == 'S')
                {
                    totalSeniors++;
                    totalTicketsSold++;
                }
                else if(columnPointer.getPayLoad().getTicketType() == '.')
                {
                    totalTicketsOpen++;
                }
                //Move pointer over to the right
                columnPointer = columnPointer.getRight();
            }
            //Move on to new line in file
            fileWriter.printf("\n");
            //Update the row pointer
            rowPointer = rowPointer.getDown();
            //Have the pointers meet at the same row
            columnPointer = rowPointer;
          }
        
          //Close the output file
          fileWriter.close();
        
          //Calculate the totals
          overAllOpen += totalTicketsOpen;
          overAllSold += totalTicketsSold;
          overAllAdults += totalAdults;
          overAllKids += totalKids;
          overAllSeniors += totalSeniors;
         //Calculate the total sales
          totalSales = (10.00f * (float)totalAdults)+ (5.00f * (float)totalKids) + (7.50f * (float)totalSeniors);
           overAllSales += totalSales;
           //Write the report to the console
          System.out.printf("Auditorium  %d    %d    %d    %d    %d    %d    $ %.2f \n",a.getAuditoriumNumber(),totalTicketsOpen,totalTicketsSold, totalAdults, totalKids, totalSeniors, totalSales);
    }
        //Show the totals for all theaters overall  
        System.out.printf("Total      %d    %d    %d    %d    %d    $ %.2f\n", overAllOpen, overAllSold, overAllAdults, overAllKids, overAllSeniors, overAllSales);
        
    }
    /**
     * Method to obtain order to be updated
     * @param Scanner object, Ineteger cartSize
     * @return The integer of the order being updated 
     */
    public static int getOrderToUpdate(Scanner kbd, int cartSize){
        //Declare local variables 
        int order = 0;
        boolean valid = false;
        String wholeLine;
        //While the input is invalid loop again 
        while(valid != true){
            System.out.println("Which of these orders would you like to update? ");
            wholeLine = kbd.nextLine();
            try{
                order = Integer.valueOf(wholeLine);
                valid = true;
            }catch(Exception e)
            {continue;}
            //If the order number is greater than the cart size index pool then loop again 
            if(order > cartSize){
                valid = false;}
           
        }
        return order;
    }
   /**
    * Method to add tickets, delete tickets or cancel whole orders
    * @param Scanner object, User object, and the auditorium room holder
    * @return Nothing
    */
   public static void updateOrders(Scanner kbd, User user, Auditorium [] roomHolder ){
       //Declare variables
       int rowRequested = 0;
       int adultTickets = 0, childTickets = 0, seniorTickets = 0;
       char requestedColumn;
       int totalTicketsRequested;
       boolean finishedUpdating = false;
       //Get the order to update minus 1
       int orderToUpdate = getOrderToUpdate(kbd,user.getCartSize()) - 1;
       //Point to the order to update 
       Orders currentOrder = user.getOrder(orderToUpdate);
       //Loop until some kind of update happens to order 
       while(finishedUpdating != true){
           //Get the action they wish to perform
           int updateOrderAction = getOrderUpdateAction(kbd);
           if(updateOrderAction == 1 && !currentOrder.getListOfSeats().isEmpty()){//Add tickets to order
                      //Show the seating arrangement 
                     System.out.println("Here is the current seating arrangement.");
                     //Reference the theater object selected and show the seats
                     roomHolder[currentOrder.getAuditoriumNumber()-1].showSeats();
                     //Ask for input to check availability/reserve seats
                     rowRequested = getRowNumber(kbd,roomHolder[currentOrder.getAuditoriumNumber()-1].getNumOfRows());
                     requestedColumn = getStartingColumn(kbd,roomHolder[currentOrder.getAuditoriumNumber()-1].getNumOfColumns());
                     System.out.println("How many adult tickets do you need?");
                     adultTickets = getQuantity(kbd);
                     System.out.println("How many child tickets do you need?");
                     childTickets = getQuantity(kbd);
                     System.out.println("How many senior tickets do you need?");
                     seniorTickets = getQuantity(kbd);
                     totalTicketsRequested = adultTickets + childTickets + seniorTickets;
                     //If the seats are available then reserve them 
                     if(roomHolder[currentOrder.getAuditoriumNumber()-1].checkAvailability(rowRequested,requestedColumn,totalTicketsRequested))
                        {
                            //Reserve the seats
                            roomHolder[currentOrder.getAuditoriumNumber()-1].reserveSeats(rowRequested,requestedColumn,adultTickets,childTickets,seniorTickets);
                            //Set the new ticket quantities 
                            currentOrder.setAdultQuant(adultTickets);
                            currentOrder.setChildQuant(childTickets);
                            currentOrder.setSeniorQuant(seniorTickets);
                            //Get the string value of the selected row 
                            String startingRow = String.valueOf(rowRequested);
                            for(int i = 0; i < totalTicketsRequested;i++)
                            { 
                                //Concatenate the row and the column of the seats to add them to the list of seats 
                                String seatsToAdd = rowRequested + String.valueOf(requestedColumn);
                                //Reference the current order and add the seat strings
                                currentOrder.addSeats(seatsToAdd);
                                //Update the column 
                                requestedColumn++;
                            }
                            //Give the key to end the loop
                            finishedUpdating = true;
                        }
                     else{
                         //Otherwise dont offer best available and loop again
                         System.out.println("Sorry seats are not available.");}
                    }
           else if(updateOrderAction == 2 && !currentOrder.getListOfSeats().isEmpty()){//Delete tickets one by one if action 2 is selected and list of seats is not empty
               //Prompt user for the row and the column to get the desired seat 
               System.out.println("In order to delete the seat, please provide row number and column letter.");
               int rowToDelete = getRowToDelete(kbd);
               char columnToDelete = getColumnToDelete(kbd);
               //Concatenate the seat string 
               String seatToDelete = String.valueOf(rowToDelete) + String.valueOf(columnToDelete);
               //Find the index of the seat in the order object 
               int seatToDeleteIndex = currentOrder.findSeatIndex(seatToDelete);
               //If is not -1 then delete it
               if(seatToDeleteIndex != -1){
               //Unreserve the seat in the auditoroium
               char letter = roomHolder[currentOrder.getAuditoriumNumber()-1].unreserveIndividualSeat(rowToDelete,columnToDelete);
               //Check if the seat list is empty if it is then delete the whole order
               currentOrder.removeSeat(seatToDeleteIndex);
                    //Update the quantity of ticket types 
                    if(letter == 'A'){
                        //Update adult quantity
                        currentOrder.minusOneAdult();
                    }
                    else if(letter == 'C')
                    {
                        //Update child quantity
                        currentOrder.minusOneKid();
                    }
                    else if(letter == 'S')
                    {
                        //Update senior quantity
                        currentOrder.minusOneSenior();
                    }
                    
                    //Remove the order if the seat list is empty 
                    if(currentOrder.getListOfSeats().isEmpty())
                    {user.removeOrder(orderToUpdate);}
                 //Give key to exit loop    
                finishedUpdating = true;
              }
              else{
                //Otherwise let them know that was not a valid seat and loop again 
                System.out.println("Sorry that was not a valid seat to delete.");
                }
           
        }
       else if(updateOrderAction == 3 && !currentOrder.getListOfSeats().isEmpty()){//Cancel order if action 3 is chosen and the list of seats is not empty 
           //Unreserve all of the seats
           roomHolder[currentOrder.getAuditoriumNumber()-1].unreserveSeats(currentOrder.getListOfSeats());
           //Remove order from user's cart
           user.removeOrder(orderToUpdate);
           //Give key to exit the loop 
           finishedUpdating = true;
        }
       else{
           //Otherwise let user know they have no orders 
           System.out.println("No orders");}
    }
    }
    /**
     * Method to get column to delete seat 
     * @param Scanner object 
     * @return Character of selected column 
     */
    public static char getColumnToDelete(Scanner kbd){
        //Declare local variables
        char column = ' ';
        boolean valid = false;
        //While is not valid keep looping 
        while (valid != true){
            System.out.println("Column letter: ");
            String wholeLine = kbd.nextLine();
            if(wholeLine.length() == 1){
                //Check that the character given is between upper/lower A through Z
                if(wholeLine.charAt(0) >= 'A' && wholeLine.charAt(0) <= 'Z')
                {
                     column = wholeLine.charAt(0);
                
                     valid = true;
                }
                //If it is lower case make it upper case before returning
                else if(wholeLine.charAt(0) >= 'a' && wholeLine.charAt(0) <= 'z'){
                    column = Character.toUpperCase(wholeLine.charAt(0));
              
                    valid = true;
                }
            }
        }
        return column;
    }
    /**
     * Method to get row to delete seat
     * @param Scanner object 
     * @return Integer of the selected row 
     */
    public static int getRowToDelete(Scanner kbd){
    //Declare variables 
    int row = 0;
    boolean valid = false;
    //while is not valid keep looping 
    while(valid != true)
    {
        System.out.println("Row number: ");
        String wholeLine = kbd.nextLine();
        try{
            row = Integer.valueOf(wholeLine);
        }catch(Exception e)
        {continue;}
        //If the row is not 0 but is greater than 1 and less than 100
        if(row != 0 && row >= 1 && row <= 100 )
        {   //Exit the loop 
            valid = true;}
        else{
            continue;}
    }
    return row;
   }
   /**
    * Get update order menu selection
    * @param Scanner object
    * @return Integer of the Order that needs to be updated 
    */
   public static int getOrderUpdateAction(Scanner kbd)
   {
    //Declare variables    
    int action = 0;
    boolean valid = false;
    //While the input is not valid keep looping 
    while(valid != true){
        System.out.println("Please choose one of the following options from the Update Order Menu.");
        System.out.println("1. Add Tickets to order");
        System.out.println("2. Delete tickets from order");
        System.out.println("3. Cancel Order");
        String wholeLine = kbd.nextLine();
        try{
            action = Integer.valueOf(wholeLine);
        }catch(Exception e)
        {
            continue;
        }
        //If is not any of the 3 options then is not valid 
        if(action == 1 || action == 2 || action ==3)
        {
            valid = true;
        }
        else{
            continue;
        }
    }
       return action;}
   /**
    * Get admin action selection.
    * @param Scanner object 
    * @return Integer signifying admins choice 
    */
   public static int getAdminMainAction(Scanner kbd)
   {
    //Declare local variables 
    int action = 0;
    boolean valid = false;
    //While is not valid keep looping 
    while(valid != true){
       System.out.println("Please choose one of the followinig options from the Admin Menu.");
       System.out.println("1.Print Report");
       System.out.println("2.Logout");
       System.out.println("3.Exit");
       String wholeLine = kbd.nextLine();
       try{
           action = Integer.valueOf(wholeLine);
        }catch(Exception e)
        {continue;
        }
       //Loop again if is not any of the valid 3 options 
       if(action == 1 || action == 2 || action == 3)
       { valid = true;}
       else{
           continue;
        }
    }
    return action;}
   /**
    * Method will obatin starting row.
    * @param Scanner object, Integer number of rows
    * @return Integer of selected row
    */
   public static int getRowNumber(Scanner kbd , int numOfRows)
    {
    //Declare local variables
    int rowNumber = 0;
    boolean valid = false;
    //Keep looping until its valid 
    while(valid != true )
    {
        System.out.printf("Please choose the row number you would like to sit in. \n");
        //Ask user for row number they would like to sit in
        String wholeLine = kbd.nextLine();
        //Check if the input is a number
        try{
            //Get the numeric value of the input given 
            rowNumber = Integer.valueOf(wholeLine);
            valid = true;
        }catch (Exception e)
        {
           //If its not a number that was given then loop again
           continue;
        }
        //Check that the number is within range of the maximum number of rows and also more than 0
        if(rowNumber == 0 || rowNumber > numOfRows)
        {
            //Loop again if number is 0 or greater than maximum rows
            valid = false;
            continue;
            
        }
    }
   
    return rowNumber;
    }
   /**
    * Method will obtain the starting column
    * @param Scanner object and Integer number of columns
    * @return Character of the starting column
    */
   public static char getStartingColumn(Scanner kbd, int numOfColumns)
    {
        //Declare the variable to be returned by the method
        //Initialize it to empty character
        char startingColumn = ' ';
        //Save the whole line
        String wholeLine;
        //String to find index of column and determine if its valid
        String possibleColumns = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Assume the column given is not valid
        boolean valid = false;
        while(valid != true)
        {
            //Ask user for starting column that the seat is located in
            System.out.printf("Please choose the starting seat.\n");
            wholeLine = kbd.nextLine();
            //Check that the length of the input given is 1
            if(wholeLine.length() == 1){
                //Check that the character given is between upper/lower A through Z
                if(wholeLine.charAt(0) >= 'A' && wholeLine.charAt(0) <= 'Z')
                {
                     startingColumn = wholeLine.charAt(0);
                
                     valid = true;
                }
                //If it is lower case make it upper case before returning
                else if(wholeLine.charAt(0) >= 'a' && wholeLine.charAt(0) <= 'z'){
                    startingColumn = Character.toUpperCase(wholeLine.charAt(0));
              
                    valid = true;
                }
            }
            //Check the position of the letter and make sure its not out of bounds
            int positionOfLetter = possibleColumns.indexOf(startingColumn);
            if(positionOfLetter == 0 || positionOfLetter>numOfColumns)
            {
                //If empty space or letter out of bounds is given then loop again
                valid=false;
            }
        }
        return startingColumn;
    }
   /**
    * Method will obtain the quantity for ticket types
    */
   public static int getQuantity(Scanner kbd)
   {     //Declare local variables
     int quantity = 0;
     String wholeLine;
     //Assume the input is not valid
     boolean valid = false;
     
        while(valid != true){
         //Take in the whole line to check if it is a number
            wholeLine = kbd.nextLine();
         try{
             //Get numeric value of the line 
             quantity = Integer.valueOf(wholeLine);
             //If exception was not thrown then key to exit loop is unlocked
             valid = true;
            }catch(Exception e)
            {
            //Loop again
            continue;
            }
         
        }
    //Return the requested quantity 
    return quantity;}
   /**
    * Method will get the specific theater selected by the user
    * @param Scanner object 
    * @return Integer signifying the theater selection
    */
   public static int getTheaterSelection(Scanner kbd){
    //Declare local variables 
    int action = 0;
    boolean valid = false;
    //Keep looping until valid 
    while(valid != true)
    {
        System.out.println("Choose from one of our three auditoriums to reserve tickets.");
        System.out.println("1.Auditorium 1");
        System.out.println("2.Auditorium 2");
        System.out.println("3.Auditorium 3");
        String wholeLine = kbd.nextLine();
        try{
            action = Integer.valueOf(wholeLine);
        }catch(Exception e)
        {continue;}
        if(action == 1 || action == 2 || action == 3)
        {valid = true;}
        else{continue;}
    }
    return action;
}
   /**
    * Method will get action for user, for the main menu.
    * @param Scanner object
    * @return Integer signifying the action chosen by the user 
    */
  public static int getUserMainAction(Scanner kbd){
    //Declare variables 
    int action = 0;
    boolean valid = false;
    //Keep looping until valid 
    while(valid!=true)
    {
    System.out.println("Please choose one of the following options from the Main Menu.");
    System.out.println("1.Reserve Seats");
    System.out.println("2.View Orders");
    System.out.println("3.Update Order");
    System.out.println("4.Display Receipt");
    System.out.println("5.Log Out");
    String wholeLine = kbd.nextLine();
    try{
        action = Integer.valueOf(wholeLine);
    }catch(Exception e){
    continue;}
    //If the action is not a selected 1,2 or 3 then its not valid 
    if(action == 1 || action == 2 || action == 3 || action == 4 || action == 5)
    {valid = true;}
    else{continue;}
}
return action;
} 
  /**
   * Method will return value kept in HasMap to update orders.
   * @param Scanner object and the map containing the user objects 
   * @return User object 
   */
  public static User getProfile(Scanner kbd, Map<String,User> map){
    //Declare local variables 
    String userName, password;
    //Keep track of login attempts 
    int loginAttempts = 0;
    System.out.println("Username: ");
    userName = kbd.nextLine();
    //If the exceed 3 attempts then return to greeting page and return null 
    while(loginAttempts < 3){
     System.out.println("Please enter the password.");
     password = kbd.nextLine();
     if(map.get(userName).passwordEquals(password))
     //If password is matched then exit the loop
     {return map.get(userName);}
     //Otherwise track log in attempts and allow no more than 3
     else{
         System.out.println("Invalid password");
         loginAttempts++;}
    }
    System.out.println("Invalid password");
    return null;}
  /**
     * Method will read in the user data base file and return a hash map object
     * @param Nothing
     * @return filled in HashMap
     */
    public static Map<String,User> getMap()throws IOException
    {   
      String username;
      String password;
     //Make a pointer for the file object
     File userFile = null;
     //try to open it 
     try{
         userFile = new File ("userdb.dat"); 
        }catch(Exception E)
        {System.out.println("Double check the input file");
        }
     //If successful then read in the information into the HashMap
     Scanner fileReader = new Scanner(userFile);
     //Instantiate HashMap object to store Users
     HashMap<String,User> map = new HashMap<String,User>();
     while(fileReader.hasNext())
     {username = fileReader.next();
      password = fileReader.nextLine().trim();
      map.put(username,new User(username,password));
        }
    return map;}
  /**
     * Method will create a 2d array of seat objects to work as a guide for the Auditorium constructor.
     * @param String filename 
     * @return the guide used to make the auditorium object 
     */
    public static Seat[][] createSeatGuide(String fileName)throws IOException
    {
    //Declare variables
    int numRows = 0,numColumns = 0;
    //Create a file pointer that points to nothing
    File file = null;
    //Assume the file does not exist and cannot be read 
    boolean fileExists = false, fileRead = false;
    while (fileExists == false && fileRead == false)
    {
         try{
             //Attempt to create a file object with the name given
             file = new File(fileName);
             //If the file exists and can be read then exit the loop
             fileExists = file.exists();
             fileRead = file.canRead();
            }catch (Exception E)
            {
            //Do nothing and continue to loop until valid file name is given 
            continue;
            }
    }
    Scanner sizeChecker = new Scanner(file);
    //Check how many rows and columns you will need to accomodate all of the seats in the auditorium
    while(sizeChecker.hasNext())
    {   
        //Create a string variable to hold the last line in the file
        String measureColumns = sizeChecker.nextLine();
        //Number of columns is equal to the length of the sentences in the file
        numColumns = measureColumns.length();
        //Keep track of how many rows are in the file
        numRows++;
    }
    //Close the file
    sizeChecker.close();
    //Create the 2d array
    Seat[][] guide = new Seat[numRows][numColumns];
    //Scanner object to get the character from the file
    Scanner inputFile = new Scanner(file);
    //Set first row of array pointer
    int row = 0;
    //Set the row number
    int rowNumber = 1;
    //As long as the file is not empty
    while(inputFile.hasNext())
    {
        //Store the current line in String variable
        String currentRow = inputFile.nextLine();
        
        //Set the column letter to A
        char columnLetter = 'A';
            //Iterate through the whole line
        for(int column = 0; column<currentRow.length();column++)
            {
                //Set current character in line to seatType
                char seatType = currentRow.charAt(column);
                //Create the seat object and store it in the 2d array
                Seat currentSeat = new Seat(rowNumber,columnLetter,seatType);
                guide[row][column] = currentSeat;
                //Update the column letter
                columnLetter++;
            }
        //Update the row number guide for the object
        rowNumber++;
        //Update array row pointer
        row++;
    }
    //Close the file once again
    inputFile.close();
    //Return the grid back to main to create the linked list
    return guide;}
}

 



