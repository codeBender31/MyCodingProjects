//Abel Hernandez
//AXH-127530
import java.lang.*;
import java.util.*;
import java.io.*;
/**
 * Main will implement a binary search tree to simulate a red box kiosk.
 * @Author: Abel Hernandez
 * @AXH-127530
 * @version 10/16/2020
 */

public class Main
{
    public static void main(String args[]) throws IOException
  {
    //Create a scanner object 
    Scanner kbd = new Scanner(System.in);
    //Validate the file and return the file object to give to the constructor 
    File file = getFile(kbd);
    //Create a scanner for the file
    Scanner reader = new Scanner(file);
    //Create instance of tree
    BSTree <DVD> tree = new BSTree <DVD> ();   
    //Get info from file to create DVD objects and store in Nodes to add to tree
        while(reader.hasNext())
        {   
       //Create variable to hold line
        String currentLine = reader.nextLine();
        String [] tokens = currentLine.split(",");
        String title = tokens[0];
        int available = Integer.parseInt(tokens[1]);
        int rented = Integer.parseInt(tokens[2]); 
        tree.add(new Node <> (new DVD(title,available,rented)));
       }
       //Close input file
       reader.close();
    //Get transaction file
    File transactionFile = getFile(kbd);
    //Create transaction reader
    Scanner transactionReader = new Scanner(transactionFile);
    //Create error log to hold invalid transactions 
    PrintWriter error = new PrintWriter("error.log");
    while(transactionReader.hasNext())
    {
        //Create strings to hold the contents of the line
        String [] input = new String [2];
        input[0] = transactionReader.next();
        input[1] = transactionReader.nextLine().trim();
        //If validation comes back true proceed
        if(invalidTransaction(input)){
         String action = input[0];
         String second = input[1];
        if(second.indexOf(",") != -1){
            //If a comma is present split it, otherwise dont do anything
            String [] tokens = second.split(",");
            String title = tokens[0];
            int amount = Integer.parseInt(tokens[1]);
            Node <DVD> tempNode = new Node <>(new DVD(title,amount,0));
            //This one will handle add and remove
            if(action.equals("add")){
                addMovies(tree,tempNode,amount);
            }
            else if (action.equals("remove"))
            {
                removeMovies(tree,tempNode,amount);
            }
        }
        else{
            //This one will handle rent and return
            Node <DVD> tempNode = new Node <>(new DVD(second,0,0));//Make temp Node
            //Check what action needs to be performed
            if(action.equals("rent"))
            {rentMovies(tree,tempNode);}
            else if(action.equals("return")){
                returnMovies(tree,tempNode);
            }
        }
        }
        else{
        //If validation came back false then write concatenated String to file
        error.println(input[0] + " " + input[1]);}
    }
    //Close the error log
    error.close();
    //Close the file
    transactionReader.close();
    //Show end results of content of tree
    tree.inOrderPrint(tree.getRoot());
    }
    /**
     * Method to validate the transaction log.
     * @param Action and rest of line concatenated
     * @return Boolean
     */
    public static boolean invalidTransaction(String [] transactionLine)
    {
    //Check action first if it is not any valid action then whole line is not valid
    if(!transactionLine[0].equals("add") && !transactionLine[0].equals("remove") && !transactionLine[0].equals("rent") && !transactionLine[0].equals("return"))
    {   return false;}
    //rent and return will be checked together
    if(transactionLine[0].equals("rent") || transactionLine[0].equals("return"))
    {   //Return and rent should not have a comma
        if(transactionLine[1].contains(",") == true)
        {return false;}
        //Check that the title starts and ends with qoutes
        if(!transactionLine[1].startsWith("\"") || !transactionLine[1].endsWith("\""))
        {return false;}
    }
    //add and remove will be checked together
    else if(transactionLine[0].equals("add") || transactionLine[0].equals("remove"))
    {   //Add and Remove should have a comma present
        if(transactionLine[1].contains(",") == false)
        {return false;}
        //Split the second string with comma
        String [] tokens = transactionLine[1].split(",");
        //Check length to see another string is after comma
        if(tokens.length != 2)
        {return false;}
        String title = tokens[0];
        if(!title.startsWith("\"") || !title.endsWith("\""))
        {return false;}
        //Check that second token is present and is a number
        try{
            int num = Integer.parseInt(tokens[1]);}
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    //Assume it is valid
    return true;}
    /**
     * Method to remove certain amount of copies and delete Titles when both rented and available are at 0
     * @param Tree Object,Node being searched and amount being removed
     * @return Nothing 
     */
    public static void removeMovies(BSTree <DVD> tree, Node <DVD> searchNode, int amount)
    {   //If null is not returned but rather the Node being searched for then modify the available amount 
        if(tree.search(searchNode) != null)
        {tree.search(searchNode).getPayLoad().addMovie((-1*amount));}
        //Check if available and rented are both at 0 then call delete and delete the node
        if(tree.search(searchNode).getPayLoad().getAvailableAmount() == 0 && tree.search(searchNode).getPayLoad().getRentedAmount() == 0)
        {Node <DVD> deletingNode = tree.search(searchNode);
         tree.delete(deletingNode);}
    }
    /**
     * Method to return movies to the kiosk
     * @param Tree Object, Node being searched
     * @return Nothing
     */
    public static void returnMovies(BSTree <DVD> tree, Node<DVD> searchNode)
    {   //If null is not returned but rather the Node being searched for then modify the available amount and the rented amount
        if(tree.search(searchNode) != null)
        {
         tree.search(searchNode).getPayLoad().addMovie(1);
         tree.search(searchNode).getPayLoad().rentMovie(-1);
        }

    }
    /**
     * Method to add movies to the kiosk
     * @param Tree object, Node being searched
     * @return Nothing
     */
    public static void addMovies(BSTree <DVD> tree,Node<DVD> searchNode,int ammount)
    {   //If null is not returned but rather the Node being searched for then modify the available amount 
        if(tree.search(searchNode) != null)
        {   tree.search(searchNode).getPayLoad().addMovie(ammount);
        }
        //Otherwise add the movie with its quantity
        else{
            tree.add(searchNode);}

    }
    /**
     * Method to rent a movie at the kiosk
     * @param Tree object, Node being searched
     * @return Nothing
     */
    public static void rentMovies(BSTree <DVD> tree,Node<DVD> searchNode){
         //If null is not returned but rather the Node being searched for then modify the available amount and the rented amount
        if(tree.search(searchNode) != null)
        {
            tree.search(searchNode).getPayLoad().rentMovie(1);
            tree.search(searchNode).getPayLoad().addMovie(-1);
        }

    }
   /**
    * Method to validate that the name of the file given is a valid file name.
    * @param Scanner object to take in string name.
    * @return file object
    */
   public static File getFile(Scanner kbd)
   {
    //File pointer pointing to nothing at first 
    File file = null;
    //Assume file does not exist and cannot be read
    boolean fileExists = false, fileRead = false;
    while(fileExists == false && fileRead == false)
    {
        //Prompt user for file name that will be used to seed the BST
        System.out.println("Please provide a valid file name.");
        String fileName = kbd.nextLine();
        try{
            //Attempt to create a file object with given name
            file = new File(fileName);
            //If file can be read and exists then exit the loop
            fileExists = file.exists();
            fileRead = file.canRead();
        }catch(Exception E)
        {
            //Do nothing and loop again
            continue;
        }
    }
    return file;}
}
