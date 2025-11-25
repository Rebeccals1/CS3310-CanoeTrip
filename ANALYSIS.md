# CS 3310 – Programming Assignment 3
### Canoe Trip Dynamic Programming Solution
### Author: Rebecca Smith

------------------------------------------------------------
## 1. Recurrence Relation

Let C[a][b] be the direct cost of renting a canoe from post a 
to post b (a < b). Let OPT[a][b] be the minimum cost of 
traveling from post a to post b using any sequence of rentals.

```
Base case:
    If a and b are consecutive:
        OPT[a][a+1] = C[a][a+1]

General recurrence (for a < b):
    OPT[a][b] = min( C[a][b], min_{a < k < b} ( C[a][k] + OPT[k][b] ) )
```

**Explanation:**

To travel from a to b, we either rent a canoe directly from 
a to b or stop at an intermediate post k, rent from a to k, 
and then continue optimally from k to b. The DP algorithm 
evaluates all possible k and chooses the minimum.

A table split[a][b] records the value k that achieved the 
minimum so the optimal route can be reconstructed.

------------------------------------------------------------
## 2. Dynamic Programming Strategy

The OPT table is filled in order of increasing interval length 
(b - a). Smaller subproblems OPT[k][b] are computed first, 
so they are available when computing OPT[a][b].

**Example order of computation:**

```
    Distance 1: (0,1), (1,2), (2,3), ...
    
    Distance 2: (0,2), (1,3), ...
    
    Distance 3: (0,3), ...
```

------------------------------------------------------------
## 3. Time Complexity

There are O(n^2) distinct subproblems OPT[a][b].

For each pair (a, b), the algorithm checks all intermediate 
posts k with a < k < b. This requires O(n) work.

**Therefore, total runtime:**

```
    O(n^2) * O(n) = O(n^3)
```

This matches the theoretical time complexity expected from  interval dynamic programming.

------------------------------------------------------------
## 4. Space Complexity

The program stores:

* direct cost matrix C[a][b]      : O(n^2)
* optimal cost matrix OPT[a][b]  : O(n^2)
* split table for reconstruction : O(n^2)

Total space usage: O(n^2).

------------------------------------------------------------
## 5. Summary

The canoe trip problem is solved using a classic interval-based
dynamic programming approach. The recurrence relation ensures 
optimal substructure, and the split table allows the exact rental 
sequence to be reconstructed. The resulting algorithm runs in 
O(n^3) time and uses O(n^2) space.


