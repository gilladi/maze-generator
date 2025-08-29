# maze-generator
A Java-based maze generator and solver that uses depth-first search (DFS) to create and visualize mazes.

---

# Features
- Generates unique mazes every run  
- Adjustable maze size (rows and columns)  
- Clean and responsive design  

---

# Tutorial
Here’s a quick overview of how the algorithm works:

**Maze Generation**  
- The maze is represented as a 2D grid of cells.
- Depth-First Search (DFS) with backtracking is used to carve out a randomized maze.  
- Starting from the top-left corner, the algorithm visits unvisited neighbors, carving passages until every cell is reachable.

**Maze Solving**  
- Once generated, the solver uses DFS again to search for a path from the **start (S)** to the **end (E)**.  
- The algorithm explores possible paths recursively, backtracking when a dead end is reached, until the correct solution path is found.

---

# Future Updates
- I am working on allowing the maze to properly generate visually even if the window is not large enough for your inputted dimensions.
- I am working on animating the process in an optimized way so users can visually see the mazes being generated and solved step by step.
