#include "stack.hpp"

using namespace std;

template <class T> void Stack<T>::push(T c) {
  if (topIndex == MAXSIZE - 1) { // Check if the stack is about to get full
    cout << "Stack is full" << endl;

  } else {
    topIndex++;        // Increase top index by 1
    arr[topIndex] = c; // New top index points to new element
  }
  // define the push methosd here
}

template <class T> T Stack<T>::pop() {
  if (topIndex ==
      -1) { // Check if stack is empty, meaning nothing can be popped
    cout << "Stack is empty" << endl;
    return -1;
  } else {
    T c = arr[topIndex]; // return value will equal to top index
    topIndex--;          // decrease topIndex counter by 1
    return c;            // return top value
  }
  // define the pop method here
}

template <class T> T Stack<T>::peek() {
  if (topIndex < 0) {
    cout << "Cannot peek. Stack empty." << endl;
    return -1;
  }
  return arr[topIndex];
}

template <class T> int Stack<T>::size() { return topIndex + 1; }

template <class T> void Stack<T>::display() {
  for (int i = topIndex; i >= 0; --i) {
    cout << arr[i] << "\t";
  }
  cout << endl;
}

template class Stack<char>;
template class Stack<int>;
