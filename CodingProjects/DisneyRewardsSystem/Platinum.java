 //@Author: Abel Hernandez
 //@AXH-127530
/**
 * Platinum class will extend customer but have exclusive fields of its own as well as specific methods 
 *
 * @author Abel Hernandez
 * @version 9/11/2020
 */
public class Platinum extends Customer
{
    // instance variables - replace the example below with your own
    protected int bonusBucks = 0;

    /**
     * Constructor for objects of class PreferredCustomerPlatinum
     */
    public Platinum(String guestId, String firstName, String lastName, float amountSpent, float bonusBucks)
    {
        // initialise instance variables
        super(guestId, firstName,lastName, amountSpent);
        this.bonusBucks = (int) bonusBucks;
    }
    
 
     /**
     * A mutator method that will use the previous amount spent and add it to the order total minus the current bonus bucks.
     * It will just need to take in the order total.
     */
    public void calculateNewAmountSpent(float orderTotal)
    {
      
         this.amountSpent = amountSpent + (orderTotal - bonusBucks);
    
        //If non zero bonus bucks were used in the total then subtract them from the field to set it to 0
            this.bonusBucks -= bonusBucks; //bonusBucks*0; 
    }
    
   /**
    * Method returns the bonus bucks the object can use in calculating.
    * 
    */
   public int getBonusBucks()
   {
       return bonusBucks;
    }
    
    /**
     * Method will set the new Bonus Bucks if a new object is cast and needs to initialize the bonus bucks.
     * 
     */
    public void setBonusBucks(float bonusBucks)
    {
        //If we have an error we fix it here 
        this.bonusBucks += (int)bonusBucks;
    
    }
    
          /**
     * Specify in the toString() format I would like the object to show its fields.
     */
    public String toString()
    {
    
        return guestId + " " + firstName + " " + lastName + " " + String.format("%.2f",amountSpent) + " " + bonusBucks;
    }
   
}
