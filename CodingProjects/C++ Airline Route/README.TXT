------------------------------------------------------------------------
Airline Route Shortest Path
------------------------------------------------------------------------

PURPOSE OF PROJECT:
Shortest Path Final Project READ-ME
To compile project 
g++ WA.cpp
To run project
./a.out 
routeSearch2: Performs three calls to Dijkstra’s to populate the distance and parent arrays for city A, B and C. 
Then checks if no negative -1s are found in the path while the answer vector is being populated, then that denotes that a path exists. 
After vector 1 and vector 2 are populated then they are compared to find the smallest one, if they are the same size then ABCD vector will be chosen to be the output. 
routeSearch3: Performs three calls to Dijkstra’s to populate the distance and parent arrays for city A, B and C. 
Searches through all parent and distance arrays to find a common city.
 The common city must meet three requirements, one it cannot be any of the starting cities and two it has to be the shortest distance among the three starting points. 
Lastly it must have the smallest city number among all the common cities if more than one city was found. If there is no common city found then it will output no such city. 
Once the application is done running it will let the user know any and all information needed to make the shortest connecting flight route
for their journey.
VERSION or DATE: 7/7/2023
HOW TO START THIS PROJECT: To start this project compile and run the main and use between option 1-3.
AUTHORS: Abel Hernandez

