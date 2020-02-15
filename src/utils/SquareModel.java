package utils;


import java.util.ArrayList;
import java.util.List;

/**
 * The SquareModel class is a subclass of model
 * SquareModel will be rendered as a grid of squares within the view
 * Main unique aspect of this class is how neighbors are found
 */
public class SquareModel extends Model {

  /**
   * Constructor for the Square model
   * @param widthCells
   * @param heightCells
   * @param maxState
   */
  public SquareModel(int widthCells, int heightCells, int maxState) {
    super(widthCells, heightCells, maxState);

  }

  /**
   * Finds the simple neighbors meaning the ones directly adjacent to a specific cell
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return list of simple neighbors
   */
  @Override
  public List<Cell> getSimpleNeighborhood(int x, int y) {
    ArrayList<Cell> ret = new ArrayList<>();
    addIfExists(ret, x, y + 1);
    addIfExists(ret, x - 1, y);
    addIfExists(ret, x + 1, y);
    addIfExists(ret, x, y - 1);
    return ret;
  }

  /**
   * Finds the torus version of neighbors for a specific cell
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return list of torus neighbors
   */
  @Override
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

  /**
   * Similar to simpleNeighborhood but including the cells diagonal to the target cell
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return list of more neighbors
   */
  @Override
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


}
