import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class Visualization extends Grid {
    private Grid grid;
    // temp holder of what step the visualization is on
    private int stepNum = 0;
    // holds grid of visible tiles
    private VisibleTile[][] tileGrid;
    // tile dimensions
    private int tileWidth;
    private int tileHeight;
    // path to follow
    private ArrayList<Tile> path;
    // visited visible tiles
    private ArrayList<Tile> visualized;

    public Visualization(Grid grid, ArrayList<Tile> path, int width, int height) {
        this.grid = grid;
        this.path = path;
        tileWidth = width;
        tileHeight = height;
        visualize();
    }
    private void visualize() {
        visualized = new ArrayList<Tile>();
        JFrame frame = newFrame(path);
        frame.setVisible(true);
    }
    
    // creates new frame
    private JFrame newFrame(ArrayList<Tile> path) {
        BorderLayout frameLayout = new BorderLayout(0, 0);
        GridLayout gridLayout = new GridLayout(this.grid.getRows(), this.grid.getColumns());
        BorderLayout menuLayout = new BorderLayout(0, -7);
        gridLayout.setVgap(1);
        gridLayout.setHgap(1);

        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization");
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(this.grid.getColumns() * tileWidth, (this.grid.getRows() + 1) * tileHeight));
        
        JPanel menu = new JPanel();
        menu.setLayout(menuLayout);
        menu.setSize(tileWidth, tileHeight);
        // JPanel buttons = new JPanel();
        // buttons.setLayout(new BorderLayout());
        StepButton step = new StepButton(this, new Dimension(tileWidth, tileHeight/2), true);
        StepButton backStep = new StepButton(this, new Dimension(tileWidth, tileHeight/2), false);
        step.setPreferredSize(new Dimension(tileWidth, tileHeight/2));
        backStep.setPreferredSize(new Dimension(tileWidth, tileHeight/2));
        // buttons.add(step, BorderLayout.SOUTH);
        // buttons.add(backStep, BorderLayout.SOUTH);
        // menu.add(buttons, BorderLayout.WEST);
        menu.add(step, BorderLayout.NORTH);
        menu.add(backStep, BorderLayout.SOUTH);
        JPanel blankSpace = new JPanel();
        blankSpace.setSize(new Dimension(this.grid.getColumns() * tileWidth/2, tileHeight));
        menu.add(new JPanel(), BorderLayout.EAST);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        gridPanel.setPreferredSize(new Dimension(this.grid.getColumns() * tileWidth, this.grid.getRows() * tileHeight));

        // add tiles to grid
        tileGrid = new VisibleTile[this.grid.getRows()][this.grid.getColumns()];
        for (int i = 0; i < this.grid.getRows(); i++) {
            for (int j = 0; j < this.grid.getColumns(); j++) {
                VisibleTile tile = new VisibleTile(new Dimension(tileWidth, tileHeight));
                
                // check if obstacle
                if (this.grid.getTile(i, j).isObstacle()) {tile.setObstacle();}
                // check if keyTile
                if (this.grid.getTile(i, j).isKeyTile()) {tile.setKeyTile();}
                // check if goal
                if (this.grid.getTile(i, j).isGoal()) {tile.setGoal();}
                //add to GridPanel
                tileGrid[i][j] = tile;
                gridPanel.add(tile);
            }
        }
        
        // set obstacles
        for (int i = 0; i < this.grid.getObstacles().length; i++) {
            int row = this.grid.getObstacles()[i][0];
            int column = this.grid.getObstacles()[i][1];
            tileGrid[row][column].setObstacle();
        }

        //set goal
        tileGrid[this.grid.getGoal().getRow()][this.grid.getGoal().getColumn()].setGoal();

        frame.add(menu, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        return frame;
    }

    public void nextBtnHit() {
        if (!path.isEmpty()) {
            path = viewNextTile(path);
        }
    }

    public void backBtnHit() {
        if (!visualized.isEmpty()) {
            path = undoTile(path);
        }
    }
    private ArrayList<Tile> viewNextTile(ArrayList<Tile> path) {
        Tile tile = path.remove(0);
        visualized.add(tile);
        int row = tile.getRow();
        int column = tile.getColumn();

        VisibleTile visibleTile = tileGrid[row][column];
        visibleTile.setVisited(true, stepNum);
        stepNum++;

        return path;
    }
    
    private ArrayList<Tile> undoTile(ArrayList<Tile> path) {
        Tile tile = visualized.remove(visualized.size()-1);
        int row = tile.getRow();
        int column = tile.getColumn();

        VisibleTile visibleTile = tileGrid[row][column];
        visibleTile.setVisited(false, stepNum);
        stepNum--;

        ArrayList<Tile> newPath = new ArrayList<Tile>();
        newPath.add(tile);

        for (int i=0; i < path.size(); i++) {
            newPath.add(path.get(i));
        }

        return newPath;
    }
}
