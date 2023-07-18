/***********************************************************
  Member  : Student 1 and Student 2
  Synopsis: read from city.name and flight.txt
            store the graph in a adjacency matrix
***********************************************************/

#include <iostream>
#include <vector>
#include <ctime> 
#include <cstdlib>
#include <string>
#include <fstream>
#include <map>
#include <iomanip>
#include <climits>
#include <queue>

#include "minheap_pair.h"

using namespace std;

class Graph{
     public:
          Graph(int d);
          ~Graph(){};
	  int get(){return n;} //Return the number of nodes
	  int (* getG())[140]{return adj;}
//Change back to 140 after testing
	  
          void print();
          void addEdge(int node1, int node2){ adj[node1][node2] = 1; };
     private: 
          int n;
          int adj[140][140]; // You may want to use adjacent
//Change back to 140 instead of 10
//list implementation for sparse graph instead
};

Graph::Graph(int d){
    for (int i=0; i < d; i++){
        for (int j=0; j < d; j++){
            adj[i][j] = 0;
        }
    }
    n = d;
};
//Function prints graph and what each node points to 
void Graph::print(){
    for (int i=0; i < n; i++){
        cout << i << " -> ";
        for (int j=0; j < n; j++){
            if (adj[i][j]){
              //Assigns the number to the city ex 0 -> Moscow
                cout << j << " ";
            }
        }
        cout << endl;
    }

};

void routeSearch(Graph graph);
void routeSearch_1(Graph graph, int city_A, int city_B, int num_connection);
void routeSearch_2(Graph graph, int city_A, int city_B, int city_C, int city_D);
void routeSearch_3(Graph graph, int city_A, int city_B, int city_C);
void Dijkstra(Graph graph, int s, int d[], int p[]);


/*to map a city name with an unique integer */
//map<const string, int, strCmp> city;
map<const string, int> city;

int main(int argc, char *argv[]){
   int i,j, node1, node2,n;
   string line;


   //cout << "Please enter the number of cities in your graph: " << endl;
   //cout << "---------------------------------------------------" << endl;
   n = 140;
  //Change back to 140 after testing
   //cin >> n;
   Graph graph(n);
 
   char lineChar[256];

   ifstream cityFile;
   cityFile.open("city.name", ios::in);

   /*map a city name with an unique integer */
   for (i = 0; i < n; i++){
      std::getline(cityFile, line);
      line.erase(std::prev(line.end()));
      city[line] = i;
   }

   cityFile.close();

   ifstream flightFile;
   flightFile.open("flight.txt", ios::in);
  
   while (flightFile.getline(lineChar, 256)){
      // if line constains From: 
      line = lineChar;
      if (line.find("From:", 0) == 0 ){
          line.erase(0,7);
	  //printf("%s\n", line.c_str());
          if(city.find(line) != city.end()){
	  	//cout << "locate!" << line <<  line.length() << endl;
	  }
	  else{
	  	//cout << "NO!" << line <<  line.length() << endl;
		continue;
	  }
          node1 = city[line];
      } else {
	  line.erase(0,7);

          node2 = city[line];
          graph.addEdge(node1,node2);
      }
   }
   flightFile.close();
   
   /* print the graph */
   cout << endl << "The graph generated can be represented by the following adjacent matrix : " << endl;
   cout << "-----------------------------------------------------------------------------------" << endl;
  //Uncomment to show graph
   //graph.print(); 

   /*print the map representation of cities*/
   map<const string, int>::iterator it;  
   for(it = city.begin(); it != city.end(); it++){
	//cout << it->first << ": " << it->second << endl; 
     //Return output statement once done
     //Gives corresponding number to city name
     
   }

   routeSearch(graph); 
}

void routeSearch(Graph graph){
	int choice;
	cout << "Please choose the type of the questions: " << endl;
	cout << "1: From city 'A' to city 'B' with less than x connestions?" << endl;
	cout << "2: Route with the samllest number of connections from city 'A' to city 'D' through city 'B' and 'C'?" << endl;	
	cout << "3: Find intermediate city, I, makes the total number of connections (A to I, B to I and C to I) the smallest." << endl;	
	cin >> choice;


	int city_A, city_B, city_C, city_D;
	int num_connection;

  //Hard code options for now 
	switch (choice){
		case 1:
			cout << "Please enter the city A: ";
			cin >> city_A;
      

			cout << "Please enter the city B: ";
			cin >> city_B;
      

			cout << "number of connections: ";
			cin >> num_connection;

			routeSearch_1(graph, city_A, city_B, num_connection);
			break;	
		case 2:
			cout << "Please enter the city A: ";
			cin >> city_A;


			cout << "Please enter the city D: ";
			cin >> city_D;
 

			cout << "Please enter the city B: ";
			cin >> city_B;
    

			cout << "Please enter the city C: ";
			cin >> city_C;


			routeSearch_2(graph, city_A, city_B, city_C, city_D);
			break;	
		
		case 3:
			cout << "Please enter the city A: ";
			cin >> city_A;
			cout << "Please enter the city B: ";
			cin >> city_B;
			cout << "Please enter the city C: ";
			cin >> city_C;

			routeSearch_3(graph, city_A, city_B, city_C);
			break;
		
		default:
			cout << "incorrect choice" << endl;	
	}


}
//Dijkstra algorithm for finding the SSSP
void Dijkstra(Graph graph, int s, int d[], int p[]) {
  //Get the number of nodes equal to n
	int n = graph.get();
	int (*mat)[140] = graph.getG();
  //Change back to 140 after testing 
	for(int i = 0; i < graph.get(); i++){
    //Initialize the values
		d[i] = INT_MAX; //Values equal to infinity
		p[i] = -1;	//Values set to null or no parent 
	}
  //Distance of starting node is 0
	d[s] = 0;
	//create a minheap
	MinPriorityQueue pq;
  //Process the whole graph 
	for(int i = 0; i < n; i++){
    //Call queue to insert distance array and current node 
		pq.insert(d[i], i);
	}
  //While the priority queue is not empty, pop elements, nodes
	while(!pq.is_empty()){
    //Pop the mininum node
		int u = pq.extract_min();
		//Graph is unconnected if the distance is infinity 
		if (d[u] == INT_MAX)
			break; //Exit loop if there is no path
    //Loop equal while less than the niumber of nodes 
		for(int i = 0; i < n; i++){
			if(mat[u][i]){
        //If new distance is smaller than INTMAX then update
				if(d[i] > d[u] + 1){
          //Update new distance 
					d[i] = d[u] + 1;
          //Move on to next node in queue 
					pq.decrease_key(i, d[i]);
					p[i] = u;
				}
			}
		}
	}		
}
//Task 1
void routeSearch_1(Graph graph, int city_A, int city_B, int num_connection) {
  //N holds the number of nodes 
	int n = graph.get();
  //Arrays each equal to the number of nodes 
  //Array d holds distance, intialized at infinity
  //Array p holds the parent node, intialized at NIL
	int d[n], p[n];
  //int test[graph.get()]; Test array delcaration
  // vector holding the routes 
	vector<int> ans; 
  
  //Call Dijkstra's 
  //Pass in graph, starting city, 2 arrays
	Dijkstra(graph, city_A, d, p);
	if(d[city_B] <= num_connection){ //there are less than x connections
    //Current city is city B
		int cur_city = city_B;
    //Declare index variable 
		int i;
    //While it is a valid current city, not -1
		while(cur_city != -1) {
      //Insert into vector starting node ***
			//ans.insert(ans.begin(), cur_city);
      ans.push_back(cur_city);
      //Current city becomes city in parent vector index
			cur_city = p[cur_city];
		}
    //Loop through the answer array to output
		for(i = 0; i < ans.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ans.size() - 1){
				cout << "city" << ans[i] << " to ";
			}
			else{
				cout << "city" << ans[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
	}
	else{
		//can not fly from A to B with less than x connections
		cout << "No such route." << endl; 
	}
}

//Task 2
void routeSearch_2(Graph graph, int city_A, int city_B, int city_C, int city_D) {
    //N holds the number of nodes 
	int n = graph.get();
  // vector holding the routes 
	vector<int> ans; //A->B->C->D
  vector<int> ans2; //A->C->B->D
  //Array d holds distance, intialized at infinity
  //Array p holds the parent node, intialized at NIL
  //Distances and parents for A
	int dA[n], pA[n];
  //Call Dijkstras for A
  Dijkstra(graph, city_A, dA, pA);
  //Parents and distances for B
  int dB[n], pB[n];
  //Call Dijkstras for B
  Dijkstra(graph, city_B, dB, pB);
   //Parents and distances for C
  int dC[n], pC[n];
  Dijkstra(graph, city_C, dC, pC);
  
  bool routeExists = true;
    //Check the route from C -> D
    int cur_city = city_D;
    //While it is a valid current city, not -1
		while(cur_city != city_C && routeExists ) {
      //Check if path exists from c to c
      if(cur_city == -1 || cur_city == INT_MAX){
          //if it does not then exit loop 
            routeExists = false;
          cout<<"No such route "<< endl;
          return;
      }
      else{
      //Insert into vector starting node ***
			ans.insert(ans.begin(), cur_city);
      //Current city becomes city in parent vector index
			cur_city = pC[cur_city];
      }
    }  

  //Check the route from B -> D
    cur_city = city_D;
    //While it is a valid current city, not -1
		while(cur_city != city_B && routeExists ) {
      //Check if path exists from c to c
      if(cur_city == -1 || cur_city == INT_MAX){
          //if it does not then exit loop 
            routeExists = false;
            cout<<"No such route "<< endl;
          return;
      }
      else{
        //Second answer vector
      ans2.insert(ans2.begin(),cur_city);
      //Current city becomes city in parent vector index
			cur_city = pB[cur_city];
      }
    }  
  
  
  
  //Check the route from B->C
    cur_city = city_C;
    //While it is a valid current city, not -1
		while(cur_city != city_B && routeExists) {
      //If current city is -1 exit loop and return 
      if(cur_city == -1 || cur_city == INT_MAX){
        routeExists = false;
          cout<<"No such route "<< endl;
        return;
      }
      else{
        //If the current city is not a -1 then add it to the answer vector 
        ans.insert(ans.begin(), cur_city);
      //Current city becomes city in parent vector index
			cur_city = pB[cur_city];
      }

		}

   //Check the route from C->B
    cur_city = city_B;
    //While it is a valid current city, not -1
		while(cur_city != city_C && routeExists) {
      //If current city is -1 exit loop and return 
      if(cur_city == -1 || cur_city == INT_MAX){
        routeExists = false;
        cout<<"No such route "<< endl;
        return;
      }
      else{
        //If the current city is not a -1 then add it to the answer vector 
        ans2.insert(ans2.begin(), cur_city);
      //Current city becomes city in parent vector index
			cur_city = pC[cur_city];
      }

		}

  

  //Checks the route from A -> B
    cur_city = city_B;
    //While it is a valid current city, not -1
		while(cur_city != -1 && routeExists) {
      //If the route does not exist then return 
      if(cur_city == -1 || cur_city == INT_MAX){
        routeExists = false;
        cout<<"No such route "<< endl;
        return;
      }
      else{
        //If its not a negative one then add it to the answer vector
      ans.insert(ans.begin() , cur_city);
      //Current city becomes city in parent vector index
			cur_city = pA[cur_city];
      }
		}

  //Checks the route from A -> C
    cur_city = city_C;
    //While it is a valid current city, not -1
		while(cur_city != -1 && routeExists) {
      //If the route does not exist then return 
      if(cur_city == -1 || cur_city == INT_MAX){
        routeExists = false;
        cout<<"No such route "<< endl;
        return;
      }
      else{
        //If its not a negative one then add it to the answer vector
      ans2.insert(ans2.begin() , cur_city);
      //Current city becomes city in parent vector index
			cur_city = pA[cur_city];
      }
		}  
  //Declare the integer to output the routes
    int i;
  //Check vector size to find the smallest one
  //If they are equal size then output ABCD
  if(ans.size() < ans2.size() || ans.size() == ans2.size()){
   	for(i = 0; i < ans.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ans.size() - 1){
				cout << "city" << ans[i] << " to ";
			}
			else{
				cout << "city" << ans[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
  }
    //Otherwise output ACBD
  else{
  for(i = 0; i < ans2.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ans2.size() - 1){
				cout << "city" << ans2[i] << " to ";
			}
			else{
				cout << "city" << ans2[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
  }
  return;
}

//Task 3
void routeSearch_3(Graph graph, int city_A, int city_B, int city_C) {
//Get the size of the graph 
int n = graph.get();
//Vectors to hold the path from the starting cities to the destination city
vector<int> ansA; //Path from A to city
vector<int> ansB; //Path from B to city
vector<int> ansC; //Path from C to city 
 //Array d holds distance, intialized at infinity
  //Array p holds the parent node, intialized at NIL
  //Distances and parents for A
	int dA[n], pA[n];
  //Call Dijkstras for A
  Dijkstra(graph, city_A, dA, pA);
  //Parents and distances for B
  int dB[n], pB[n];
  //Call Dijkstras for B
  Dijkstra(graph, city_B, dB, pB);
   //Parents and distances for C
  int dC[n], pC[n];
  Dijkstra(graph, city_C, dC, pC);
//Assume there is no city to meet in
  int destination = -1;
  int distanceSum = -1;
//Show me all the distance and parent results
  for(int i = 0; i < n - 1 ;i++){
    //if destination distance is not 0 or intmax
    //if parent index is not -1
    //and parent index does not equal starting cities
    //destination can have different distances for a,b,c
    //If the parent value are all the same in the same index
  if(pA[i] == pB[i] && pA[i] == pC[i] && pB[i] == pC[i] ){
  //If the node is not any of the starting cities based on distance
  if(dA[i] != 0 && dB[i] != 0 && dC[i] != 0){
    //If the node is mot any of the starting cities based on parent
  if(((pA[i] != city_A) && (pA[i] != city_A) && (pC[i] !=city_A)) && ((pA[i] != city_B) && (pA[i] != city_B) && (pC[i] !=city_B)) && ((pA[i] != city_C) && (pA[i] != city_C) && (pC[i] !=city_C))){
    //Calculate the sum of the distance 
    int newDistanceSum = dA[i] +  dB[i] + dC[i];
    if(distanceSum == -1){
        distanceSum = newDistanceSum ;
    }
    else if((newDistanceSum < distanceSum) || (newDistanceSum == distanceSum))
    {
      //Find new distance sum
      distanceSum = newDistanceSum;
      //Find the city with smallest total distance and smallest city number 
        if(destination == -1){
          destination = pA[i];
        }
      else if(pA[i]<destination){
         destination = pA[i];
      }
    }
    
      }
    }
    }
  }
  //If no common city was found then output no such city
  if(destination == -1){
    cout<<"No such city" << endl;
  }
  else{
    //Output the city to meet in 
    cout<<"You three should meet at city: " << destination <<endl;
    //Collect answers for city A
    int current_city = destination;
    //Collect the path from A to destination 
    while(current_city != -1){
      ansA.insert(ansA.begin(),current_city);
      current_city = pA[current_city];
    }
    //Output the answer vectors using int i 
    int i;
    cout<<"Route for the first person: ";
    	for(i = 0; i < ansA.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ansA.size() - 1){
				cout << "city" << ansA[i] << " to ";
			}
			else{
				cout << "city" << ansA[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
    //Update the current city pointer 
    current_city = destination;
    //Collect the route from B to destination 
    while(current_city != -1){
      ansB.insert(ansB.begin(),current_city);
      current_city = pB[current_city];
    }
    //Output the second answer vectors
    cout<<"Route for the second person: ";
    	for(i = 0; i < ansB.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ansB.size() - 1){
				cout << "city" << ansB[i] << " to ";
			}
			else{
				cout << "city" << ansB[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
    //Update the current city pointer 
      current_city = destination;
    //Collect route from C to destination 
    while(current_city != -1){
      ansC.insert(ansC.begin(),current_city);
      current_city = pC[current_city];
    }
    //Output the answer vectors for the third person 
    cout<<"Route for the third person: ";
    	for(i = 0; i < ansC.size(); i++){
      //While you havent reached the end of the array output each element 
			if(i != ansC.size() - 1){
				cout << "city" << ansC[i] << " to ";
			}
			else{
				cout << "city" << ansC[i];
			}
		}
		cout << endl;
		cout << "Total connection: " << i-1 << endl;
  
  }
  //Return to Main 
  return;

}
