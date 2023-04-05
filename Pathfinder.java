import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// TODO: line 54, 82, 95, 129, 146

public class Pathfinder extends Grid {
    private static Grid grid;
    private static Tile start;
    private static Tile goal;

    public Pathfinder(Grid newGrid) {
        grid = newGrid;
        // start = 
        // start = newStart;
        // goal = newGoal;
    }
    // bfs
    public static void bfs_pathfinding() { //will return int[][] path
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        // int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        // boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> queue = new ArrayList<>(); // ArrayList to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        // visited[start.getRow()][start.getColumn()] = true; // mark starting tile as visited
        // visited.add()
        int index = 0; // index of the current node being processed
        
        while (index < queue.size()) {
            Tile current = queue.get(index);
            index++;
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.getRow(), current.getColumn()).setOnPath(true); // mark tile as part of path
                    // current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile

                    current = grid.getTile(current.getRow(), current.getColumn());
                }
                grid.getTile(start.getRow(), start.getColumn()).setOnPath(true); // mark starting tile as part of path
                return;
            }
            
            for (int[] dir : directions) {
                int nx = current.getRow() + dir[0];
                int ny = current.getColumn() + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                // if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                //     queue.add(neighbor);
                //     visited[nx][ny] = true;
                    // FIX parent[nx][ny] = current.getIndex();
                // }
            }
        }
    }

    //NOTES FROM KENNA:
    // Changed grid.getRow() to grid.getRows()  same for grid.getColumns()

    // A*  
    public static void aStar_pathfinding() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        double[][] gScore = new double[m][n]; // array to keep track of the cost to get to each node
        double[][] fScore = new double[m][n]; // array to keep track of the total estimated cost to reach the goal through each node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        gScore[start.getRow()][start.getColumn()] = 0; // set cost to starting tile to 0
        fScore[start.getRow()][start.getColumn()] = heuristic(start, goal); // set estimated cost to goal through starting tile
        while (!queue.isEmpty()) {
            Tile current = queue.poll(); // get node with lowest fScore
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.getRow(), current.getColumn()).setOnPath(true); // mark tile as part of path
                    // FIX current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile
                }
                grid.getTile(start.getRow(), start.getColumn()).setOnPath(true); // mark starting tile as part of path
                return;
            }
            visited[current.getRow()][current.getColumn()] = true;
            for (int[] dir : directions) {
                int nx = current.getRow() + dir[0];
                int ny = current.getColumn() + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    double tentativeGScore = gScore[current.getRow()][current.getColumn()] + 1; // cost to get to neighbor through current
                    if (!queue.contains(neighbor) || tentativeGScore < gScore[nx][ny]) {
                        // FIX parent[nx][ny] = current.index();
                        gScore[nx][ny] = tentativeGScore;
                        fScore[nx][ny] = tentativeGScore + heuristic(neighbor, goal);
                        if (!queue.contains(neighbor)) {
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }
    }

    // heuristic function (manhattan distance)
    private static double heuristic(Tile tile1, Tile tile2) {
        return Math.abs(tile1.getRow() - tile2.getRow()) + Math.abs(tile1.getColumn() - tile2.getColumn());
    }
    
     // Dijkstra's algorithm
    public static void dijkstra_pathfinding() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        int[][] dist = new int[m][n]; // array to keep track of the shortest distance to each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingInt(t -> dist[t.getRow()][t.getColumn()])); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        dist[start.getRow()][start.getColumn()] = 0; // set starting tile distance to zero
        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.getRow(), current.getColumn()).setOnPath(true); // mark tile as part of path
                    // FIX current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile
                }
                grid.getTile(start.getRow(), start.getColumn()).setOnPath(true); // mark starting tile as part of path
                return;
            }
            if (visited[current.getRow()][current.getColumn()]) {
                continue; // skip if already visited
            }
            visited[current.getRow()][current.getColumn()] = true;
            for (int[] dir : directions) {
                int nx = current.getRow() + dir[0];
                int ny = current.getColumn() + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    int new_dist = dist[current.getRow()][current.getColumn()] + 1; // assuming edge weights of 1
                    if (new_dist < dist[nx][ny]) {
                        dist[nx][ny] = new_dist;
                        // FIX parent[nx][ny] = current.index();
                        queue.add(neighbor);
                    }
                }
            }
        }
    }
}
