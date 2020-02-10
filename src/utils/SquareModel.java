package utils;


import java.util.ArrayList;
import java.util.List;


public class SquareModel extends Model{

    private final Cell[][] grid;
    public boolean manualEntry;

    public SquareModel(int widthCells, int heightCells, int maxState) {
        super(widthCells,heightCells,maxState);
        manualEntry = false;
        grid = new Cell[heightCells][widthCells];
        initializeGrid(maxState);
    }

    private void initializeGrid(int maxState) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                grid[y][x] = new Cell(x, y, maxState);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return grid[y][x];
    }

    public List<Cell> getSimpleNeighborhood(int x, int y) {
        ArrayList<Cell> ret = new ArrayList<>();
        addIfExists(ret, x, y + 1);
        addIfExists(ret, x - 1, y);
        addIfExists(ret, x + 1, y);
        addIfExists(ret, x, y - 1);
        return ret;
    }

    public List<Cell> getTorusNeighborhood(int x, int y) {
        ArrayList<Cell> ret = new ArrayList<>();
        addTorus(ret, x, y + 1);
        addTorus(ret, x - 1, y);
        addTorus(ret, x + 1, y);
        addTorus(ret, x, y - 1);
        return ret;
    }

    private void addTorus(ArrayList<Cell> ret, int x, int y) {
        int newX = x;
        int newY = y;
        if (x >= grid[0].length) {
            newX = 0;
        }
        if (x < 0) {
            newX = grid[0].length - 1;
        }
        if (y >= grid.length) {
            newY = 0;
        }
        if (y < 0) {
            newY = grid.length - 1;
        }

        ret.add(grid[newY][newX]);
    }


    public List<Cell> getMooreNeighborhood(int x, int y) {
        ArrayList<Cell> ret = new ArrayList<>();

        addIfExists(ret, x - 1, y + 1);
        addIfExists(ret, x, y + 1);
        addIfExists(ret, x + 1, y + 1);

        addIfExists(ret, x - 1, y);
        addIfExists(ret, x + 1, y);

        addIfExists(ret, x - 1, y - 1);
        addIfExists(ret, x, y - 1);
        addIfExists(ret, x + 1, y - 1);

        return ret;
    }

    private void addIfExists(ArrayList<Cell> ret, int x, int y) {
        if (x < grid[0].length && x >= 0 && y >= 0 && y < grid.length) {
            ret.add(grid[y][x]);
        }
    }
}
