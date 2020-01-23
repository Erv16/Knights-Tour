# **Knights-Tour**

### **Introduction**
The Knight’s tour is an interesting question related to the game of chess. In chess, the knight must move two squares in one direction followed by one square in a perpendicular direction. The knight’s tour problem asks whether a knight on a chessboard can visit every square on the board exactly once.

To compile the code type the following commands in the command prompt:
```
javac Position.java
javac KnightsTour.java
```

To run the application type the following command in the command prompt:
```
java KnightsTour
```

## **Algorithms**
The Knight’s Tour Problem is predominantly an example of a backtracking problem. But straightforward backtracking maybe an inadequate tool and its performance can be bolstered by using heuristically guided choices.

The backtracking approach works well for relatively small sized boards (5x5), but is unpredictable for larger boards (8x8), often taking millions of backtracking calls before finding a solution and rarely producing a quick solution. Therefore, we have decided to tackle the problem using the heuristic approach.

### *1. Backtracking using Depth First Search*
There is a maximum of 8 different moves which a knight can take from a cell, we choose a move for the knight from all the available moves and then recursively check if this will lead to the solution or not. If the knight reaches a cell, from where there is no further available cell to move and we have not reached the solution yet (not all cells are covered), then we backtrack and change our decision and choose a different path. If not, then we choose a different path. We keep the track of the moves in a matrix. This matrix stores the step number in which we visited a cell. For example, if we visit a cell in the second step, it will have a value of 2. This matrix also helps us to know whether we have covered all the cells or not. If the last visited cell has a value of N*N, it means we have covered all the cells. Thus, our approach includes starting from a cell and then choosing a move from all the available moves. Then we check if this move will lead us to the solution or not. If not, we choose a different move. 

### *2. Backtracking using Bruteforce*
The Bruteforce approach attempts to try every possible permutation of moves and check if it is valid. The knight has a maximum of 8 different moves from a cell. From among the possible options (candidates) available, the algorithm will pick one candidate and enumerate all of its possible solutions. It will try every valid cell. It will keep repeating this process until the candidates it has chosen leads to a satisfactory solution. If the chosen candidate(s) do not guarantee a solution, it simply backtracks and picks the next candidate and proceeds towards finding the solution. The algorithm generates all possible solutions, including the ones that may not necessarily be a solution and from these solutions finds the appropriate one

### *3. Warnsdorff’s Rule*
Warnsdorff’s Rule is a heuristic method for finding the Knight’s Tour. The basic idea of Warnsdorff’s rule is that the knight is moved in such a manner that it always proceeds to the cell from which the knight  will have the least onward moves. Set the initial position of the knight on the board which can be any arbitrary position. There will be a set of accessible positions for the knight from the initial position. The algorithm then calculates the positions (neighbor move) with the least amount of viable available moves and returns that position. This process is repeated for every position and only one new position is returned at a time, therefore dramatically shortening runtime.  

## **Performance Evaluation**
The Knight’s Tour problem cannot provide a solution for smaller sized boards. The 3 X 3 case cannot have a tour because the middle square is not adjacent to any other squares. The 4 x 4 case is also not solvable, the board being symmetrical, there are certain cells that form disjoint cycles. Since there is no way to jump from one cycle to the next, there are always four white squares left over [6].
 
The Backtracking algorithms namely Depth First Search and Bruteforce tend to generate a path within a short span of time for smaller sized boards (5 x 5 – 7 x 7). However for 8 x 8 boards, unless the starting point for the Knight is an edge, it takes a substantial amount of time (running into an hour) for computing the path. Even with the modern computing power, it takes a significant amount of time for computation. The heuristic on the other hand helps bring down the computation time drastically. Enumerated below are a few of the test results on different sized boards with the computation time.

| Board Size | Backtracking using DFS | Bruteforce using Backtracking | Warnsdorff’s Rule Heuristic
| ---------- | --------------------- | ----------------------------- | ------------------------- |
|5 x 5 | 0.002711893 | 0.009180727 | 7.61173E-4 |
6 x 6	 | 0.023058767 | 0.326552939 | 9.00551E-4 |
7 x 7	 | 0.238502047 | 0.08487706 | 0.00118215 |
8 x 8	 | 0.308912837 | 0.107203098 | 0.001395485 |

The heuristic works well for large sized boards such as 9 x 9 and 10 x 10 by finding the path in 0.001660017 seconds and 0.00208839 seconds respectively, wherein the backtracking algorithms would require massive computing power and a really long time to compute.

