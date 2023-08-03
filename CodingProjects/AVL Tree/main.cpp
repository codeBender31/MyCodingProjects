#include "avl.hpp"
using namespace std;


/* 
 * Main method, do not change
 */
int main(int argc, const char * argv[]) {
  AvlTree avltree;
  std::string filename = argv[1];
  freopen(filename.c_str(), "r", stdin);
  std::string input;
  int data;
  while(std::cin >> input){
    if (input == "insert"){
      std::cin>>data;
      avltree.insert(data);
    } else if(input == "delete"){
      std::cin>>data;
      avltree.remove(data);
    } else if(input == "print"){
      avltree.print();
      std::cout << "\n";
    } else{
      std::cout<<"Data not consistent in file";
      break;
    }
  }
  int tmp = avltree.height();
  cout << tmp << endl;
  avltree.print();
  return 0;
}
/*
 * END main method
 */
