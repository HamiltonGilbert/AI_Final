import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class Pathfinder extends Grid {
    private Grid grid;
    private Tile start;
    private Tile goal;

    public Pathfinder(Grid unsolvedGrid) {
        grid = unsolvedGrid;
        start = grid.getStartTile();
        goal = grid.getGoal();
    }

    //new bfs
    public ArrayList<Tile> bfs_pathfinding() {
        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> queue = new ArrayList<>();
        ArrayList<Tile> path = new ArrayList<>();
        Tile currentTile = start;
        

        queue.add(currentTile);
        currentTile.setParent(null);
        
        while (!queue.isEmpty()) {
            if ((!visited.contains(currentTile))) {
                if (currentTile.equals(goal)) { //If the goal has been found
                    System.out.println("FOUND IT");

                    Tile onThePath = currentTile;
                    System.out.println("(" + onThePath.getRow() + "," + onThePath.getColumn() + ")");
                    while (true) {
                        if (path.contains(onThePath)) {
                            break;
                        }
                       
                        path.add(0, onThePath);
                        onThePath.setOnPath(true);
                        if (onThePath.getParent() != null) {
                            onThePath = onThePath.getParent();
                        } 
                    }
                    return path;
                } else {

                    System.out.println("Current Tile: (" + currentTile.getRow() + "," + currentTile.getColumn() + ")");
                
                    Tile[] neighbors = grid.getNeighbors(currentTile);
                    // System.out.println("queue is not empty");

                    for (int i = 0; i < neighbors.length; i ++){
                        if ((neighbors[i] != null) && (!visited.contains(neighbors[i]))) {
                            if (neighbors[i].getParent() == null){
                                neighbors[i].setParent(currentTile);
                            }
                            
                            // System.out.println("adding (" + currentTile.getRow() + "," + currentTile.getColumn() + ") to the queue");
                            queue.add(neighbors[i]);

                            Tile[] childNeighbors = grid.getNeighbors(neighbors[i]);    //the neighbors of the child Node of currentTile

                            for (int j = 0; j < childNeighbors.length; j++) {
                                if ((childNeighbors[j] != null) && (!visited.contains(childNeighbors[j])) && (!queue.contains(childNeighbors[j]))) {
                                    if (childNeighbors[j].getParent() == null) {
                                        childNeighbors[j].setParent(neighbors[i]);
                                    }
                                    
                                    System.out.println("(" + childNeighbors[j].getRow() + "," + childNeighbors[j].getColumn() + ")\'s parent is (" + neighbors[i].getRow() + "," + neighbors[i].getColumn() + ")");
                                    queue.add(childNeighbors[j]);
                                }
                            }
                        }
                    }
                }
                //goal used to be here
            
                visited.add(currentTile);
            }
            currentTile = queue.remove(0);
            
        }

        System.out.println("The goal was never found");
        System.out.println(path);
        return path;

    }

    // heuristic function (manhattan distance)
    private double heuristic(Tile tile1, Tile tile2) {
        return Math.abs(tile1.getRow() - tile2.getRow()) + Math.abs(tile1.getColumn() - tile2.getColumn());
    }


    public ArrayList<Tile> dijkstra_pathfinding() {
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        
        ArrayList<Tile> path = new ArrayList<>();
        PriorityQueue<Tile> queue = new PriorityQueue<Tile>(new PathWeightComparator()); // priority queue to hold unvisited Tiles
        ArrayList<Tile> visited = new ArrayList<>();

        Tile currentTile = start;

        for (int i = 0; i < m; i ++) {  //initializing the cost to visit all unvisited Tiles
            for (int j = 0; j < n; j++) {
                grid.getTile(i,j).setPathWeight(Integer.MAX_VALUE);
                queue.add(grid.getTile(i,j));
            }
        }
        grid.getTile(0,0).setPathWeight(0);

        while (!queue.isEmpty()) { 
            if (currentTile.equals(goal)) { //If the goal has been found
                System.out.println("FOUND IT");

                Tile onThePath = currentTile;
                System.out.println("(" + onThePath.getRow() + "," + onThePath.getColumn() + ")");
                System.out.println("The total path cost is " + goal.getPathWeight());
                
                while (true) {
                    if (path.contains(onThePath)) {
                        break;
                    }
                   
                    path.add(0, onThePath);
                    onThePath.setOnPath(true);
                    if (onThePath.getParent() != null) {
                        onThePath = onThePath.getParent();
                    } 
                }
                return path;
            }
            
            Tile[] neighbors = grid.getNeighbors(currentTile);
            System.out.println("the current tile is (" + currentTile.getRow() + "," + currentTile.getColumn() + ")");


            for (int i = 0; i < 4; i ++) {
                if (neighbors[i] != null) {
                    
                    int temp_path_weight = currentTile.getPathWeight() + neighbors[i].weight();

                    if (!visited.contains(neighbors[i])) {
                        if (queue.contains(neighbors[i])){
                            if (temp_path_weight < neighbors[i].getPathWeight()) {
                                neighbors[i].setParent(currentTile);
                                neighbors[i].setPathWeight(temp_path_weight);
                                queue.add(neighbors[i]);
                            } 
                        } else {
                            neighbors[i].setParent(currentTile);
                            neighbors[i].setPathWeight(temp_path_weight);
                            queue.add(neighbors[i]);
                        }
                    }
                }
            }

            visited.add(currentTile);
            currentTile = queue.poll();

            while (visited.contains(currentTile)) {
                try {
                    currentTile = queue.poll();
                } catch(Exception e) {
                    System.out.println("The queue is empty");
                    break;
                }
                
            }

        }
        


        System.out.println("The path was never found");
        return path;
    }

    // A*  
    public void aStar_pathfinding() {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // array of direction vectors
        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        // int[][] parent = new int[m][n]; // array to keep track of the parent of each visited node
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

    class PathWeightComparator implements Comparator<Tile> {
        public int compare(Tile t1,Tile t2) {  
            Tile tile1=(Tile)t1;  
            Tile tile2=(Tile)t2;  
              
            if(tile1.getPathWeight()==tile2.getPathWeight())  
                return 0;  
            else if(tile1.getPathWeight()>tile2.getPathWeight())  
                return 1;  
            else  
                return -1;  
        } 
    } 
    
    class DistanceComparator implements Comparator<Tile> {
        public int compare(Tile t1,Tile t2) {  
            Tile tile1=(Tile)t1;  
            Tile tile2=(Tile)t2;  
              
            if (heuristic(tile1, goal) == heuristic(tile2, goal)) {
                return 0; 
            } else if (heuristic(tile1, goal) > heuristic(tile2, goal)) {
                return 1;
            } else {
                return -1;
            }
        }   
    }
}

