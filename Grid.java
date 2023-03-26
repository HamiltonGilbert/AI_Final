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
