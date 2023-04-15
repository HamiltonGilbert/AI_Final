public class RoadBuilder {
    public static void main(String[] args) {
        // set variables
        int tileHeight = 100;
        int tileWidth = 100;
        int gridRows1 = 4;
        int gridColumns1 = 6;
        int gridRows2 = 5;
        int gridColumns2 = 8;

        int[] goal1 = {6, 4};
        int[] goal2 = {6, 4};
        int[] goal3 = {6, 4};
        int[] start1 = {1, 1};
        int[] start2 = {1, 1};
        int[] start3 = {1, 1};

        // choose tiles to be obstacles
        int[][] obstacles1 = {{2, 1}, {2, 3}, {3, 2}, {3, 3}, {5, 4}, {5, 3}};
        int[][] obstacles2 = {{2, 1}, {5, 4}, {5, 3}, {3, 3}, {1, 3}, {3, 2}, {5, 2}};
        int[][] obstacles3 = {{2, 1}, {5, 2}, {3, 3}, {3, 4}, {5, 3}};

        // choose tiles to be keyTiles
        int[][] keyTiles1 = {{3, 1}, {5, 1}};
        int[][] keyTiles2 = {{3, 1}, {1, 4}};
        int[][] keyTiles3 = {{3, 1}, {1, 4}};

        Grid grid1 = new Grid(gridRows1, gridColumns1, makeCoords(obstacles1), makeCoords(keyTiles1), makeCoords(goal1), makeCoords(start1));
        Grid grid2 = new Grid(gridRows1, gridColumns1, makeCoords(obstacles2), makeCoords(keyTiles2), makeCoords(goal2), makeCoords(start2));
        Grid grid3 = new Grid(gridRows2, gridColumns2, makeCoords(obstacles3), makeCoords(keyTiles3), makeCoords(goal3), makeCoords(start3));

        // visualize
        new Visualization("BFS", grid1, tileWidth, tileHeight);
        // new Visualization("Djikstra", grid1, pathfinder1.dijkstra_pathfinding(), tileWidth, tileHeight);
        // new Visualization(grid2, pathfinder2.bfs_pathfinding(), tileWidth, tileHeight);
        // new Visualization("A*", grid3, pathfinder3.aStar_pathfinding(), tileWidth, tileHeight);

        // // visualize dijkstra
        // vis.visualize(pathfinder.dijkstra_pathfinding());
        // // visualize a*
        // vis.visualize(pathfinder.aStar_pathfinding());
    }

    // helper functions to make it visually easier manually setting coords
    public static int[][] makeCoords(int[][] list) {
        int[][] newList = new int[list.length][2];
        for (int i=0; i < list.length; i++) {
            newList[i] = new int[] {list[i][1]-1, list[i][0]-1};
        }
        return newList;
    }

    public static int[] makeCoords(int[] list) {
        int[] newList = new int[2];
        newList = new int[] {list[1]-1, list[0]-1};
        return newList;
    }
}