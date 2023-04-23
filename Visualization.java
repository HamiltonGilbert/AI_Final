import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class Visualization extends Grid {
    private String name;
    private Grid grid;
    private Pathfinder pathfinder;
    private JFrame frame = null;
    // temp holder of what step the visualization is on
    // private int stepNum;
    // holds grid of visible tiles
    private VisibleTile[][] tileGrid;
    // tile dimensions
    private int tileWidth;
    private int tileHeight;
    // path to follow
    private ArrayList<Tile> path;
    // visited visible tiles
    private ArrayList<Tile> visualized;
    // Set Tiles to this on click
    private String setTiles;
    // whether mouse is down
    private boolean mouseDown = false;
    // helper for key tile vis
    private int[] moveKeyTile;
    // stored grid data
    private int[] start;
    private int[] goal = {0, 0};
    private ArrayList<int[]> obstacles = new ArrayList<int[]>();
    private ArrayList<int[]> keyTiles = new ArrayList<int[]>();


    public Visualization(String name, Grid grid, int width, int height) {
        this.name = name;
        this.grid = grid;
        this.tileWidth = width;
        this.tileHeight = height;
        this.setTiles = null;
        this.start = grid.getStart().getIndex();
        visualize();
    }
    private void visualize() {
        int[][] temp = this.grid.getObstacles();
        if (temp.length != 0) {
            for (int i=0; i<temp.length; i++) {
            this.obstacles.add(temp[i]);
            }
        }
        temp = this.grid.getKeyTiles();
        if (temp.length != 0) {
            for (int i=0; i<temp.length; i++) {
                this.keyTiles.add(temp[i]);
            }
        }

        visualized = new ArrayList<Tile>();
        // this.stepNum = 0;
        JFrame frame = newFrame();
        JPanel menu = createMenu();
        JPanel grid = createGrid();
        frame.add(menu, BorderLayout.NORTH);
        frame.add(grid, BorderLayout.CENTER);
        frame.setVisible(true);
        // dispose old frame
        if (this.frame != null) {
            this.frame.dispose();
        }
        this.frame = frame;
    }

    private void createPath() {
        newGrid();
        this.pathfinder = new Pathfinder(grid);
        if (this.name == "A*") {this.path = pathfinder.aStar_pathfinding();}
        if (this.name == "BFS") {this.path = pathfinder.bfs_pathfinding();}
        else {this.path = pathfinder.dijkstra_pathfinding();}
    }
    private void clearPath() {
        while (!visualized.isEmpty()) {
            path = undoTile(path);
        }
    }

    public void runBtnHit() {
        System.out.println("HIT!");
        clearPath();
        createPath();
        while (!path.isEmpty()) {
            path = viewNextTile(path);
        }
    }

    public void newSetTiles(String tileName) {
        // if we already have that as our setTiles, make it null instead
        // if (setTiles == tileName) {this.setTiles = null;}
        // else {this.setTiles = tileName;}
        this.setTiles = tileName;
    }

    // public void tileClicked(int[] coords) {
    //     // tile isnt the goal or the start
    //     if (coords != this.goal && coords != this.start) {
    //         if (setTiles == "Goal") {
    //             this.tileGrid[this.goal[0]][this.goal[1]].setRegularTile();
    //             this.tileGrid[coords[0]][coords[1]].setGoal();
    //             this.goal = coords;
    //         }
    //         if (setTiles == "Start") {
    //             this.start = coords;
    //         }
    //         if (setTiles == "Obstacle") {
    //             if (!obstacles.contains(coords)) {
    //                 this.obstacles.add(coords);
    //                 this.tileGrid[coords[0]][coords[1]].setObstacle();
    //             }
    //         }
    //         if (setTiles == "KeyTile") {
    //             if (!keyTiles.contains(coords)) {
    //                 this.keyTiles.add(coords);
    //                 this.tileGrid[coords[0]][coords[1]].setKeyTile();
    //             }
    //         }
    //     }
    // }

    public void mouseDown(int[] coords) {
        this.mouseDown = true;
        mouseOver(coords);
    }
    public void mouseDown() {
        this.mouseDown = false;
        this.moveKeyTile = null;
    }

    public void mouseOver(int[] coords) {
        if (mouseDown) {
            // tile isnt the the start or a keyTile
            if (coords != this.start) {
                if (setTiles == "Start") {
                    this.tileGrid[this.start[0]][this.start[1]].setRegularTile();
                    this.tileGrid[coords[0]][coords[1]].setStart();
                    this.start = coords;
                }
                if (setTiles == "Obstacle" && !keyTiles.contains(coords)) {
                    if (!obstacles.contains(coords)) {
                        this.obstacles.add(coords);
                        this.tileGrid[coords[0]][coords[1]].setObstacle();
                    }
                }
                if (setTiles == "KeyTile") {
                    if (keyTiles.contains(coords)) {
                        moveKeyTile = coords;
                    }
                    if (this.moveKeyTile != null) {
                        this.tileGrid[this.moveKeyTile[0]][this.moveKeyTile[1]].setRegularTile();
                        this.keyTiles.remove(this.moveKeyTile);
                    }
                    this.moveKeyTile = coords;
                    this.keyTiles.add(coords);
                    this.tileGrid[coords[0]][coords[1]].setKeyTile();
                }
                if (setTiles == "Erase") {
                    if (obstacles.contains(coords)) {
                        obstacles.remove(coords);
                        this.tileGrid[coords[0]][coords[1]].setRegularTile();
                    }
                    if (keyTiles.contains(coords)) {
                        keyTiles.remove(coords);
                        this.tileGrid[coords[0]][coords[1]].setRegularTile();
                    }
                }
            }    
        }
    }

    private void newGrid() {
        this.grid = new Grid(grid.getRows(), grid.getColumns(), this.obstacles.toArray(new int[obstacles.size()][2]), this.keyTiles.toArray(new int[keyTiles.size()][2]), this.goal, this.start);
        visualize();
    }

    private ArrayList<Tile> viewNextTile(ArrayList<Tile> path) {
        Tile tile = path.remove(0);
        visualized.add(tile);
        int row = tile.getRow();
        int column = tile.getColumn();

        VisibleTile visibleTile = this.tileGrid[row][column];
        visibleTile.setVisited(true, "*");
        // stepNum++;

        return path;
    }
    private ArrayList<Tile> undoTile(ArrayList<Tile> path) {
            Tile tile = visualized.remove(visualized.size()-1);
            int row = tile.getRow();
            int column = tile.getColumn();
        
            VisibleTile visibleTile = this.tileGrid[row][column];
            visibleTile.setVisited(false, "*");
            // stepNum--;
        
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
        this.tileGrid = new VisibleTile[this.grid.getRows()][this.grid.getColumns()];
        for (int i = 0; i < this.grid.getRows(); i++) {
            for (int j = 0; j < this.grid.getColumns(); j++) {
                int[] coords = {i, j};
                VisibleTile tile = new VisibleTile(this, coords,new Dimension(tileWidth, tileHeight));
                // check if obstacle
                if (this.grid.getTile(i, j).isObstacle()) {tile.setObstacle();}
                // check if keyTile
                if (this.grid.getTile(i, j).isKeyTile()) {tile.setKeyTile();}
                //add to GridPanel
                this.tileGrid[i][j] = tile;
                gridPanel.add(tile);
            }
        }
        // add start
        this.tileGrid[this.grid.getStart().getRow()][this.grid.getStart().getColumn()].setStart();

        return gridPanel;
    }

    private JPanel createMenu() {
        int buttonHeight = 50;
        int buttonWidth = 100;
        GridLayout menuLayout = new GridLayout(2, 3);
        menuLayout.setVgap(-5);
        menuLayout.setHgap(0);
        JPanel menu = new JPanel(menuLayout);
        menu.setSize(buttonWidth*2, buttonHeight*2);
        RunButton runBtn = new RunButton(this, new Dimension(buttonWidth, buttonHeight));
        SetTileButton startBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "Start");
        SetTileButton obstacleBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "Obstacle");
        SetTileButton keyTileBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "KeyTile");
        SetTileButton eraseBtn = new SetTileButton(this, new Dimension(buttonWidth, buttonHeight), "Erase");
        menu.add(runBtn);
        menu.add(startBtn);
        menu.add(obstacleBtn);
        menu.add(eraseBtn);
        menu.add(keyTileBtn);
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
