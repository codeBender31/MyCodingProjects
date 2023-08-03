These assignments used the Stacks data structures in different ways. 

Balancing:
This main function uses a stack object of the stack class to check if a statement is balanced or not balanced.
Scans through the whole input expression and stores each token into the stack, pops the stack if closing bracket is found.
If the stack is empty or if the corresponding bracket is not found, then it is deemed unbalanced. 
Otherwise, it is deemed balanced. 
Postfix Evaluation:
This main function uses a stack object of the stack class to evaluate the expression.
Scans for operands and operators, if it is an operand it is pushed into the stack.
If it is an operator, then the last 2 operands are popped, and the corresponding operation is evaluated. The result is pushed back into the stack.  
Once there are no more tokens in the input then the result pops from the stack. 
Infix to Postfix conversion:
This main function uses a stack object of the stack class to convert infix to postfix expression. 
Scans for operands (a -z or A-Z) and operators. 
If it is an operand it is concatenated into the final expression.
If it is an opening parenthesis it is pushed into the stack.
If it is a closed parenthesis, then the stack is popped until an opening parenthesis is found. 
Otherwise, if it is an operator a more conditions are checked. 
Once the end of the input is reached, all remaining tokens are popped from the stack and concatenated to the final expression. 
To compile, run 
$ g++ stack.cpp balancing.cpp 
$ g++ stack.cpp postfixEval.cpp 
$ g++ stack.cpp infixtopostfix.cpp 
• To run each program, run $ ./a.out
