//Abel Hernandez
//AXH-127530
/**
*DVD Class will represent a DVD and its available and rented copies.
*@author Abel Hernandez
*AXH-127530
*@version
*/
public class DVD implements Comparable <DVD>
{   
//Declare variables
private String title;
private int available;
private int rented;
/**
*No arg constructor.
*/
public DVD(){}
/**
*Overloaded constructor
*@param Movie title, available amount and rented amount
*/
public DVD(String title, int available, int rented){
this.title = title;
this.available = available;
this.rented = rented;
}
/**
 * Method will add more copies of the movie to the current counter.
 * @param Amount being added.
 * @return Nothing.
 */
public void addMovie(int amountBeingAdded)
{this.available += amountBeingAdded;}
/**
 * Method to increase the number of copies rented.
 * @param Amount rented.
 * @return Nothing.
 */
public void rentMovie(int rented)
{this.rented += rented;}
/**
 * Method will obtain the name of the DVD object.
 * @param Nothing.
 * @return Title of movie.
 */
public String getTitle(){return this.title;}
/**
 * Method to obtain number of copies available.
 * @param Nothing
 * @return The available amount of the DVD
 */
public int getAvailableAmount(){return this.available;} 
/**
 * Method to obtain the number of copies rented.
 * @param Nothing.
 * @return The rented amount of the DVD.
 */
public int getRentedAmount(){return this.rented;}
@Override
public String toString(){
    String cleanTitle = title.replace("\"","");
    return cleanTitle + " " + available + " " + rented;}
@Override
public int compareTo(DVD object)
{return this.getTitle().compareTo(object.getTitle());}
}