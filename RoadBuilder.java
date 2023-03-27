import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.*;
import javax.swing.*;

public class RoadBuilder extends Grid {
    // bfs
    public static void bfs_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.(row.get()); // number of rows
        int n = grid.(column.get()); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        ArrayList<Tile> queue = new ArrayList<>(); // ArrayList to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        visited[start.(row.get())][start.(column.get())] = true; // mark starting tile as visited
        int index = 0; // index of the current node being processed
        
        while (index < queue.size()) {
            Tile current = queue.get(index);
            index++;
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.(row.get()), current.(column.get())).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.(row.get())][current.(column.get())]); // get parent tile
                }
                grid.getTile(start.(row.get()), start.(column.get())).set_path(true); // mark starting tile as part of path
                return;
            }
            
            for (int[] dir : directions) {
                int nx = current.(row.get()) + dir[0];
                int ny = current.(column.get()) + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    queue.add(neighbor);
                    visited[nx][ny] = true;
                    parent[nx][ny] = current.index();
                }
            }
        }
    }

    // A*
    public static void aStar_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.(row.get()); // number of rows
        int n = grid.(column.get()); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        double[][] gScore = new double[m][n]; // array to keep track of the cost to get to each node
        double[][] fScore = new double[m][n]; // array to keep track of the total estimated cost to reach the goal through each node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        gScore[start.(row.get())][start.(column.get())] = 0; // set cost to starting tile to 0
        fScore[start.(row.get())][start.(column.get())] = heuristic(start, goal); // set estimated cost to goal through starting tile
        while (!queue.isEmpty()) {
            Tile current = queue.poll(); // get node with lowest fScore
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.(row.get()), current.(column.get())).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.(row.get())][current.(column.get())]); // get parent tile
                }
                grid.getTile(start.(row.get()), start.(column.get())).set_path(true); // mark starting tile as part of path
                return;
            }
            visited[current.(row.get())][current.(column.get())] = true;
            for (int[] dir : directions) {
                int nx = current.(row.get()) + dir[0];
                int ny = current.(column.get()) + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    double tentativeGScore = gScore[current.(row.get())][current.(column.get())] + 1; // cost to get to neighbor through current
                    if (!queue.contains(neighbor) || tentativeGScore < gScore[nx][ny]) {
                        parent[nx][ny] = current.index();
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
        return Math.abs(tile1.(row.get()) - tile2.(row.get())) + Math.abs(tile1.(column.get()) - tile2.(column.get()));
    }
    
     // Dijkstra's algorithm
    public static void dijkstra_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.(row.get()); // number of rows
        int n = grid.(column.get()); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        int[][] dist = new int[m][n]; // array to keep track of the shortest distance to each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingInt(t -> dist[t.(row.get())][t.(column.get())])); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        dist[start.(row.get())][start.(column.get())] = 0; // set starting tile distance to zero
        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.(row.get()), current.(column.get())).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.(row.get())][current.(column.get())]); // get parent tile
                }
                grid.getTile(start.(row.get()), start.(column.get())).set_path(true); // mark starting tile as part of path
                return;
            }
            if (visited[current.(row.get())][current.(column.get())]) {
                continue; // skip if already visited
            }
            visited[current.(row.get())][current.(column.get())] = true;
            for (int[] dir : directions) {
                int nx = current.(row.get()) + dir[0];
                int ny = current.(column.get()) + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    int new_dist = dist[current.(row.get())][current.(column.get())] + 1; // assuming edge weights of 1
                    if (new_dist < dist[nx][ny]) {
                        dist[nx][ny] = new_dist;
                        parent[nx][ny] = current.index();
                        queue.add(neighbor);
                    }
                }
            }
        }
    }


    // prints grid
    static void visualize(Grid grid) {
        JFrame frame = new JFrame();
        
        frame.setLayout("GridLayout");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
    }
}
