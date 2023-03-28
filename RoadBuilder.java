import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class RoadBuilder extends Grid {
    // holds visualization
    private static JFrame frame;
    // button dimensions
    private static int buttonWidth = 100;
    private static int buttonHeight = 100;
    
    // bfs
    public static void bfs_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
        boolean[][] visited = new boolean[m][n]; // array to keep track of visited nodes
        ArrayList<Tile> queue = new ArrayList<>(); // ArrayList to hold nodes to be processed
        queue.add(start); // add starting tile to queue
        visited[start.getRow()][start.getColumn()] = true; // mark starting tile as visited
        int index = 0; // index of the current node being processed
        
        while (index < queue.size()) {
            Tile current = queue.get(index);
            index++;
            if (current == goal) {
                // reconstruct path and mark it on the grid
                while (current != start) {
                    grid.getTile(current.getRow(), current.getColumn()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile
                }
                grid.getTile(start.getRow(), start.getColumn()).set_path(true); // mark starting tile as part of path
                return;
            }
            
            for (int[] dir : directions) {
                int nx = current.getRow() + dir[0];
                int ny = current.getColumn() + dir[1];
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
        int m = grid.getRow(); // number of rows
        int n = grid.getColumn(); // number of columns
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
                    grid.getTile(current.getRow(), current.getColumn()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile
                }
                grid.getTile(start.getRow(), start.getColumn()).set_path(true); // mark starting tile as part of path
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
        return Math.abs(tile1.getRow() - tile2.getRow()) + Math.abs(tile1.getColumn() - tile2.getColumn());
    }
    
     // Dijkstra's algorithm
    public static void dijkstra_pathfinding(Grid grid, Tile start, Tile goal) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRow(); // number of rows
        int n = grid.getColumn(); // number of columns
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
                    grid.getTile(current.getRow(), current.getColumn()).set_path(true); // mark tile as part of path
                    current = grid.getTile(parent[current.getRow()][current.getColumn()]); // get parent tile
                }
                grid.getTile(start.getRow(), start.getColumn()).set_path(true); // mark starting tile as part of path
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
                        parent[nx][ny] = current.index();
                        queue.add(neighbor);
                    }
                }
            }
        }
    }


    // prints grid
    static void visualize(Grid grid, int[][] path) {
        int rows = 4; //grid.getRows();
        int columns = 6; //grid.getColumns();

        frame = newFrame(rows, columns);
    }
    
    // creates new frame
    public static JFrame newFrame(int rows, int columns) {
        BorderLayout frameLayout = new BorderLayout(0, buttonHeight/2);
        GridLayout gridLayout = new GridLayout(rows, columns);
        BorderLayout menuLayout = new BorderLayout(0, 0);
        gridLayout.setVgap(-5);
        gridLayout.setHgap(-5);

        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization");
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(columns * buttonWidth, (rows + 1) * buttonHeight));
        
        JPanel menu = new JPanel();
        menu.setLayout(menuLayout);
        menu.setSize(columns * buttonWidth, buttonHeight);
        JButton step = new JButton("Step");
        step.setPreferredSize(new Dimension(buttonWidth, buttonHeight/2));
        menu.add(step, BorderLayout.WEST);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        gridPanel.setPreferredSize(new Dimension(columns * buttonWidth, rows * buttonHeight));

        // add tiles to grid
        for (int i = 0; i < columns * rows; i++) {
            gridPanel.add(new MyButton(buttonWidth, buttonHeight));
        }

        frame.add(menu, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        return frame;
    }

    public static void main(String[] args) {
        int[][] path = {{0, 0}, {0, 1}, {0, 2}};
        visualize(new Grid(), path);
        frame.setVisible(true);
    }
}