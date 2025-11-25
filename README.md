# CS 3310 – Programming Assignment 3
### Canoe Trip Dynamic Programming Solution
### Author: Rebecca Smith

------------------------------------------------------------
## Overview

This program computes the optimal (minimum-cost) sequence of 
canoe rentals for a trip down the Los Angeles River using a 
dynamic programming algorithm. The cost matrix C[a][b] gives 
the direct cost of renting a canoe at post a and returning it 
at post b (where a < b). The goal is to determine:

1. The optimal cost between every pair (i, j) with i < j
2. The optimal route from post 0 to post n−1

**The program automatically processes the input files:**

* inputA.txt
* inputB.txt
* inputC.txt
  
**from src/main/resources**

------------------------------------------------------------
## How to Compile

From the project root directory:

    mvn -q -DskipTests package
or (if not using Maven)

    javac src/main/java/CanoeTrip.java

------------------------------------------------------------
## How to Run (IntelliJ or command line)

Run the CanoeTrip main class normally. 

The program will automatically read:

    src/main/resources/inputA.txt
    src/main/resources/inputB.txt
    src/main/resources/inputC.txt

**The program will then print:**

* Optimal cost matrix
* Optimal rental sequence
* Optimal total cost

After processing the three files, the program optionally allows 

the user to enter another resource filename.

------------------------------------------------------------
## Input File Format

**Each input file must follow this format:**

Line 1: integer n (number of posts)

Next n−1 lines: the upper-triangular entries C[i][j], i < j

**Example:**

```
4
10 15 50
40 20
35
```

Whitespace is not significant.

