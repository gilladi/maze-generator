import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
    
        System.out.println("Welcome to the Maze Solver!");
        System.out.println("This program will solve a maze using Depth First Search (DFS).");
        System.out.println("You can either input a maze manually or generate a random maze.");
        System.out.println("The maze will be solved and the solution path will be displayed with an @.");
        System.out.println("I will begin by asking for the dimensions of the maze.");
        System.out.println("For the best results, ensure your window is large enough for your inputted dimensions.\n");
    
        int width = 0;
        int height = 0;
    
        while (true) {
            try {
                System.out.println("Insert Width of Maze (Positive Integer): ");
                width = Integer.parseInt(console.nextLine());
                if (width <= 0) {
                    throw new NumberFormatException("Width must be greater than 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer for the width.");
            }
        }
    
        while (true) {
            try {
                System.out.println("Insert Height of Maze (Positive Integer): ");
                height = Integer.parseInt(console.nextLine());
                if (height <= 0) {
                    throw new NumberFormatException("Height must be greater than 0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer for the height.");
            }
        }
    
        char[][] maze;
    
        System.out.println("Choose an option:");
        System.out.println("Type '1' to input the maze manually.");
        System.out.println("Type '2' to generate a maze.");
        int choice = Integer.parseInt(console.nextLine());
    
        if (choice == 1) {
            maze = inputMaze(console, height, width);
        } else if (choice == 2) {
            maze = generateMaze(height, width);
        } else {
            System.out.println("Invalid choice.");
            System.out.println("Exiting...");
            return;
        }

        
        System.out.println("\nPress Enter to see the solution.");
        console.nextLine();

        String result = solveMaze(maze);
        System.out.println("Maze Solution: " + result);

        console.close();
    }


    public static char[][] inputMaze(Scanner console, int height, int width) {
        char[][] maze = new char[height][width];
        System.out.println("Generate a simple maze as a text file with the following rules:\n" +
                "- ` ` represents paths.\n" +
                "- `*` represents walls.\n" +
                "- `S` is the start point.\n" +
                "- `E` is the end point.\n" +
                "Make sure there is always a path from `S` to `E`.");
        
        int startCount = 0;
        int endCount = 0;
    
        for (int i = 0; i < height; i++) {
            System.out.println("Enter row " + (i + 1) + ":");
            String line = console.nextLine();
            
            if (line.length() != width) {
                System.out.println("Error: Each row must have exactly " + width + " characters.");
                System.out.println("Exiting...");
                System.exit(1);
            }
    
            for (int j = 0; j < width; j++) {
                char c = line.charAt(j);
                if (c != ' ' && c != '*' && c != 'S' && c != 'E') {
                    System.out.println("Error: Invalid character '" + c + "'. Allowed characters are ' ', '*', 'S', and 'E'.");
                    System.out.println("Exiting...");
                    System.exit(1);
                }
                if (c == 'S') startCount++;
                if (c == 'E') endCount++;
                maze[i][j] = c;
            }
        }
    
        if (startCount != 1 || endCount != 1) {
            System.out.println("Error: Maze must contain exactly one 'S' (start point) and one 'E' (end point).");
            System.out.println("Exiting...");
            System.exit(1);
        }
    
        return maze;
    }

    public static char[][] generateMaze(int height, int width) {
        char[][] maze = new char[height][width];
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = '*';
            }
        }
    
        int startX = 0, startY = 0;
        maze[startX][startY] = 'S';
        dfsGenerateMaze(maze, startX, startY);

        int endX = height - 1;
        int endY = width - 1;

        ensurePathToEnd(maze, endX, endY);
        maze[endX][endY] = 'E';
    
        System.out.println("Generated Maze:");
        printMaze(maze);
    
        return maze;
    }
    
    private static void ensurePathToEnd(char[][] maze, int endX, int endY) {
        Random random = new Random();

        while (maze[endX][endY] == '*') {
            int dir = random.nextInt(2);
            if (dir == 0 && endX > 0) {
                maze[endX--][endY] = ' ';
            } else if (dir == 1 && endY > 0) {
                maze[endX][endY--] = ' ';
            }
        }
    }
    
    private static void dfsGenerateMaze(char[][] maze, int x, int y) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        Random random = new Random();

        int[] dirOrder = {0, 1, 2, 3};
        for (int i = 0; i < dirOrder.length; i++) {
            int swapIdx = random.nextInt(dirOrder.length);
            int temp = dirOrder[i];
            dirOrder[i] = dirOrder[swapIdx];
            dirOrder[swapIdx] = temp;
        }
    
        for (int dir : dirOrder) {
            int nx = x + dx[dir] * 2;
            int ny = y + dy[dir] * 2;
    
            if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[0].length && maze[nx][ny] == '*') {
                maze[x + dx[dir]][y + dy[dir]] = ' ';
                maze[nx][ny] = ' ';
                dfsGenerateMaze(maze, nx, ny);
            }
        }
    }
    
    public static void printMaze(char[][] maze) {
        StringBuilder topBottomBorder = new StringBuilder(" ");
        for (int i = 0; i < maze[0].length + 1; i++) {
            topBottomBorder.append("_ ");
        }
        System.out.println(topBottomBorder);

        for (int i = 0; i < maze.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println(topBottomBorder);
    }


    public static String solveMaze(char[][] maze) {
        int height = maze.length;
        int width = maze[0].length;
    
        int startX = -1, startY = -1;
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] == 'S') {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }
    
        if (startX == -1 || startY == -1) {
            return "No start point found!";
        }
    
        boolean[][] visited = new boolean[height][width];
        if (dfs(maze, startX, startY, visited)) {
            System.out.println("Maze Solution Path:");
            printMaze(maze);
            return "Path marked with '@' in the maze.";
        } else {
            return "No solution found!";
        }
    }
    
    public static boolean dfs(char[][] maze, int x, int y, boolean[][] visited) {
        if (x < 0 || x >= maze.length || y < 0 || y >= maze[0].length || maze[x][y] == '*' || visited[x][y]) {
            return false;
        }

        if (maze[x][y] == 'E') {
            return true;
        }

        visited[x][y] = true;
        maze[x][y] = '@';
  
        if (dfs(maze, x + 1, y, visited)) {
            return true;
        }
        if (dfs(maze, x, y + 1, visited)) {
            return true;
        }
        if (dfs(maze, x - 1, y, visited)) {
            return true;
        }
        if (dfs(maze, x, y - 1, visited)) {
            return true;
        }
    
        visited[x][y] = false;
        maze[x][y] = ' ';
        return false;
    }
}
