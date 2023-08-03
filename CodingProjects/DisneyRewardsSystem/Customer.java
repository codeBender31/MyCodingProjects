 //@Author: Abel Hernandez
 //@AXH-127530
/**
 * Customer parent class will help Gold and Silver set its own fields.
 *
 * @author Abel Hernandez
 * @version 9/11/2020
 */
public class Customer
{
    // instance variables - replace the example below with your own;
    protected String guestId, firstName, lastName; 
    protected float amountSpent = 0.00f;
    
    /**
     * Constructor for objects of class Customer.
     * It will be used as the super constructor. 
     */
    public Customer(String guestId, String firstName, String lastName, float amountSpent)
    {
        // initialise instance variables
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountSpent = amountSpent;
        
       
    }
    
    /**
     * A getter method that lets the object access and show its customer ID
     * 
     */
    public String getID()
    {
        //Return the objects id number
        return guestId;
    }
    
    /**
     * A getter method that lets the object access and show its first name
     */
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * A getter method that lets the object access its last name 
     */
    public String getLastName()
    {
        return lastName;
    }
    
    /**
     * A get method that lets the object return the amountSpent
     */
    public float getOriginalAmountSpent(){
    
    return amountSpent;
    }
    
    /**
     * A setter method will set new and permanent amount spent after processing order.
     */
    public void setNewAmountSpent(float amountSpent)
    {
    
        this.amountSpent = amountSpent;
    }
   
    /**
     * A mutator method that will use the previous amount spent and add it to the order total.
     * It will just need to take in the order total.
     */
    public void calculateNewAmountSpent(float orderTotal)
    {
        this.amountSpent = amountSpent + orderTotal;
    }
    
        /**
     * Specify in the toString() format I would like the object to show its fields.
     */
    public String toString()
    {
        return guestId + " " + firstName + " " + lastName + " " + String.format("%.2f",amountSpent);
    }
    
}
