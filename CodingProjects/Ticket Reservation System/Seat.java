//Abel Hernandez
//AXH-127530
/**
 * Seat will create an object representing a seat.
 *
 * @author Abel Hernandez
 * AXH-127530
 * @version 11/14/2020
 */
public class Seat
{
    //Declare all of the fields of the seat object
    private int row;
    private char column;
    private char ticketType; 
    private float distance = 0.0f;
    /**
     * Simple no arg constructor.
     */
    public Seat(){
    
    }
    /**
     * Overloaded Constructor.
     */
    public Seat(int row, char column, char ticketType)
    {
        this.row = row;
        this.column = column;
        this.ticketType = ticketType;
    }
    /**
     * Return the row number of the seat.
     * @Param Nothing
     * @Returns The row number of the object 
     */
    public int getRow()
    {
        return row;
    }
    /**
     * Return the seat column.
     * @Param Nothing
     * @Returns The column letter of the object.
     */
    public char getColumn()
    {   
        return column;
    }
    /**
     * Return the ticket type being held in the seat. 
     * @Param Nothing
     * @Returns The ticket type or character inside the object.
     */
    public char getTicketType()
    {
        return ticketType;
    }
    /**
     * Return the current distance to check which one goes first.
     * @Param Nothing
     * @Returns The distance to center of the auditorium
     */
    public float getDistance(){
        return distance;
    }
    /**
     * Set the distance from the middle of the auditorium.
     * @Param The calculated distance.
     * @Returns Nothing.
     * 
     */
    public void setDistance(float distance)
    {
        this.distance = distance;
    }
    /**
     * Change the ticketType being held by the object. 
     * @Param The new ticket type.
     * @Returns Nothing.
     */
    public void changeTicketType(char ticket)
    {   
        this.ticketType = ticket;
    }
}
