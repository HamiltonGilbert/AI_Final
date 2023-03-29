import java.util.ArrayList;

/*  
FOR KENNA:
What we need:

Grid: (either function or variable with getters and setters)
rows (int), columns (int), start (Tile), tileArray (Tile[][]), 
neighbors (Tile[])

Tile: (either function or variable with getters and setters)
row (int), column (int), isGoal (bool), isObstacle (bool)

these are just the ones I can think of, feel free to add others you need
but follow the same naming convention so they are easy to access
*/

public class Grid {
    // public static Tile start;
    public int rows;
    public int columns;
    public Tile start;
    public ArrayList<ArrayList<Integer>> obstacleLocations;
    public Tile[][] gridTiles;
    public Tile goal;

    public Grid(int rows, int columns, ArrayList<ArrayList<Integer>> obstacleLocations, Tile goal) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacleLocations = obstacleLocations;
        this.goal = goal;
        init_grid();
    }

    public Grid(int rows, int columns, ArrayList<ArrayList<Integer>> obstacleLocations) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacleLocations = obstacleLocations;
        this.goal = null;
        init_grid();
    }

    public Grid(int rows, int columns) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacleLocations = new ArrayList<ArrayList<Integer>>();
        this.goal = null;
        init_grid();
    }

    public Grid() {
        this.rows = 0;
        this.columns = 0;
        this.obstacleLocations = new ArrayList<ArrayList<Integer>>();
        this.goal = null;
        init_grid();
    }

    private void init_grid() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 1; j < getColumns(); j++) {
                ArrayList<Integer> currentCoord = new ArrayList<Integer>();
                currentCoord.add(i,j);
                if (this.obstacleLocations.contains(currentCoord)) {        //TODO check if logically correct
                    this.gridTiles[i][j] = new Tile(i,j,false,true);
                } else {
                    if ((this.goal.getRow() == i) && (this.goal.getColumn() == j)) {
                        this.gridTiles[i][j] = new Tile(i,j,true,false);
                    }
                    this.gridTiles[i][j] = new Tile(i,j,false,false);
                }
            }
        }
    }

    public static Tile[] neighbors() {
        return null;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Tile getTile(int row, int column) {
        return gridTiles[row][column];
    }

    public void setGoal(Tile goal) {
        this.goal = goal;
    }

    public Tile getGoal() {
        return this.goal;
    }



    public class Tile {
        public int row;
        public int column;
        public int weight; //TODO how is weight determined?
        public boolean on_path;

        public Tile(int row, int column, boolean is_goal, boolean is_obstacle) {
            this.row = row;
            this.column = column;
            this.on_path = false;
            this.weight = 0;
            
        }

        public int getRow() {
            return row;
        }
        public int getColumn() {
            return column;
        }

        public int[][] getIndex() {
            return null;
            // TODO (ask Korinne)
        }

        public boolean isGoal() {
            return isGoal();
        }
        
        public void setGoal(bool b) {
            isGoal = b;
        }
        
        public boolean isObstacle() {
            return isObstacle();
        }
        
        public void setObstacle(bool b) {
            isObstacle = b;
        }

        public void set_path(boolean on_path){
            this.on_path = on_path;
        }

        public int weight() {
            return this.weight;
        }
        
    }
}
