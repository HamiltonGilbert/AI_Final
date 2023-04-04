import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.ArrayList;

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

    private static ArrayList<int[]> visualized;

    public Visualization(Grid grid, ArrayList<int[]> newBFSPath, int[][] obstacles, int width, int height) {
        tileWidth = width;
        tileHeight = height;
        bfsPath = newBFSPath;
        visualized = new ArrayList<int[]>();
        visualize(grid, bfsPath, obstacles);
    }
    static void visualize(Grid grid, ArrayList<int[]> path, int[][] obstacles) {
        int rows = grid.getRows();
        int columns = grid.getColumns();

        JFrame frame = newFrame(rows, columns, obstacles);
        frame.setVisible(true);
    }
    
    // creates new frame
    public static JFrame newFrame(int rows, int columns, int[][] obstacles) {
        BorderLayout frameLayout = new BorderLayout(0, 0);
        GridLayout gridLayout = new GridLayout(rows, columns);
        BorderLayout menuLayout = new BorderLayout(0, -10);
        gridLayout.setVgap(-5);
        gridLayout.setHgap(-5);

        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization");
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(columns * tileWidth, (rows + 1) * tileHeight));
        
        JPanel menu = new JPanel();
        menu.setLayout(menuLayout);
        menu.setSize(tileWidth, tileHeight);
        // JPanel buttons = new JPanel();
        // buttons.setLayout(new BorderLayout());
        StepButton step = new StepButton(new Dimension(tileWidth, tileHeight/2), true);
        StepButton backStep = new StepButton(new Dimension(tileWidth, tileHeight/2), false);
        step.setPreferredSize(new Dimension(tileWidth, tileHeight/2));
        backStep.setPreferredSize(new Dimension(tileWidth, tileHeight/2));
        // buttons.add(step, BorderLayout.SOUTH);
        // buttons.add(backStep, BorderLayout.SOUTH);
        // menu.add(buttons, BorderLayout.WEST);
        menu.add(step, BorderLayout.NORTH);
        menu.add(backStep, BorderLayout.SOUTH);
        JPanel blankSpace = new JPanel();
        blankSpace.setSize(new Dimension(columns * tileWidth/2, tileHeight));
        menu.add(new JPanel(), BorderLayout.EAST);

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

    public static void backBtnHit() {
        if (!visualized.isEmpty()) {
            bfsPath = undoTile(bfsPath);
        }
    }
    public static ArrayList<int[]> viewNextTile(ArrayList<int[]> path) {
        int[] pair = path.remove(0);
        visualized.add(pair);
        int row = pair[0];
        int column = pair[1];

        GridTile tile = tileGrid[row][column];
        tile.setVisited(true, stepNum);
        stepNum++;

        return path;
    }
    
    public static ArrayList<int[]> undoTile(ArrayList<int[]> path) {
        int[] pair = visualized.remove(visualized.size()-1);
        int row = pair[0];
        int column = pair[1];

        GridTile tile = tileGrid[row][column];
        tile.setVisited(false, stepNum);
        stepNum--;

        ArrayList<int[]> newPath = new ArrayList<int[]>();
        newPath.add(pair);

        for (int i=0; i < path.size(); i++) {
            newPath.add(path.get(i));
        }

        return newPath;
    }
}
