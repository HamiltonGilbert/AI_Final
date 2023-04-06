public class Grid {
    // private static Tile start;
    private int rows;
    private int columns;
    private Tile start;

    private int[] startCoords;
    private int[][] obstacles;
    private int[][] keyTiles;
    private Tile[][] gridTiles;
    private Tile goal;
    private int[] goalCoords;

    // private ArrayList<ArrayList<Integer>> obstacleLocations;
    // private Tile[][] gridTiles;
    // private Tile goal;

    public Grid(int rows, int columns, int[][] obstacles, int[][] keyTiles, int[] goalCoords, int[] startCoords) {
        this.rows  = rows;
        this.columns = columns;
        this.obstacles = obstacles;
        this.keyTiles = keyTiles;
        this.goalCoords = goalCoords;
        this.startCoords = startCoords;
        init_grid();
    }

    // public Grid(int rows, int columns, int[][] obstacleLocations) {
    //     this.rows  = rows;
    //     this.columns = columns;
    //     this.obstacles = obstacleLocations;
    //     // this.goal = null;
    //     init_grid();
    // }

    // public Grid(int rows, int columns) {
    //     this.rows  = rows;
    //     this.columns = columns;
    //     //this.obstacleLocations;
    //     // this.goal = null;
    //     init_grid();
    // }

    public Grid() {
        this.rows = 0;
        this.columns = 0;
        //this.obstacleLocations;
        // this.goal = null;
    }

    private void init_grid() {
        gridTiles = new Tile[getRows()][getColumns()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                gridTiles[i][j] = new Tile(i,j,false,false);
            }
        }

        // add obstacles
        for (int i = 0; i < obstacles.length; i++) {
            int row = obstacles[i][0];
            int column = obstacles[i][1];
            gridTiles[row][column].setObstacle(true);
        }

        // add keyTiles
        for (int i = 0; i < keyTiles.length; i++) {
            int row = keyTiles[i][0];
            int column = keyTiles[i][1];
            gridTiles[row][column].setKeyTile(true);
        }

        // add goal
        this.goal = gridTiles[goalCoords[0]][goalCoords[1]];
        // add start
        this.start = gridTiles[startCoords[0]][startCoords[1]];
        //goal.setGoal(true);
    }

    public Tile[] getNeighbors(Tile nucleus) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Tile[] neighbors = new Tile[4];

        for (int i = 0; i < 4; i++) {
            if (!((nucleus.row + directions[i][0] < 0 || nucleus.row + directions[i][0] > rows) || 
                (nucleus.column + directions[i][1] < 0 || nucleus.column + directions[i][1] > columns))) {
                    if (gridTiles[nucleus.row + directions[i][0]][nucleus.column + directions[i][1]] != null) { //testing to make sure the tile has values
                        neighbors[i] = gridTiles[nucleus.row + directions[i][0]][nucleus.column + directions[i][1]];
                    }
            }
                
        } 
        return neighbors;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public Tile getStartTile() {
        return this.start;
    }

    public Tile getGoalTile() {
        return this.goal;
    }

    public Tile getTile(int row, int column) {
        return gridTiles[row][column];
    }
    
    public Tile[][] getGridTiles() {
        return gridTiles;
    }

    public void setGoal(int[] goalCoords) {
        this.goal = new Tile(goalCoords[0], goalCoords[1], true);
    }


    public Tile getGoal() {
        return this.goal;
    }

    public int[][] getObstacles() {
        return this.obstacles;
    }


    public class Tile {

        private int row;
        private int column;
        private int weight; //TODO how is weight determined?
        private boolean on_path;
        private boolean is_goal;
        private boolean is_obstacle;
        private boolean is_key_tile;
        private Tile parent;

        public Tile(int row, int column, boolean is_goal, boolean is_obstacle, int weight) { //TODO eventually add weight as param and parent?
            this.row = row;
            this.column = column;
            this.is_goal = is_goal;
            this.is_obstacle = is_obstacle;
            this.on_path = false;
            this.weight = weight;
            
        }

        public Tile(int row, int column, boolean is_goal, boolean is_obstacle) {
            this.row = row;
            this.column = column;
            this.is_obstacle = is_obstacle;
            this.on_path = false;
            this.weight = 0;
            
        }

        public Tile(int row, int column, boolean is_goal) {
            this.row = row;
            this.column = column;
            this.on_path = false;
            this.weight = 0;
            this.is_obstacle = false;
            
        }

        public int getRow() {
            return row;
        }
        public int getColumn() {
            return column;
        }

        public int[] getIndex() {
            int[] index = {this.row, this.column};
            return index;
        }

        public boolean isGoal() {
            return this.is_goal;
        }
        
        public boolean isObstacle() {
            return this.is_obstacle;
        }

        public void setObstacle(boolean b) {
            this.is_obstacle = b;
        }

        public void setKeyTile(boolean b) {
            this.is_key_tile = b;
        }

        public boolean isKeyTile() {
            return this.is_key_tile;
        }

        public boolean getOnPath() {
            return this.on_path;
        }

        public void setOnPath(boolean on_path){
            this.on_path = on_path;
        }

        public void setParent(Tile parent) {
            this.parent = parent;
        }

        public Tile getParent() {
            return this.parent;
        }

        public int weight() {
            return this.weight;
        }
        
    }
}