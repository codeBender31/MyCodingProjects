#include "avl.hpp"
using namespace std;

static const int BALANCE = 1;

bool AvlTree::contains(AvlNode * N, int x){
	if(N == NULL){
		return false;
	}
	else if(x < N->element){
		return contains(N->left, x);
	}
	else if(x > N->element){
		return contains(N->right, x);
	}
	else{
		return true;
	}
}

AvlNode* AvlTree::findMin(AvlNode* N){
	if(N == NULL){
		return NULL;
	}	
	if(N->left == NULL){
		return N;
	}
	return findMin(N->left);
}

AvlNode* AvlTree::findMax(AvlNode* N){
	if(N == NULL){
		return NULL;
	}	
	if(N->right == NULL){
		return N;
	}
	return findMax(N->right);
}

/**
 * Internal method to insert into a subtree.
 * x is the item to insert.
 * N is the node that roots the subtree.
 * Set the new root of the subtree.
 */
AvlNode* AvlTree::insert(AvlNode *& N, int x){
if(N == NULL)
{  N = new AvlNode;
  N->element = x;
  N->left = N->right = NULL;
  return N;
}
else if(x < N->element)//Less than then search to the left
{
 N->left = insert(N->left,x);
  balance(N); //Call balance function
}
else if (x > N->element) //Greater than then search to the right
{
  N->right = insert(N->right,x);
  balance(N); //Call balance function
}
  return N;
}

/**
 * Internal method to remove from a subtree.
 * x is the item to remove.
 * N is the node that roots the subtree.
 * Set the new root of the subtree.
 */
AvlNode* AvlTree::remove(AvlNode *& N, int x){
if (N == NULL) //If tree is empty return pointer 
{
  return N;
}
if(x < N->element) //If item deleting is less than current node element look left 
{
  N->left = remove(N->left,x); //Keep searching
}
else if(x > N->element) //If item deleting is greater than current node element look right
{
  N->right = remove(N->right,x);
}
else{ //If found consider 3 cases 
  if((N->left == NULL) && (N->right == NULL))// If Node has no children
  {
    return NULL;
  }
  else if(N->left == NULL)//Node has one or no children
  {
    struct AvlNode* temporary = N->right; //Point to right 
    free(N);
    balance(temporary);
    return temporary;
  }
  else if(N->right == NULL)// Same condition 
  {
   struct AvlNode* temporary = N->left; //Point left 
   free(N);
    balance(temporary);
   return temporary;
  }
  //If node has two children,get smallest in right subtree
  struct AvlNode* temporary = findMin(N->right);
  N->element = temporary->element;
  N->right = remove(N->right,temporary->element);
  
}
  balance(N);
  return N; // Return N before returning
}

/**
 * Returns the height of the node root or -1 if nullptr 
 */
int AvlTree::height(AvlNode * N){
	return N == NULL ? -1 : N->height;
}
//rebalancing the tree by performing appropriate 
//rotation on the subtree rooted with N
void AvlTree::balance(AvlNode *& N){
if(N == NULL){return;}//If t is null, return
if(height(N->left)-height(N->right) > 1)//Case 1 and 4
{
  if(height(N->left->left)> height(N->left->right))
  {
    rotateWithLeftChild(N);//Case 1
  }
  else{doubleLeftChild(N);} //Case 4
}
  else if(height(N->right) - height(N->left)>1)//Case 2 amd 3
  {
    if(height(N->right->right) > height(N->right->left))
    {
      rotateWithRightChild(N); //Case 2
    }
    else{doubleRightChild(N);}//Case 3
  }
  //Update the height
  N->height = max(height(N->left),height(N->right))+1;
}
//right rotation
void AvlTree::rotateWithLeftChild(AvlNode *& k2){

  AvlNode *k1 = k2->left;
  k2->left = k1->right;
  k1->right = k2;
  k2->height = max(height(k2->left), height(k2->right) ) + 1;
  k1->height = max(height(k1->left), k2->height ) + 1;
  k2 = k1;
}
//left rotation
void AvlTree::rotateWithRightChild(AvlNode *& k2){

  AvlNode *k1 = k2->right;
  k2->right = k1->left;
  k1->left = k2;
  k2->height = max(height(k2->right), height(k2->left) ) + 1;
  k1->height = max(height(k1->right), k2->height ) + 1;
  k2 = k1;
}
//left-right rotation
void AvlTree::doubleLeftChild(AvlNode *& k3){

  rotateWithRightChild(k3->left);
  rotateWithLeftChild(k3);
}
//right-left rotation
void AvlTree::doubleRightChild(AvlNode *& k3){

  rotateWithLeftChild(k3->right);
  rotateWithRightChild(k3);
}

void AvlTree::print(AvlNode *N, int level, int type){
        //cout << "printing N: " << N << endl;
  	if (N == NULL) {
                cout << "test!" << endl;
    		return;
  	}
  	if (type == IS_ROOT) {
    		std::cout << N -> element << "\n";
  	} else {
    		for (int i = 1; i < level; i++) {
      			std::cout << "| ";
    		}
    		if (type == IS_LEFT) {
      			std::cout << "|l_" << N -> element << "\n";
    		} else {
      			std::cout << "|r_" << N -> element << "\n";
    		}
  	}
  	if (N -> left != NULL) {
    		print(N -> left, level + 1, IS_LEFT);
  	}
  	if (N -> right != NULL) {
    		print(N -> right, level + 1, IS_RIGHT);
  	}
}
