#include "stack.hpp"

using namespace std;

int main(){
  freopen("input_balanced.txt", "r", stdin);
  Stack<char> stack; //Create a stack of character type
  string s,r;
  int line_counter;
  while(cin >> r){
    cin>>s;
    Stack<char> stack;
    bool isBalanced = true;
    bool solution;
    if(r[0] == 'Y' || r[0] == 'y'){
      solution = true;
    }else{
      solution = false;
    }

    // The input file is in the format "expected_solution expression"
    // so variable solution tells you whether 'expression' is balanced or not

    for(int i=0; i<s.length(); ++i){
        if (s[i] == '[' || s[i] == '{' || s[i] == '(' ){
        stack.push(s[i]); //If stack is not empty and closing bracket was not found the push into stack
      }
      else if((s[i] == ']'&& stack.size() == 0) || (s[i] == '}'&& stack.size() == 0) || (s[i] == ')' && stack.size() == 0)) //If it is a closing symbol, check if stack is empty, if it is then error
      {
        isBalanced = false;
      
      }
      else if((stack.peek() != '[' && s[i] == ']') || 
        (stack.peek() != '{' && s[i] == '}') ||
        (stack.peek() != '(' && s[i] == ')'))
      {
        isBalanced = false;
        stack.pop();//delete top element meaning we found the closing bracket
      } 
      else if((stack.peek() == '[' && s[i] == ']') || 
        (stack.peek() == '{' && s[i] == '}') ||
        (stack.peek() == '(' && s[i] == ')')){
        stack.pop();
      }
      // WRITE CODE HERE so that isBalanced indicates whether 's' is balanced
  }
 
    // checking if you stored in isBalanced the correct value
    if(isBalanced == solution){
      cout << "line " << line_counter << ": OK [" << solution << " " << isBalanced << "]" << endl;
    }else{
      cout << "line " << line_counter << ": ERROR [" << solution << " " << isBalanced << "]" << endl;
    }
    line_counter++;
  }
}
