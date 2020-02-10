package utils;

import java.util.ArrayList;
import java.util.List;

public class HexModel {
    private final Cell[][] grid;
    public boolean manualEntry;

    public HexModel(int widthCells, int heightCells, int maxState) {
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

    public List<Cell> getNeighborhood(int x, int y) {
        ArrayList<Cell> ret = new ArrayList<>();
        if(y % 2 == 0) {
            addIfExists(ret, x, y - 1);
            addIfExists(ret, x + 1, y - 1);
            addIfExists(ret, x - 1, y);
            addIfExists(ret, x + 1, y);
            addIfExists(ret, x, y + 1);
            addIfExists(ret, x + 1, y + 1);
        } else {
            addIfExists(ret, x-1, y-1);
            addIfExists(ret, x, y-1);
            addIfExists(ret, x-1, y);
            addIfExists(ret, x + 1, y);
            addIfExists(ret, x - 1, y + 1);
            addIfExists(ret, x, y + 1);
        }
        return ret;
    }


    private void addIfExists(ArrayList<Cell> ret, int x, int y) {
        if (x < grid[0].length && x >= 0 && y >= 0 && y < grid.length) {
            ret.add(grid[y][x]);
        }
    }
}
