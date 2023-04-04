import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class Visualization {
    private static Grid grid;
    // temp holder of what step the visualization is on
    private static int stepNum = 0;
    // holds paths
    // MAKE PRIVATE ONCE PATHFINDER WORKS
    public static ArrayList<int[]> bfsPath;
    private static ArrayList<int[]> DjikstraPath;
    private static ArrayList<int[]> AStarPath;
    // holds grid of visible tiles
    private static VisibleTile[][] tileGrid;
    // tile dimensions
    private static int tileWidth;
    private static int tileHeight;
    // visited visible tiles
    private static ArrayList<int[]> visualized;

    public Visualization(Grid newGrid, int width, int height) {
        grid = newGrid;
        tileWidth = width;
        tileHeight = height;
        visualized = new ArrayList<int[]>();
        visualize();
    }
    static void visualize() {
        int rows = grid.getRows();
        int columns = grid.getColumns();

        JFrame frame = newFrame();
        frame.setVisible(true);
    }
    
    // creates new frame
    public static JFrame newFrame() {
        BorderLayout frameLayout = new BorderLayout(0, 0);
        GridLayout gridLayout = new GridLayout(grid.getRows(), grid.getColumns());
        BorderLayout menuLayout = new BorderLayout(0, -7);
        gridLayout.setVgap(2);
        gridLayout.setHgap(2);

        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization");
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(grid.getColumns() * tileWidth, (grid.getRows() + 1) * tileHeight));
        
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
        blankSpace.setSize(new Dimension(grid.getColumns() * tileWidth/2, tileHeight));
        menu.add(new JPanel(), BorderLayout.EAST);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        gridPanel.setPreferredSize(new Dimension(grid.getColumns() * tileWidth, grid.getRows() * tileHeight));

        // add tiles to grid
        tileGrid = new VisibleTile[grid.getRows()][grid.getColumns()];
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                VisibleTile tile = new VisibleTile(new Dimension(tileWidth, tileHeight));
                
                // check if obstacle
                if (grid.getTile(i, j).isObstacle()) {tile.setObstacle();}
                // check if goal
                if (grid.getTile(i, j).isGoal()) {tile.setGoal();}
                //add to GridPanel
                tileGrid[i][j] = tile;
                gridPanel.add(tile);
            }
        }
        
        // set obstacles
        for (int i = 0; i < grid.getObstacles().length; i++) {
            int row = grid.getObstacles()[i][0];
            int column = grid.getObstacles()[i][1];
            tileGrid[row][column].setObstacle();
        }

        //set goal
        tileGrid[grid.getGoal().getRow()][grid.getGoal().getColumn()].setGoal();

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

        VisibleTile tile = tileGrid[row][column];
        tile.setVisited(true, stepNum);
        stepNum++;

        return path;
    }
    
    public static ArrayList<int[]> undoTile(ArrayList<int[]> path) {
        int[] pair = visualized.remove(visualized.size()-1);
        int row = pair[0];
        int column = pair[1];

        VisibleTile tile = tileGrid[row][column];
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
