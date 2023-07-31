//Abel Hernandez
//AXH-127530
/**
*Generic Node will extend Comparable interface.
*@author Abel Hernandez
*AXH-127530
*@version 10/16/2020
*/
public class Node <E extends Comparable <E>>
{   
//Declare variables
private Node <E> right;
private Node <E> left;
private E payLoad;
/**
*No arg constructor.
*/
public Node () {}
    /**
*Overloaded constructor
*/
public Node(E payLoad){
this.payLoad = payLoad;
this.right = null;
this.left = null;}
/**
 * Setter methods for left and right directions.
 * @param Node that it will point to.
 * @return nothing
 */
public void setRight(Node <E> right)
{
    this.right = right;
}
public void setLeft(Node <E> left)
{
    this.left = left;
}
/**
 * Getter methods will return where the node points to next.
 * @param Nothing.
 * @return Direction it points to.
 */
public Node <E> getRight()
{   
    return this.right;
}
public Node <E> getLeft()
{
    return this.left;
}
/**
 * Getters for payload.
 * @return payload
 * @param nothing
 */
public E getPayLoad()
{
    return this.payLoad;
}
/**
 * Setter for payload.
 * @return PayLoad
 * @param Generic PayLoad
 */
public void setPayLoad(E payLoad)
{
    this.payLoad = payLoad;
}
/**
 * CompareTo Method will compare the objects inside the Nodes.
 * @param Another Node
 * @return integer
 */
public int compareTo(Node <E> otherNode){
return this.payLoad.compareTo(otherNode.payLoad);
}
}