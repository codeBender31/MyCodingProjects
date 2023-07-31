 //Abel Hernandez
//AXH-127530
/**
*BSTree will create a binary search tree to store data.
*@author Abel Hernandez
*AXH-127530
*@version 10/16/2020
*/
public class BSTree<E extends Comparable<E>>
{   
//Declare variables
private Node <E> root;
/**
*No arg constructor, creates and empty tree object.
*@param None.
*@return Nothing.
*/
public BSTree(){
    root = null;
}
/**
*Overloaded constructor.
*@param Takes in a generic value to assign it to the root.
*@returns nothing
*/
public BSTree(Node <E> firstNode){
    //Set root equal to first value given
    root = firstNode;
}
/**
 * Helper function will insert a new node into the tree.
 * @param Node <E>
 * @return nothing
 */
public void add(Node <E> insertionNode)
{
    root = insert(root,insertionNode);
}
/**
 * Function will insert new node in the correct place.
 * @param Current Node
 * @return Current Node
 */
private Node <E> insert(Node <E> currentNode,Node <E> insertionNode){
//If the tree is empty, store the node as the root of the tree
if(currentNode == null){
    return root = insertionNode; 
}
//If the insertion node is smaller than the current node then go left
if (insertionNode.compareTo(currentNode) < 0) 
      {currentNode.setLeft(insert(currentNode.getLeft(), insertionNode));}
//Otherwise go right
else if (insertionNode.compareTo(currentNode) > 0){
   currentNode.setRight(insert(currentNode.getRight(), insertionNode));}
//If its neither smaller or greater than then return the current node and set that as the right or left child
else{
      return currentNode; }
return currentNode;
}
/**
 * Method will search to see if node exists in the tree.
 * @param Generic Node being checked.
 * @return boolean
 */
public Node <E> search(Node <E> searchNode)
{   //Call main function to search and pass in the root along with the node being searched 
    return searchNodes(root,searchNode);}
private Node <E> searchNodes(Node <E> currentNode, Node <E> searchNode)
{
 //If the current node is not pointing to null then continue
 if(currentNode != null)
 {
     //Go left if less than 
    if(searchNode.compareTo(currentNode) < 0)
    {
        return searchNodes(currentNode.getLeft(),searchNode);
    } //Go right if greater than 
    else if(searchNode.compareTo(currentNode) > 0)
    {   return searchNodes(currentNode.getRight(),searchNode);
    }//Otherwise return the current node.
    else{
        return currentNode;
    }
}    
//Return null by default
return null;
}
/**
 * Method to obtain the parent of Child Node
 * @param Child Node
 * @return Root of tree and Child Node
 */
private Node <E> getParent(Node <E> childNode){
return getParentRecursive(root,childNode);}
/**
 * Method uses Get Parent function return nodes to search for parent.
 * @param Root of tree and Child Node
 * @return Parent Node
 */
private Node <E> getParentRecursive(Node <E> root, Node <E> childNode)
{   //If the root is empty then return null
 if(root == null)
{return null;}
//If the child Node is the left or right child of root then parent Node is the root, return root
if(root.getLeft() == childNode || root.getRight() == childNode)
{return root;}
//Otherwise if the child node is smaller than root's left nodes keep calling recursively until correct parent is found
if(childNode.compareTo(root)<0)
{return getParentRecursive(root.getLeft(),childNode);}
//Otherwise if it is bigger then look to the right until correct parent is found
return getParentRecursive(root.getRight(),childNode);
}
/**
 * Methods will delete Nodes recursively.
 * @param Deleting Node
 * @return boolean
 */
public boolean delete(Node <E> deletingNode){
Node <E> parentNode = getParent(deletingNode);
return deleteRecursive(parentNode,deletingNode);}
private boolean deleteRecursive(Node <E> parentNode,Node <E> deletingNode){
    //If deleting node is non existent then return null
    if(deletingNode == null)
    {return false;}
    //Internal node with 2 children
    if(deletingNode.getLeft() != null && deletingNode.getRight() != null)
    {
    Node <E> succesor = deletingNode.getRight();
    Node <E> succesorParent = deletingNode;
    while(succesor.getLeft() != null)
    {succesorParent = succesor;
     succesor = succesor.getLeft();
    }
    //Copy the value from sucessor
    deletingNode.setPayLoad(succesor.getPayLoad());
    //Call deleteRecursive again
    return deleteRecursive(succesorParent,succesor);
   }
    //Root node with 1 or 0 children
    else if(deletingNode == root){
        if(deletingNode.getLeft() != null)
        {   root = deletingNode.getLeft();
        }
        else
        {   root = deletingNode.getRight();   
        }
    }
    else if(deletingNode.getLeft() != null){
        if(parentNode.getLeft() == deletingNode){
        parentNode.setLeft(deletingNode.getLeft());}
        else
        {parentNode.setRight(deletingNode.getLeft());}
    }
    //Internal with right child only or leaf
    else{
         if(parentNode.getLeft() == deletingNode)
         {parentNode.setLeft(deletingNode.getRight());}
         else{parentNode.setRight(deletingNode.getRight());}
    }
    return true;
}
/**
 * Method will take in node and return its smallest left value.
 * @param Current node set to root initially
 * @return Minimum Node
 */
public Node <E> getMinimum(Node <E> currentNode)
{
   //If null is encountered then return the current node
    if(currentNode.getLeft() == null)
   {return currentNode;}
     //Keep moving left until you null
   return getMinimum(currentNode.getLeft());}
/**
 * Method will print Binary Search Tree contents in order.
 * @param root of the tree
 * @return nothing
 */
public void inOrderPrint(Node <E> root){
    //If the root is null then return 
    if(root == null)
    {return;}
    //Go all the way to the farthest left until you cant anymore
    inOrderPrint(root.getLeft());
    //Print Node contents
    System.out.println(root.getPayLoad());
    //Go all the way to the right
    inOrderPrint(root.getRight());
}
/**
 * Method will be getter for root. To see if root is pointing to null.
 * @param nothing
 * @return The root of the tree.
 */
public Node <E> getRoot(){
    return this.root;}
}