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
    public static Tile start;
    public static int rows;
    public static int columns;

    public static Tile[] neighbors() {
        return null;
    }

    public void set_goal() {

    }

    public class Tile {
        public static int row;
        public static int column;
        public Tile(int row, int column, boolean is_goal, boolean is_obstacle) {

        }

        public int getRow() {
            return row;
        }
        public int getColumn() {
            return row;
        }

        
    }
}
