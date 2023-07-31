//Abel Hernandez
//AXH-127530
/**
 * Node will have multiple pointers and hold the payload. 
 *
 * @author Abel Hernandez
 * AXH-127530
 * @version 11/14/2020
 */
public class Node<E>
{
    //Declare the directional pointers
    private Node<E> right;
    private Node<E> left;
    private Node<E> up;
    private Node<E> down;
    //Declare the payload that will be held in the Node
    private E payLoad;
    /**
     * No arg constructor.
     * Will initialize the directions to null.
     */
    public Node(){
        this.right = null;
        this.left = null;
        this.up = null;
        this.down = null;
     }
    /**
     * Overloaded Constructor.
     * Will also initialize the directions to null.
     */
    public Node (E payLoad)
    {
        this.payLoad = payLoad;
        this.right = null;
        this.left = null;
        this.up = null;
        this.down = null;
    }
    /**
     * The getter to return the object being held in the Node.
     */
    public E getPayLoad()
    {
        return payLoad;
    }
    /**
     * Setter methods that will set the Node that current Node points to.
     * @Param Node pointer to set to
     * @Returns Nothing
     */
    public void setRight(Node <E> pointer)
    {
        this.right = pointer;
    }
    public void setLeft(Node <E> pointer)
    {
         this.left = pointer;   
    }
    public void setUp(Node <E> pointer)
    {
        this.up = pointer;
    }
    public void setDown(Node <E> pointer)
    {
        this.down = pointer;
    }
    /**
     * Getters to determine what Nodes the current node is pointing to.
     * @Param Nothing
     * @Returns Node it points to in set direction
     */
    public Node <E> getRight(){
        return this.right;
    }
    public Node <E> getLeft(){
        return this.left;
    }
    public Node <E> getUp(){
        return this.up;
    }
    public Node <E> getDown()
    {
        return this.down;
    }
}
