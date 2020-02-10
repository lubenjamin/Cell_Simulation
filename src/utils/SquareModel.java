package utils;


import java.util.ArrayList;
import java.util.List;


public class SquareModel extends Model {

  public SquareModel(int widthCells, int heightCells, int maxState) {
    super(widthCells, heightCells, maxState);

  }


  @Override
  public List<Cell> getSimpleNeighborhood(int x, int y) {
    ArrayList<Cell> ret = new ArrayList<>();
    addIfExists(ret, x, y + 1);
    addIfExists(ret, x - 1, y);
    addIfExists(ret, x + 1, y);
    addIfExists(ret, x, y - 1);
    return ret;
  }


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
