public class RoadBuilder {
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
        int[][] keyTiles = {{2, 0}, {0, 3}};

        // temp path until using algorithm
        // int[][] path = {{0, 0}, {0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}};

        Grid grid = new Grid(gridRows, gridColumns, obstacles, keyTiles, goal, start);

        Pathfinder pathfinder = new Pathfinder(grid);

        // visualize bfs
        // new Visualization(grid, pathfinder.bfs_pathfinding(), tileWidth, tileHeight);
        new Visualization(grid, pathfinder.dijkstra_pathfinding(), tileWidth, tileHeight);

        // // visualize dijkstra
        // vis.visualize(pathfinder.dijkstra_pathfinding());
        // // visualize a*
        // new Visualization(grid, pathfinder.aStar_pathfinding(), tileWidth, tileHeight);
    }
}