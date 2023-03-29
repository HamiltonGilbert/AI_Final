public class Grid {
    // public static Tile start;
    public int rows;
    public int columns;
    public Tile start;

    public int[] startCoords;
    public int[][] obstacleLocations;
    public Tile[][] gridTiles;
    public Tile goal;
    public int[] goalCoords;

    // public ArrayList<ArrayList<Integer>> obstacleLocations;
    // public Tile[][] gridTiles;
    // public Tile goal;

    public Grid(int rows, int columns, int[][] obstacleLocations, int[] goalCoords, int[] startCoords) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacleLocations = obstacleLocations;
        this.goalCoords = goalCoords;
        this.startCoords = startCoords;
        init_grid();
    }

    public Grid(int rows, int columns, int[][] obstacleLocations) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacleLocations = obstacleLocations;
        // this.goal = null;
        init_grid();
    }

    public Grid(int rows, int columns) {
        this.rows  = rows;
        this.columns = columns;
        //this.obstacleLocations;
        // this.goal = null;
        init_grid();
    }

    public Grid() {
        this.rows = 0;
        this.columns = 0;
        //this.obstacleLocations;
        // this.goal = null;
        init_grid();
    }

    private void init_grid() {
        gridTiles = new Tile[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                gridTiles[i][j] = new Tile(i,j,false,false);
            }
        }

        // add obstacles
        for (int i = 0; i < obstacleLocations.length; i++) {
            int row = obstacleLocations[i][0];
            int column = obstacleLocations[i][1];
            gridTiles[row][column].setObstacle(true);
        }

        // add goal
        goal = gridTiles[goalCoords[0]][goalCoords[1]];
        //goal.setGoal(true);
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

        public boolean is_obstacle;

        public Tile(int row, int column, boolean is_goal, boolean is_obstacle) {
            this.row = row;
            this.column = column;
            this.on_path = false;
            this.weight = 0;
            this.is_obstacle = is_obstacle;
            
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
        
        public boolean isObstacle() {
            return this.is_obstacle;
        }

        public void setObstacle(boolean b) {
            this.is_obstacle = b;
        }

        public void set_path(boolean on_path){
            this.on_path = on_path;
        }

        public int weight() {
            return this.weight;
        }
        
    }
}