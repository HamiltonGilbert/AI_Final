import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Visualization {
    // temp holder of what step the visualization is on
    private static int stepNum = 0;
    // holds paths
    private static ArrayList<int[]> bfsPath;
    private static ArrayList<int[]> DjikstraPath;
    private static ArrayList<int[]> AStarPath;
    // holds grid of tiles
    private static GridTile[][] tileGrid;
    // tile dimensions
    private static int tileWidth;
    private static int tileHeight;

    public Visualization(Grid grid, ArrayList<int[]> path, int[][] obstacles) {
        visualize(grid, path, obstacles);
    }
    static void visualize(Grid grid, ArrayList<int[]> path, int[][] obstacles) {
        int rows = grid.getRows();
        int columns = grid.getColumns();

        JFrame frame = newFrame(rows, columns, obstacles);
        frame.setVisible(true);
    }
    
    // creates new frame
    public static JFrame newFrame(int rows, int columns, int[][] obstacles) {
        BorderLayout frameLayout = new BorderLayout(0, tileHeight/2);
        GridLayout gridLayout = new GridLayout(rows, columns);
        BorderLayout menuLayout = new BorderLayout(0, 0);
        gridLayout.setVgap(-5);
        gridLayout.setHgap(-5);

        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization");
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(columns * tileWidth, (rows + 1) * tileHeight));
        
        JPanel menu = new JPanel();
        menu.setLayout(menuLayout);
        menu.setSize(columns * tileWidth, tileHeight);
        StepButton step = new StepButton("Step", new Dimension(tileWidth, tileHeight/2));
        step.setPreferredSize(new Dimension(tileWidth, tileHeight/2));
        menu.add(step, BorderLayout.WEST);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        gridPanel.setPreferredSize(new Dimension(columns * tileWidth, rows * tileHeight));

        // add tiles to grid
        tileGrid = new GridTile[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                GridTile tile = new GridTile(new Dimension(tileWidth, tileHeight), false);
                tileGrid[i][j] = tile;
                gridPanel.add(tile);
            }
        }
        
        // set obstacles
        for (int i = 0; i < obstacles.length; i++) {
            int row = obstacles[i][0];
            int column = obstacles[i][1];
            tileGrid[row][column].setObstacle();
        }

        frame.add(menu, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        return frame;
    }

    public static void nextBtnHit() {
        if (!bfsPath.isEmpty()) {
            bfsPath = viewNextTile(bfsPath);
        }
    }

    public static ArrayList<int[]> viewNextTile(ArrayList<int[]> path) {
        int[] pair = path.remove(0);
        int row = pair[0];
        int column = pair[1];

        GridTile tile = tileGrid[row][column];
        tile.setVisited(stepNum);
        stepNum++;

        return path;
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
