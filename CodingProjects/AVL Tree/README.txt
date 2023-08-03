1.For Avl Tree:
   To compile:
	g++ main.cpp avl.cpp
   To run:
	./a.out input1.txt
Insert: Method recursively looks for correct place to insert new Node if tree is already populated.
Remove: Finds the Node containing the correct key and checks 3 cases to either delete it or find its successor and then delete it. 
Balance: Takes in the root node or temporary node that is created in remove method and checks if tree is unbalanced. Meaning balance factor is not 0,-1, or 1. If it is then it does nothing and returns otherwise it conducts rotations until balanced. 
