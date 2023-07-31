//Abel Hernandez
//AXH-127530
import java.lang.*;
import java.util.*;
import java.io.*;
/**
 * Auditorium class will build an auditorium object.
 *
 * @author Abel Hernandez
 * AXH-127530
 * @version 11/14/2020
 */
public class Auditorium
{
    //Declare local variables
    private Node <Seat> head;
    private int auditoriumNumber;
    private Seat [][] guide;
    private int numOfRows;
    private int numOfColumns;

    public Auditorium(Seat[][] guide,int auditoriumNumber)
    {
        //Assign the head to the very first element in the guide
        this.guide = guide;
        this.head = new Node<>(guide[0][0]);
        this.auditoriumNumber = auditoriumNumber;
        //set current Node pointer to point to head
        Node <Seat> currentNode = head;
        //Declare but dont initialize newNode
        Node <Seat> newNode;
        
        //Loop through the first row
        for(int i = 1; i < guide[0].length; i++)
        {
            //New nodes in the first row will be the objects remaining in the row
            newNode = new Node<>(guide[0][i]);
            //Make current node and new node "shake hands"
            currentNode.setRight(newNode);
            newNode.setLeft(currentNode);
            //Set currentNode over to newNode
            currentNode = newNode;
        }
        
        //Set pointer back to head node to be able to point down
        currentNode = head;
        //Initialize 2 new pointers to keep track of begining of top row and topNode
        Node<Seat> topNode = currentNode;
        Node<Seat> beginingNode = topNode;
        
        //Two nested loops to construct the remaining rows below the first row
        for(int i = 1;i < guide.length; i++)
        {
            for(int j = 0;j < guide[i].length; j++)
            {
                //Check to see if pointers are on the farthest left
                if(topNode.getLeft() == null)
                {
                    newNode = new Node<>(guide[i][j]);
                    //Connect up and down
                    topNode.setDown(newNode);
                    newNode.setUp(topNode);
                    //Change the currentNode pointer down to newNode
                    currentNode = newNode;
                    //Begining of row will move down to current Node in new row
                    beginingNode = currentNode;
                    //Move over topNode to the right
                    topNode = topNode.getRight();
                }
                //Check if pointers are neither in farthest left or right
                else if(topNode.getLeft() != null && topNode.getRight() != null)
                {
                    newNode = new Node<>(guide[i][j]);
                    //Connect up and down
                    //topNode.down = newNode;
                    topNode.setDown(newNode);
                    //newNode.up = topNode;
                    newNode.setUp(topNode);
                    //Connect left and right
                    currentNode.setRight(newNode);
                    newNode.setLeft(currentNode);
                    //Move pointers to the right both top and bottom
                    currentNode = newNode;
                    topNode = topNode.getRight();
                }
                //Check to see if we are in the farthest right column
                else if(topNode.getRight() == null){
                    newNode = new Node<>(guide[i][j]);
                    //Connect up and down 
                    topNode.setDown(newNode);
                    newNode.setUp(topNode);
                    //Connect right and left
                    currentNode.setRight(newNode);
                    newNode.setLeft(currentNode);
                    //Move current to newNode
                    currentNode = newNode;
                }
            }
            //Top node should now start at the begining of the previous row
            topNode = beginingNode;
        }
    }
    /**
     * Method to get the number of rows inside the theater
     * @param Nothing
     * @return Integer of size of rows
     */
    public int getNumOfRows(){return this.guide.length;}
    /**
     * Method will get the number of columns
     * @param Nothing
     * @return Integer of size of the columns 
     */
    public int getNumOfColumns(){return this.guide[0].length;}
    /**
     * Methods to set auditorium numbers and get the auditorium numbers.
     * @param Nothing for the getter, int for the setter
     * @return Nothing for the setter, int for the getter
     */
    public void setAuditoriumNumber(int number){
    this.auditoriumNumber = number;}
    /**
     * Method will get the auditorium number
     * @param Nothing
     * @return The auditorium number 
     */
    public int getAuditoriumNumber(){
    return this.auditoriumNumber;}
    /**
     * Method will return the best seats in the theater.
     * @Param: Number of tickets requested, total number of columns and rows
     * @Returns: Starting seat object closest to the middle of the auditorium
     */
    public Object bestSeats(int ticketsRequested){
        //Create three pointers to go through the whole auditorium
        Node <Seat> firstSeat = head;
        Node <Seat> columnPointer = head;
        Node <Seat> rowPointer = head;
        int numOfRows = this.guide.length;
        int numOfColumns = this.guide[0].length;
        //Declare local variables
        int counterOfOpenSeats = 0;
        String possibleColumns = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Variables to hold the middle of the auditorium
        float centerX = (float) ((numOfColumns + 1) * .5);
        float centerY = (float)((numOfRows + 1) * .5);
       float distanceFromMiddle = 0.0f;
        
        //Create an array list to hold the seat objects 
        ArrayList <Seat> listOfSeats = new ArrayList<Seat>();
        
        //This pointer will move from head down to null
        while(rowPointer != null)
        {
            //This pointer will move to the right until null
            while(columnPointer != null)
            {
               //If the object has a period as its character
                if(columnPointer.getPayLoad().getTicketType() == '.')
                {
                    //Keep track of how many seats are periods
                    counterOfOpenSeats++;
                    //If only one has been found and the firstSeat variable is not where columnPointer is
                    if(counterOfOpenSeats == 1 && firstSeat != columnPointer)
                    {
                        //Assign the first seat in the sequence to the current seat being checked
                         firstSeat = columnPointer;
                     }
                     //If an equal number of seats have been found equal to number of seats requested then save the first seat
                    if(counterOfOpenSeats == ticketsRequested)
                    {
                        //Create a new seat object with the same attributes as the payload in the Node
                        Seat tempSeat = new Seat(firstSeat.getPayLoad().getRow(),firstSeat.getPayLoad().getColumn(),firstSeat.getPayLoad().getTicketType());
                        //Declare variables needed to calculate distance
                        int indexOfSeat = possibleColumns.indexOf(tempSeat.getColumn());
                        float xOfSeat = indexOfSeat + (float)((ticketsRequested - 1)*.5);
                        float yOfSeat = tempSeat.getRow();
                        //Calculate the distance to the middle of the auditorium
                        distanceFromMiddle = (float) Math.sqrt(Math.pow((centerX - xOfSeat),2)+Math.pow((centerY - yOfSeat),2));
                  
                        //Set the distance
                        tempSeat.setDistance(distanceFromMiddle);
                        //Save the seat in the list
                        listOfSeats.add(tempSeat);
                        //Reset the counter of open seats
                        counterOfOpenSeats = 0;
                        //Move first seat to right
                        firstSeat = firstSeat.getRight();
                        //Move columnPointer back to first seat 
                        columnPointer = firstSeat;
                    }
                    else{
                        //If the counter and tickets requested dont equal to each other then keep checking right
                        columnPointer = columnPointer.getRight();
                     }
                }
                else if(columnPointer.getPayLoad().getTicketType() != '.')
                {
                    //If the character is not a period then reset the counter of seats checked back to 0
                    counterOfOpenSeats = 0;
                    //Move the pointers to the right
                    columnPointer = columnPointer.getRight();
                    firstSeat = columnPointer;
                }
             
            }
            //Move down
            rowPointer = rowPointer.getDown(); 
            //Set other two pointers to the row pointer
             columnPointer = rowPointer;
            firstSeat = rowPointer;
           
        }
        
            //Seat pointer for closest seat points to nothing until a closest seat is determined
            Seat closestSeat = null;
        
            if(listOfSeats.isEmpty())
            {return null;}
            //Check the whole array list and compare their distances
            for(Seat seats : listOfSeats)
            {
                //If the pointer is pointing to nothing set it to the first seat object in the array list
                if(closestSeat == null)
                {
                    //Assume the first seat has the smallest distance
                    closestSeat = seats;
                }
                else if(seats.getDistance() < closestSeat.getDistance())
                {
                    //If the subsequent seat has a smaller distance then closestSeat becomes that seat 
                    closestSeat = seats;
                }
                else if(seats.getDistance() == closestSeat.getDistance())
                {
                    if(closestSeat.getRow() < seats.getRow()){
                    //Do nothing
                    }
                    else if (closestSeat.getRow()>seats.getRow())
                    {
                        closestSeat = seats;
                    }
                }
                //If there is a tie and one of the two seats is in the middle row then that seat wins
                if(seats.getRow() == centerY && seats.getDistance() == closestSeat.getDistance()){
                    
                    closestSeat = seats;
                }
            }
            //Return the starting seat of the closest set of seats
            return closestSeat;
    }
    /**
     * Method to unreserve one seat at a time
     * @param Row and column of the seat being unreserved
     * @return Character that was on the seat object 
     */
    public char unreserveIndividualSeat(int row, char column){
        Node <Seat> rowPointer = head;
        Node <Seat> columnPointer = head;
        while (rowPointer != null )
            {
                //If correct row is found then exxit loop
                if(rowPointer.getPayLoad().getRow() == row)
                {
                    //Change the pointer
                    columnPointer = rowPointer;
                    //Exit the loop
                    break;
                }
                //Move both column and row pointer down to next row
                rowPointer = rowPointer.getDown();
                columnPointer = rowPointer;
            }
            
        while(columnPointer != null){
                    //If correct column pointer is found then exit the loop
                    if(columnPointer.getPayLoad().getColumn() == column)
                    {
                        //Exit the loop
                        break;
            
                    }
                    columnPointer = columnPointer.getRight();
                    
                }
         //Get the letter in the seat object 
         char letter = columnPointer.getPayLoad().getTicketType();     
         columnPointer.getPayLoad().changeTicketType('.');   
         return letter;
    }
    /**
     * Method to unreserve multiple seats
     * @param List of seats 
     * @return Nothing 
     */
    public void unreserveSeats(List<String> listOfSeats)
    {//Create the pointers to land on the correct seat object inside the node
        Node <Seat> rowPointer = head;
        Node <Seat> columnPointer = head;
        //Loop through the list of seats and unreserve every single one 
        for(String a: listOfSeats){
            //Minus 48 to land on the correct integer 
            int row = Integer.valueOf(a.charAt(0)) - 48;
            char column = a.charAt(1);
            //Loop to move down and look for correct row. Make column pointer follow row pointer
            while (rowPointer != null )
            {
                //If correct row is found then exxit loop
                if(rowPointer.getPayLoad().getRow() == row)
                {
                    //Change the pointer
                    columnPointer = rowPointer;
                    //Exit the loop
                    break;
                }
                //Move both column and row pointer down to next row
                rowPointer = rowPointer.getDown();
                columnPointer = rowPointer;
            }
    
            //Loop through the columns until correct letter is matched
            while(columnPointer != null){
                    //If correct column pointer is found then exit the loop
                    if(columnPointer.getPayLoad().getColumn() == column)
                    {
                        //Exit the loop
                        break;
            
                    }
                    columnPointer = columnPointer.getRight();
                }
          
                columnPointer.getPayLoad().changeTicketType('.');
        }
    }
    /**
     * Method will reserve seats in specified row.
     * @returns nothing
     * @param row number, column letter, adult tickets, child tickets, senior tickets
     */
    public void reserveSeats(int row,char column, int adultTickets, int childTickets, int seniorTickets)
    {
        //Create the pointers to land on the correct seat object inside the node
        Node <Seat> rowPointer = head;
        Node <Seat> columnPointer = head;
        //Keep track of how many seats were changed
        int seatsChanged = 1;
    
        //Loop to move down and look for correct row. Make column pointer follow row pointer
        while (rowPointer != null )
        {
         //If correct row is found then exxit loop
         if(rowPointer.getPayLoad().getRow() == row)
        {
            //Change the pointer
            columnPointer = rowPointer;
            //Exit the loop
            break;
        }
        //Move both column and row pointer down to next row
        rowPointer = rowPointer.getDown();
        columnPointer = rowPointer;
       }
    
       //Loop through the columns until correct letter is matched
       while(columnPointer != null){
           //If correct column pointer is found then exit the loop
           if(columnPointer.getPayLoad().getColumn() == column)
           {
               //Exit the loop
               break;
            
            }
           columnPointer = columnPointer.getRight();
        }
       
        //Create a third pointer to loop throgh the seats changing each one
        Node <Seat> seatChanger = columnPointer;
        
        //Change the adult tickets first as long as you dont hit null and the adult tickets have not been exceeded
        while(seatsChanged <= adultTickets && seatChanger != null)
        {
            //Changed the ticket type
            seatChanger.getPayLoad().changeTicketType('A');
            //Move to the right
            seatChanger = seatChanger.getRight();
            //update the seatsChanged
            seatsChanged++;
        }
        
        //Reset the counter 
        seatsChanged = 1;
        
        //Change the child tickets as long as you dont hit null and the child tickets have not been exceeded
        while(seatsChanged <= childTickets && seatChanger != null)
        {
            //Change the ticket type
            seatChanger.getPayLoad().changeTicketType('C');
            //Move over to the right
            seatChanger = seatChanger.getRight();
            //Update the seats changed
            seatsChanged++;
        }
        
        //Reset the counter
        seatsChanged = 1;
        
        //Change the senior tickets as long as you dont hit null and the senior tickets have not been exceeded
        while(seatsChanged <= seniorTickets && seatChanger != null)
        {
            //Change the ticket type
            seatChanger.getPayLoad().changeTicketType('S');
            //Move over to the right
            seatChanger = seatChanger.getRight();
            //Update the seats changed
            seatsChanged++;
        }
        
    }
        /**
     * Method will check if seats are available.
     * @returns boolean
     * @param row number, column letter, total tickets requested
     */
    public boolean checkAvailability(int row, char column, int ticketsRequested)
    {
        //Create pointers
        Node <Seat> rowPointer = head;
        Node <Seat> columnPointer = head;
        
        //Loop to move down and look for correct row. Make column pointer follow row pointer
        while (rowPointer != null )
        {
         //If correct row is found then exxit loop
         if(rowPointer.getPayLoad().getRow() == row)
        {
            //Update the pointer
            columnPointer = rowPointer;
            break;
        }
        //Move both column and row pointer down to next row
        rowPointer = rowPointer.getDown();
        columnPointer = rowPointer;
       }
    
       //Loop through the columns until correct letter is matched
       while(columnPointer != null){
           //If correct column pointer is found then exit the loop
           if(columnPointer.getPayLoad().getColumn() == column)
           {
               //Exit the loop
               break;
            
            }
           columnPointer = columnPointer.getRight();
        }
           
        //Create a third pointer to loop throught the seats checking the character
        //Will point to the place where column pointer landed
        Node <Seat> seatCheckerPointer = columnPointer;
        int seatsChecked = 0;
        
        while(seatCheckerPointer != null)
         {
            //If the seat being checked is not a period return false no more checking is required
             if(seatCheckerPointer.getPayLoad().getTicketType() != '.')
            {
                //The seats are not available
                return false;
                
            }
            else if (seatCheckerPointer.getPayLoad().getTicketType() == '.')
            {
                seatsChecked++;
                //Check if the total number of seats were found
                if(seatsChecked == ticketsRequested)
                {   
                    return true;
                }
            }
             //Update pointer going right
            seatCheckerPointer = seatCheckerPointer.getRight();
         }
         //By default assume the seats are not available 
         return false;}
            
    /**
     * Method to show me the objects stored in the linked list.
     * @Returns nothing
     * @Param Nothing.
     */
    public void showSeats(){
        //Two top pointers will point to head at the begining to show whole list
        Node <Seat> rowPointer = head;
        Node <Seat> columnPointer = head;
        //Declare local variables
        int rowIndicator = 1;
        int numberOfLettersNeeded = 0;
        //Create a loop to check how many letters will need to be printed
        while(columnPointer != null)
        {
            numberOfLettersNeeded++;
            //columnPointer = columnPointer.right;
            columnPointer = columnPointer.getRight();
        }
        //Print out an empty space in between
        System.out.print("  ");
         //For loop to show the column letters.
        char firstLetter = 'A';
        for(int i = 0; i < numberOfLettersNeeded; i++ )
        {
            //Show the letter
            System.out.print(firstLetter);
            //Every subsequent letter after that incrementing by 1
            firstLetter++;
        }
        
        //Print out a new line character
        System.out.println("");
        //Reset the pointers
        rowPointer = head;
        columnPointer = head;
        
        while(rowPointer != null)
        {
            //Print out the row number
            System.out.print(rowIndicator + " ");
            while(columnPointer != null)
            {
                //If the character is not a period then print out a hashtag instead
                if(columnPointer.getPayLoad().getTicketType() != '.')
                {
                   System.out.print("#");
                }
                else{
                    System.out.print(".");
                }
                //Move pointer to the right
                columnPointer = columnPointer.getRight();
            }
            System.out.println("");
            //Increase the row indicator
            rowIndicator++;
            //Pointers move down to next row
            rowPointer = rowPointer.getDown();
            columnPointer = rowPointer;
        }
        
    }
    /**
     * Method to give access to the head of the linked list to print out to file 
     * and write report
     * @Returns the head pointer
     * @Param Nothing
     * 
     */
    public Node <Seat> getHeadNode(){
    
        return this.head;
    }
}
