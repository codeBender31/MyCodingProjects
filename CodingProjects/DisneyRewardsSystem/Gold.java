 //@Author: Abel Hernandez
 //@AXH-127530
/**
 * Write a description of class PreferredCustomer here.
 *
 * @author Abel Hernandez
 * @version 9/11/2020
 */
public class Gold extends Customer
{
    // instance variables 
    protected float discountPercentage = 0.00f;

    /**
     * Constructor for objects of class PreferredCustomer
     */
    public Gold(String guestId, String firstName, String lastName, float amountSpent, float discountPercentage)
    {
        // initialise instance variables
        super(guestId,firstName,lastName,amountSpent);
        this.discountPercentage = discountPercentage;
    }
    
    /**
     * Method to return the discount percentage so Main can calculate the new AmountSpent
     */
    public float getDiscount()
    {
        return discountPercentage;
    }
    
       /**
     * Set the discount percentage for new gold objects.
     */
    public void setNewDiscount(float newDiscountPercentage){
        this.discountPercentage = newDiscountPercentage;
    }
   
      /**
     * A mutator method that will use the previous amount spent and add it to the order total.
     * It will just need to take in the order total.
     */
    public void calculateNewAmountSpent(float orderTotal)
    {
        this.amountSpent = amountSpent + (orderTotal-(orderTotal*(discountPercentage/100)));
     
    }
   
    /**
     * Specify in toString how to show the object's fields.
     *
     */
    public String toString()
    {
        return guestId + " " + firstName + " " + lastName + " " + String.format("%.2f",amountSpent) + " " + (int)discountPercentage + "%";
    }
}
