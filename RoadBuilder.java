public class RoadBuilder {
    public static void main(String[] args) {
        // set variables
        int tileHeight = 25;
        int tileWidth = 25;
        int gridRows = 20;
        int gridColumns = 30;
        int[] goal = {1, 1};
        int[] start = {1, 1};
        int[][] obstacles = {};
        int[][] keyTiles = {};

        Grid grid1 = new Grid(gridRows, gridColumns, makeCoords(obstacles), makeCoords(keyTiles), makeCoords(goal), makeCoords(start));

        new Visualization("A*", grid1, tileWidth, tileHeight);
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
