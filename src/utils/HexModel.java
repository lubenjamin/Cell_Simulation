package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The HexModel class is a subclass of model
 * HexModel will be rendered as a grid of hexs within the view
 * Main unique aspect of this class is how neighbors are found
 */
public class HexModel extends Model {

  /**
   * Constructor for the HexModel
   * @param widthCells
   * @param heightCells
   * @param maxState
   */
  public HexModel(int widthCells, int heightCells, int maxState) {
    super(widthCells, heightCells, maxState);
  }

  /**
   * Finds the neighbors of a specific hex, only one type of neighbor finding
   * @param x coordinate of the cell wanting to find neighbors of
   * @param y coordinate of the cell wanting to find neighbors of
   * @return list of neighboring hexs
   */
  public List<Cell> getSimpleNeighborhood(int x, int y) {
    ArrayList<Cell> ret = new ArrayList<>();
    if (y % 2 == 0) {
      addIfExists(ret, x, y - 1);
      addIfExists(ret, x + 1, y - 1);
      addIfExists(ret, x - 1, y);
      addIfExists(ret, x + 1, y);
      addIfExists(ret, x, y + 1);
      addIfExists(ret, x + 1, y + 1);
    } else {
      addIfExists(ret, x - 1, y - 1);
      addIfExists(ret, x, y - 1);
      addIfExists(ret, x - 1, y);
      addIfExists(ret, x + 1, y);
      addIfExists(ret, x - 1, y + 1);
      addIfExists(ret, x, y + 1);
    }
    return ret;
  }

  @Override
  public List<Cell> getMooreNeighborhood(int x, int y) {
    return getSimpleNeighborhood(x, y);
  }

  @Override
  public List<Cell> getTorusNeighborhood(int x, int y) {
    return null;
  }


}
