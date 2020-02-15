package utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Model is one of the fundamental classes of the program
 * It initializes the grid of cell objects
 * Does not give the grid to any other class, only allows for accessing specific cells and their neighbors
 */
public abstract class Model {

  protected final Cell[][] grid;
  public boolean manualEntry;

  /**
   * Initialize the model object
   * @param widthCells
   * @param heightCells
   * @param maxState
   */
  public Model(int widthCells, int heightCells, int maxState) {
    manualEntry = false;
    grid = new Cell[heightCells][widthCells];
    initializeGrid(maxState);
  }

  /**
   * Create the grid of cell objects
   * @param maxState
   */
  private void initializeGrid(int maxState) {
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[0].length; x++) {
        grid[y][x] = new Cell(x, y, maxState);
      }
    }
  }

  /**
   * One of the few public methods of this class
   * Provides a cell at a specific coordinate
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return cell object at a specific coordinate
   */
  public Cell getCell(int x, int y) {
    return grid[y][x];
  }

  /**
   * Finds the simple neighbors
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return list of simple neighbors
   */
  public List<Cell> getSimpleNeighborhood(int x, int y) {
    ArrayList<Cell> ret = new ArrayList<>();
    return ret;
  }

  public abstract List<Cell> getMooreNeighborhood(int x, int y);

  public abstract List<Cell> getTorusNeighborhood(int x, int y);

  /**
   * Checks if a specific cells are within the bounds of the grid, if so added to a list
   * @param ret
   * @param x
   * @param y
   */
  protected void addIfExists(ArrayList<Cell> ret, int x, int y) {
    if (x < grid[0].length && x >= 0 && y >= 0 && y < grid.length) {
      ret.add(grid[y][x]);
    }
  }


}
