import java.util.ArrayList;

public class RoadBuilder extends Grid {
    public static void main(String[] args) {
        // set variables
        int tileHeight = 100;
        int tileWidth = 100;
        int gridRows = 4;
        int gridColumns = 6;
        int[] goal = {3, 5};
        int[] start = {0, 0};

        // choose tiles to be obstacles
        int[][] obstacles = {{2, 2}, {3, 1}, {3, 0}, {1, 5}, {3, 3}, {3, 4}};

        // choose tiles to be keyTiles
        int[][] keyTiles = {{2, 0}, {3, 0}};

        // temp path until using algorithm
        int[][] path = {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}};
        ArrayList<int[]> bfsPath = addAll(path);

        Grid grid = new Grid(gridRows, gridColumns, obstacles, keyTiles, goal, start);
        //Grid grid = new Grid(gridRows, gridColumns);
        
        Visualization vis = new Visualization(grid, tileWidth, tileHeight);

        // GET RID OF ONCE PATHFINDER WORKS
        vis.bfsPath = addAll(path);
    }

    // helper function to convert int[][] to ArrayList<int[]>
    public static ArrayList<int[]> addAll(int[][] toAdd) {
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int i = 0; i < toAdd.length; i++) {
            list.add(toAdd[i]);
        }
        return list;
    }
}