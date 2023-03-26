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
        int m = grid.GetRows(); // number of rows
        int n = grid.GetColumns(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        ArrayList<Tile> queue = new ArrayList<>(); // ArrayList to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        visited[start.GetRow()][start.GetColumn()] = true; // mark starting tile as visited
        int index = 0; // index of the current node being processed
        
        while (index < queue.size()) {
            Tile current = queue.get(index);
            index++;
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.row(), current.col()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.row()][current.col()]); // get parent tile
                }
                grid.getTile(start.row(), start.col()).set_path(true); // mark starting tile as part of path
                return;
            }
            
            for (int[] dir : directions) {
                int nx = current.row() + dir[0];
                int ny = current.col() + dir[1];
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
        int m = grid.rows(); // number of rows
        int n = grid.cols(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        double[][] gScore = new double[m][n]; // array to keep track of the cost to get to each node
        double[][] fScore = new double[m][n]; // array to keep track of the total estimated cost to reach the goal through each node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        gScore[start.row()][start.col()] = 0; // set cost to starting tile to 0
        fScore[start.row()][start.col()] = heuristic(start, goal); // set estimated cost to goal through starting tile
        while (!queue.isEmpty()) {
            Tile current = queue.poll(); // get node with lowest fScore
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.row(), current.col()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.row()][current.col()]); // get parent tile
                }
                grid.getTile(start.row(), start.col()).set_path(true); // mark starting tile as part of path
                return;
            }
            visited[current.row()][current.col()] = true;
            for (int[] dir : directions) {
                int nx = current.row() + dir[0];
                int ny = current.col() + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    double tentativeGScore = gScore[current.row()][current.col()] + 1; // cost to get to neighbor through current
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
        return Math.abs(tile1.row() - tile2.row()) + Math.abs(tile1.col() - tile2.col());
    }
    
     // Dijkstra's algorithm
    public static void dijkstra_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.rows(); // number of rows
        int n = grid.cols(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        int[][] dist = new int[m][n]; // array to keep track of the shortest distance to each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        PriorityQueue<Tile> queue = new PriorityQueue<>(Comparator.comparingInt(t -> dist[t.row()][t.col()])); // priority queue to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        dist[start.row()][start.col()] = 0; // set starting tile distance to zero
        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.row(), current.col()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.row()][current.col()]); // get parent tile
                }
                grid.getTile(start.row(), start.col()).set_path(true); // mark starting tile as part of path
                return;
            }
            if (visited[current.row()][current.col()]) {
                continue; // skip if already visited
            }
            visited[current.row()][current.col()] = true;
            for (int[] dir : directions) {
                int nx = current.row() + dir[0];
                int ny = current.col() + dir[1];
                Tile neighbor = grid.getTile(nx, ny);
                if (neighbor != null && neighbor.weight() == 1 && !visited[nx][ny]) {
                    int new_dist = dist[current.row()][current.col()] + 1; // assuming edge weights of 1
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