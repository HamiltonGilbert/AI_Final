import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
        //ArrayList<int[]> obstaclesList = addAll(obstacles);

        // temp until using algorithm
        int[][] path = {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}};
        ArrayList<int[]> bfsPath = addAll(path);

        Grid grid = new Grid(gridRows, gridColumns, obstacles, goal, start);
        //Grid grid = new Grid(gridRows, gridColumns);
        
        new Visualization(grid, bfsPath, obstacles, tileWidth, tileHeight);

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