package utils;


import java.util.ArrayList;
import java.util.List;


public abstract class Model {

  protected final Cell[][] grid;
  public boolean manualEntry;

  public Model(int widthCells, int heightCells, int maxState) {
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
    return ret;
  }

  public abstract List<Cell> getMooreNeighborhood(int x, int y);

  public abstract List<Cell> getTorusNeighborhood(int x, int y);

  protected void addIfExists(ArrayList<Cell> ret, int x, int y) {
    if (x < grid[0].length && x >= 0 && y >= 0 && y < grid.length) {
      ret.add(grid[y][x]);
    }
  }


}
