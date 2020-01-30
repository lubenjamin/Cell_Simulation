package cellsociety;

import java.util.ArrayList;
import java.util.List;

public class Model {  

  private ArrayList<List<Cell>> grid = new ArrayList<List<Cell>>();
  private ArrayList<Cell> neighborhood = new ArrayList<Cell>();

  public Model(int widthCells, int heightCells){
  }

  public Cell getCell(int x, int y){
    return grid.get(x).get(y);
  }

  public ArrayList<Cell> getNeighborhood(int x, int y){
    neighborhood.add(grid.get(x).get(y + 1));
    neighborhood.add(grid.get(x).get(y - 1));
    neighborhood.add(grid.get(x + 1).get(y));
    neighborhood.add(grid.get(x - 1).get(y));
    return neighborhood;
  }
}
