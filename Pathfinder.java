import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;


public class Pathfinder extends Grid {
    private Grid grid;
    private Tile start;
    private Tile goal;

    public Pathfinder(Grid unsolvedGrid) {
        grid = unsolvedGrid;
        start = grid.getStartTile();
        goal = grid.getGoal();
    }

    //BFS
    public ArrayList<Tile> bfs_pathfinding() {
        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> queue = new ArrayList<>();
        ArrayList<Tile> path = new ArrayList<>();
        Tile currentTile = start;
        int keyTileIndex = grid.getKeyTilesArray().size()-1;

        

        queue.add(currentTile);
        currentTile.setParent(null);
        
        while (keyTileIndex>=0) {
            while (!queue.isEmpty()) {
                if ((!visited.contains(currentTile))) {
                    if (currentTile.equals(grid.getKeyTilesArray().get(keyTileIndex))) { //If the goal has been found  //TODO needs to change to keyTile
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
                        if (keyTileIndex == 0) {
                            return path;
                        } else {
                            queue.clear();
                            visited.clear();
                            queue.add(currentTile);
                            keyTileIndex -= 1;
                            // continue;
                        }
                    }
                    // } else if (currentTile.equals(grid.getKeyTilesArray().get(keyTileIndex))) { //keyTile has been found
                    //     System.out.println("FOUND IT");
                    //     System.out.println("(" + currentTile.getRow() + "," + currentTile.getColumn() + ")");
                    //     queue.clear();
                    //     visited.clear();
                    //     queue.add(currentTile);
                    //     keyTileIndex += 1;
                    // } 
    
                        // System.out.println("Current Tile: (" + currentTile.getRow() + "," + currentTile.getColumn() + ")");
                    
                        Tile[] neighbors = grid.getNeighborsBFS(currentTile);
                        // System.out.println("queue is not empty");
    
                        for (int i = 0; i < neighbors.length; i ++){
                            if ((neighbors[i] != null) && (!visited.contains(neighbors[i]))) {
                                if (neighbors[i].getParent() == null){
                                    neighbors[i].setParent(currentTile);
                                }
                                
                                // System.out.println("adding (" + currentTile.getRow() + "," + currentTile.getColumn() + ") to the queue");
                                queue.add(neighbors[i]);
    
                                Tile[] childNeighbors = grid.getNeighborsBFS(neighbors[i]);    //the neighbors of the child Node of currentTile
    
                                for (int j = 0; j < childNeighbors.length; j++) {
                                    if ((childNeighbors[j] != null) && (!visited.contains(childNeighbors[j])) && (!queue.contains(childNeighbors[j]))) {
                                        if (childNeighbors[j].getParent() == null) {
                                            childNeighbors[j].setParent(neighbors[i]);
                                        }
                                        
                                        // System.out.println("(" + childNeighbors[j].getRow() + "," + childNeighbors[j].getColumn() + ")\'s parent is (" + neighbors[i].getRow() + "," + neighbors[i].getColumn() + ")");
                                        queue.add(childNeighbors[j]);
                                    }
                                }
                            }
                        }
                    
                    //goal used to be here
                
                    visited.add(currentTile);
                }
                currentTile = queue.remove(0);
                
            }
        }
        

        System.out.println("The goal was never found");
        System.out.println(path);
        return path;

    }

    // heuristic function (manhattan distance)
    private double heuristic(Tile tile1, Tile tile2) {
        return Math.abs(tile1.getRow() - tile2.getRow()) + Math.abs(tile1.getColumn() - tile2.getColumn());
    }

    // Dijkstra's Algorithm
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

    // A* Search
    public ArrayList<Tile> aStar_pathfinding() {

        int m = grid.getRows(); // number of rows
        int n = grid.getColumns(); // number of columns
        
        ArrayList<Tile> path = new ArrayList<>();
        PriorityQueue<Tile> queue = new PriorityQueue<Tile>(new AStarComparator()); // priority queue to hold unvisited Tiles
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
    
    class AStarComparator implements Comparator<Tile> {
        public int compare(Tile t1,Tile t2) {  
            Tile tile1=(Tile)t1;  
            Tile tile2=(Tile)t2;  
              
            if ((heuristic(tile1, goal) + tile1.getPathWeight()) == (heuristic(tile2, goal) + tile2.getPathWeight())) {
                return 0; 
            } else if ((heuristic(tile1, goal) + tile1.getPathWeight()) > (heuristic(tile2, goal) + tile2.getPathWeight())) {
                return 1;
            } else {
                return -1;
            }
        }   
    }

}

