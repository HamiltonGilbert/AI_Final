import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class Visualization extends Grid {
    private String name;
    private Grid grid;
    private Pathfinder pathfinder;
    // temp holder of what step the visualization is on
    private int stepNum;
    // holds grid of visible tiles
    private VisibleTile[][] tileGrid;
    // tile dimensions
    private int tileWidth;
    private int tileHeight;
    // path to follow
    private ArrayList<Tile> path;
    // visited visible tiles
    private ArrayList<Tile> visualized;
    // Set Tiles to ___ on click
    private String setTiles;

    public Visualization(String name, Grid grid, int width, int height) {
        this.name = name;
        this.grid = grid;
        tileWidth = width;
        tileHeight = height;
        setTiles = null;
        visualize();
    }
    private void visualize() {
        visualized = new ArrayList<Tile>();
        this.stepNum = 0;
        this.pathfinder = new Pathfinder(grid);
        this.path = pathfinder.bfs_pathfinding();
        JFrame frame = newFrame();
        JPanel menu = createMenu();
        JPanel grid = createGrid();
        frame.add(menu, BorderLayout.NORTH);
        frame.add(grid, BorderLayout.CENTER);
        frame.setVisible(true);
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

    public void newSetTiles(String tileName) {
        // if we already have that as our setTiles, make it null instead
        if (setTiles == tileName) {this.setTiles = null;}
        else {this.setTiles = tileName;}
    }

    public void tileClicked(int[] coords) {
        Tile tile = grid.getTile(coords[0], coords[1]);
        // tile isnt the goal or the start
        if (!tile.isGoal() && (tile != grid.getStart())) {
            System.out.println("HIT");
            if (setTiles == "Goal") {
                newGrid(tile.getIndex(), grid.getStart().getIndex());
            }
            if (setTiles == "Start") {
                newGrid(grid.getGoal().getIndex(), tile.getIndex());
            }
        }
    }

    private void newGrid(int[] goalCoords, int[] startCoords) {
        this.grid = new Grid(grid.getRows(), grid.getColumns(), grid.getObstacles(), grid.getKeyTiles(), goalCoords, startCoords);
        visualize();
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


    private JPanel createGrid() {
        GridLayout gridLayout = new GridLayout(this.grid.getRows(), this.grid.getColumns());
        gridLayout.setVgap(1);
        gridLayout.setHgap(1);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(gridLayout);
        gridPanel.setPreferredSize(new Dimension(this.grid.getColumns() * tileWidth, this.grid.getRows() * tileHeight));

        // add tiles to grid
        tileGrid = new VisibleTile[this.grid.getRows()][this.grid.getColumns()];
        for (int i = 0; i < this.grid.getRows(); i++) {
            for (int j = 0; j < this.grid.getColumns(); j++) {
                int[] coords = {i, j};
                VisibleTile tile = new VisibleTile(this, coords,new Dimension(tileWidth, tileHeight));
                // check if obstacle
                if (this.grid.getTile(i, j).isObstacle()) {tile.setObstacle();}
                // check if keyTile
                if (this.grid.getTile(i, j).isKeyTile()) {tile.setKeyTile();}
                // check if goal
                if (this.grid.getTile(i, j).isGoal()) {tile.setGoal(); System.out.println("HIT!!!!!!!!!!!");}
                //add to GridPanel
                tileGrid[i][j] = tile;
                gridPanel.add(tile);
            }
        }

        //set goal
        tileGrid[this.grid.getGoal().getRow()][this.grid.getGoal().getColumn()].setGoal();

        return gridPanel;
    }

    private JPanel createMenu() {
        int buttonHeight = 50;
        int buttonWidth = 100;
        GridLayout menuLayout = new GridLayout(2, 2);
        menuLayout.setVgap(-5);
        menuLayout.setHgap(0);
        JPanel menu = new JPanel(menuLayout);
        menu.setSize(buttonWidth*2, buttonHeight*2);
        StepButton stepBtn = new StepButton(this, new Dimension(buttonWidth, buttonHeight), true);
        StepButton backStepBtn = new StepButton(this, new Dimension(buttonWidth, buttonHeight), false);
        SetTileButton goalBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "Goal");
        SetTileButton startBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "Start");
        menu.add(stepBtn);
        menu.add(goalBtn);
        menu.add(backStepBtn);
        menu.add(startBtn);
        return menu;
    }
    
    // creates new frame
    private JFrame newFrame() {
        BorderLayout frameLayout = new BorderLayout(0, 0);
        JFrame frame = new JFrame();
        frame.setTitle("Pathfinding Visualization: " + name);
        frame.setLayout(frameLayout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(this.grid.getColumns() * tileWidth, (this.grid.getRows()) * tileHeight + 110));
        frame.setResizable(false);
        return frame;
    }
}
