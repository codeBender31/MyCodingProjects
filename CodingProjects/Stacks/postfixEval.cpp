#include "stack.hpp"

using namespace std;

int main(){
  freopen("input_postfixEval.txt", "r", stdin);
  string s;
  int solution;
  int line_counter = 0;
  while(cin>>solution){
    cin>>s;
    Stack<int> stack;
    // The input file is in the format "expected_solution postfix_expression"
    // We assume that all operands in the input are one digit (so they range from 0 to 9)
    for(int i=0; i<s.length(); ++i){
      if(isdigit(s[i]) && s[i] != ' ' )
      {
        stack.push(s[i] - '0'); //Push the token iff its a number between 0 an 9
      }
      else if(s[i] == '+' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue + rightValue);//Push the answer back in stack 
      
      }
      else if(s[i] == '-' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue - rightValue);//Push the answer back in stack 
      
      }
      else if(s[i] == '*' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue * rightValue);//Push the answer back in stack 
      
      }
      else if(s[i] == '/' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue / rightValue);//Push the answer back in stack 
      
      }
      else if(s[i] == '^' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue ^ rightValue);//Push the answer back in stack 
      
      }
      else if(s[i] == '%' && stack.size() > 0){
        int rightValue = stack.pop();
        int leftValue = stack.pop();
        stack.push(leftValue % rightValue);//Push the answer back in stack 
      
      } 
      // WRITE CODE HERE to evaluate the postfix expression in s
      // At the end of the loop, stack.pop() should contain the value of the postfix expression
    }

    // Checking whether the value you calculated is correct ...
    int value = stack.pop();

    if(solution == value){
      cout << "line " << line_counter << ": OK [" << solution << " " << value << "]" << endl;
    }else{
      cout << "line " << line_counter << ": ERROR [" << solution << " " << value << "]" << endl;
    }
    line_counter++;
  }
}
