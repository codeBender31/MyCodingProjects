#include "stack.hpp"

using namespace std;

// Auxiliary method, you probably find it useful
// Operands are all lower case and upper case characters
bool isOperand(char c){
  return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}

// Auxiliary method, you probably find it useful
int precedence(char c)
{
  if(c == '+' || c == '-'){
    return 0;
  }
  if(c == '*' || c == '/'){
    return 1;
  }
  if(c == '^'){
    return 2;
  }
  return -1;
}

int main(){
  freopen("input_infix2postfix.txt", "r", stdin);
  string input;
  string solution;
  int line_counter = 0;
  while(cin >> solution){
    cin >> input;
    Stack<char> stack;
    string result;

     //The input file is in the format "expected_solution infix_expression", 
     //where expected_solution is the infix_expression in postfix format

    for(int i=0; i<input.length(); ++i){
     if(isOperand(input[i])) //Checks if token is Operand or Operator
     {
       result += input[i];
     }
    else if (input[i] == '(') //If open parenthesis push on stack
    {
      stack.push(input[i]);
    }
    else if (input[i] == ')')//If closed parenthesis pop til opening parenthesis found
    {
      while(stack.size() != 0 && stack.peek() != '(')
        {result += stack.pop();}
      stack.pop();//pop again to pop opening parenthesis
      
    }
    else if (input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/' 
      || input[i] == '^') //If next token a operator
    {
      if(stack.size() != 0){ //If stack is not empty check precedence
          if(precedence(input[i]) > precedence(stack.peek()) || stack.peek() == '(')
          {stack.push(input[i]);} //Push on to stack if greater precedence or open parenthesis
          else{
            //Loop until less precedence is found
            while(stack.size() != 0 && precedence(input[i]) <= precedence(stack.peek()))
            { result += stack.pop();} //Keep popping 
            stack.push(input[i]); //Push scanned operator at the end of loop 
          }
      }
      else{
        stack.push(input[i]);//push operator into stack if stack is empty
      }
      
    }
      // WRITE CODE HERE to store in 'result' the postfix transformation of 'input'
    }
    
    // You need to do some extra stuff here to store in 'result' the postfix transformation of 'input'
    while(stack.size() != 0)
      {
        if(stack.peek() != '(' && stack.peek() != ')'){
        result += stack.pop();
          }
        else if (stack.size() != 0){stack.pop();}
      }
    
    // Checking whether the result you got is correct
    if(solution == result){
      cout << "line " << line_counter << ": OK [" << solution << " " << result << "]" << endl;
    }else{
      cout << "line " << line_counter << ": ERROR [" << solution << " " << result << "]" << endl;
    }
    line_counter++;
  }
}
